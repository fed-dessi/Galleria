package it.uniroma3.galleria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.galleria.model.Quadro;
import it.uniroma3.galleria.service.QuadroService;

@Controller
public class IndexController {
	
	@Autowired
	QuadroService service;
	
	@GetMapping(value = "/")
	public String index(){
		return "index";
	}
	
	@GetMapping(value = "/lista")
	public String paginaLista(Model model){
		List<Quadro> quadri = service.getQuadri();
		model.addAttribute("quadri",quadri);
		return "listaQuadri";
	}
	
	@GetMapping(value = "/dettagli")
	public String dettagliQuadro(@ModelAttribute("id") Long id, BindingResult results, Model model){
		
		if(results.hasErrors()){
			return "listaQuadri";
		}
		
		Quadro quadro = service.getOneQuadro(id);
		model.addAttribute(quadro);
		return "dettagliQuadro";
	}
	
	  // Login form
	  @RequestMapping("/login")
	  public String login() {
		  return "login";
	  }
}
