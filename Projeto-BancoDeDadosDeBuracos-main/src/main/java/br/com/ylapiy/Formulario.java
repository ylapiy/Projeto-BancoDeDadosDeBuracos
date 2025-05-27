package br.com.ylapiy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public class Formulario {

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> receberFormulario(
        @RequestParam("location") String localizacao,
        @RequestParam("size") String tamanho,
        @RequestParam("risk") String risco,
        @RequestParam("description") String observacao,
        @RequestParam("photo") MultipartFile file
    ) {
        try {
            String uploadPath = System.getProperty("java.io.tmpdir");
            File foto = new File(uploadPath, file.getOriginalFilename());
            file.transferTo(foto);

            // upload para o Google Drive
            DriveUploader.fileId = DriveUploader.post(
                foto.getAbsolutePath(),
                file.getContentType(),
                file.getOriginalFilename()
            );

            Date data = Metadados.PegarDadoData(foto);
            String nome = localizacao;
            Double latitude = Metadados.PegarDadoGPSLatitude(foto);
            Double longitude = Metadados.PegarDadosGPSLongitude(foto);
            GeografiaReversa saida = GeografiaReversa.RuaeBairro(latitude, longitude);

            Integer CategoriaReal = RetornoDeTamanho(tamanho) + RetornoDeRisco(risco);

            BancoDeDados.InserirITEM(data, CategoriaReal, 10 , observacao, nome, longitude ,latitude,saida.GetRua(),saida.GetBairro());

            foto.delete();

            // response.getWriter().println("Denúncia enviada com sucesso!");
            return ResponseEntity.ok("Denúncia enviada com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao processar formulário.");
        }    
    }

    private Integer RetornoDeTamanho(String size) {
        if ("small".equalsIgnoreCase(size)) {
            return 3;
        } else if ("medium".equalsIgnoreCase(size)) {
            return 4;
        } else if ("large".equalsIgnoreCase(size)) {
            return 5;
        } else {
            return 0;
        }
    }

    private Integer RetornoDeRisco(String risk) {
        if ("low".equalsIgnoreCase(risk)) {
            return 3;
        } else if ("medium".equalsIgnoreCase(risk)) {
            return 4;
        } else if ("high".equalsIgnoreCase(risk)) {
            return 5;
        } else {
            return 0;
        }
    }
}

