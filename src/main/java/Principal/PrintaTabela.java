package Principal;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tabela")
public class PrintaTabela extends HttpServlet{

    @Override
    protected void doGet (HttpServletRequest requisição, HttpServletResponse resposta) throws ServletException, IOException {

        resposta.setHeader("Access-Control-Allow-Origin", "*");
        resposta.setContentType("application/json");

        List<Registro> registros = BancoDeDados.printarTabela(); 

        Gson GoogleJson = new Gson();
        String json = GoogleJson.toJson(registros);

        PrintWriter Printagem = resposta.getWriter();
        Printagem.print(json);
        Printagem.flush();

    }


}
