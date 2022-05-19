package com.example.demo.controladores;

import com.example.demo.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ControladorPrincipal { // tengamos controladores que nos devuelven nuestras vistas principales
    
    
//    @GetMapping("") // solo lo muestro
//    public String index() {
//        return "index";
//    }    
     
    @GetMapping("")
    public String index(ModelMap htmlCualquiera) { // lo muestro y le inyecto información dinámica con thymeleaf: "♥PAGINA COMISION 9♥"
        htmlCualquiera.put("nombreReferencia", "♥ PAGINA COMISION 10 ♥");
        return "index";
    }
    
    @GetMapping("/registroUsuario")
    public String registroUsuario(){
        return "usuario_registro";
    }
    
    @GetMapping("/registroDireccion")
    public String registroDireccion(){
        return "direccion_registro.html";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}

   
