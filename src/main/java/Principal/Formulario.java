package Principal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.Date;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig
public class Formulario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, jakarta.servlet.ServletException {

        String uploadPath = System.getProperty("java.io.tmpdir");

        String localizacao = request.getParameter("location");
        String tamanho = request.getParameter("size");
        String risco = request.getParameter("risk");
        String observacao = request.getParameter("description");

        Part filePart = request.getPart("photo");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        File Foto = new File(uploadPath + File.separator + fileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Foto.toPath());
        }
        
        //mandando a foto para o google drive
        //eu to ficnado doido

		try {
            DriveUploader.
			fileId = DriveUploader.post(Foto.getAbsolutePath(), filePart.getContentType(), fileName);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}

        Date data = Metadados.PegarDadoData(Foto);
        String nome = localizacao;
        Double latitude = Metadados.PegarDadoGPSLatitude(Foto);
        Double longitude = Metadados.PegarDadosGPSLongitude(Foto);
        GeografiaReversa saida = GeografiaReversa.RuaeBairro(latitude, longitude);

        Integer CategoriaReal = RetornoDeTamanho(tamanho) + RetornoDeRisco(risco);

        BancoDeDados.InserirITEM(data, CategoriaReal, 10 , observacao, nome, longitude ,latitude,saida.GetRua(),saida.GetBairro());

        Foto.delete();

       // response.getWriter().println("Denúncia enviada com sucesso!");
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

