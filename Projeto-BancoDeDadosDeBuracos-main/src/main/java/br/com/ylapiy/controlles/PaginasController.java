package Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {

    @GetMapping("/formulario")
    public String paginaFormulario() {
        return "form/formulario.html"; 
    }

    @GetMapping("/serv")
    public String paginaServ() {
        return "serv/serv.html"; 
    }

    @GetMapping("/sobre")
    public String paginaSobre() {
        return "form/sobre.html"; 
    }

    @GetMapping("/")
    public String paginaInicial() {
        return "home/index.html"; 
    }
}
