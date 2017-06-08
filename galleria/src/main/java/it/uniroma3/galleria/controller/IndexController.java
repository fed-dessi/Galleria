package it.uniroma3.galleria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

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
	
	//Prima implementazione di una lista di quadri disponibili (bisogna aggiungere link)
	@GetMapping(value = "/lista")
	public String paginaLista(Model model){
		List<Quadro> quadri = service.getQuadri();
		model.addAttribute("quadri",quadri);
		return "listaQuadri";
	}
}
