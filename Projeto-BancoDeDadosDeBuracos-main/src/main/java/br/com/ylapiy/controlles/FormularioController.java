package br.com.ylapiy.controlles;

import br.com.ylapiy.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.sql.Date;

@RestController
@RequestMapping("/api/formulario")
public class FormularioController {

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> receberFormulario(
            @RequestParam("location") String localizacao,
            @RequestParam("size") String tamanho,
            @RequestParam("risk") String risco,
            @RequestParam("description") String observacao,
            @RequestParam("photo") MultipartFile file
    ) throws IOException {

        // salvar o arquivo temporariamente
        String uploadPath = System.getProperty("java.io.tmpdir");
        File foto = new File(uploadPath + File.separator + file.getOriginalFilename());
        file.transferTo(foto);

        // envia para o Drive
        try {
            DriveUploader.fileId = DriveUploader.post(
                    foto.getAbsolutePath(),
                    file.getContentType(),
                    file.getOriginalFilename()
            );
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao enviar para o Google Drive.");
        }

        // extrai metadados
        Date data = Metadados.PegarDadoData(foto);
        Double latitude = Metadados.PegarDadoGPSLatitude(foto);
        Double longitude = Metadados.PegarDadosGPSLongitude(foto);
        GeografiaReversa saida = GeografiaReversa.RuaeBairro(latitude, longitude);

        int categoria = mapearTamanho(tamanho) + mapearRisco(risco);

        BancoDeDados.InserirITEM(
                data, categoria, 10, observacao, localizacao,
                longitude, latitude, saida.GetRua(), saida.GetBairro()
        );

        foto.delete();

        return ResponseEntity.ok("Denúncia enviada com sucesso.");
    }

    private int mapearTamanho(String size) {
        return switch (size.toLowerCase()) {
            case "small" -> 3;
            case "medium" -> 4;
            case "large" -> 5;
            default -> 0;
        };
    }

    private int mapearRisco(String risk) {
        return switch (risk.toLowerCase()) {
            case "low" -> 3;
            case "medium" -> 4;
            case "high" -> 5;
            default -> 0;
        };
    }
}
