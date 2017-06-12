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
import it.uniroma3.galleria.model.RuoliUtente;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.service.QuadroService;
import it.uniroma3.galleria.service.RuoliUtenteService;
import it.uniroma3.galleria.service.UtenteService;

@Controller
public class IndexController {
	
	@Autowired
	QuadroService service;
	@Autowired
	UtenteService uService;
	@Autowired
	RuoliUtenteService ruService;
	
	@GetMapping(value = "/")
	public String index(){
		Utente utente = new Utente("admin", "$2a$04$uNPbZivky49s9o6eNT/YpuXQNXSr7JLYM7KzRhxG0T/mOX7Gwr3UO", true);
		Utente utente1 = new Utente("user", "$2a$04$uNPbZivky49s9o6eNT/YpuXQNXSr7JLYM7KzRhxG0T/mOX7Gwr3UO", true);
		if(uService.getUtenteByUsername(utente.getUsername()) == null){
				RuoliUtente ru = new RuoliUtente("ADMIN", utente);
				ruService.inserisciRuoliUtente(ru);
		}
		if(uService.getUtenteByUsername(utente1.getUsername()) == null){
				RuoliUtente ru1 = new RuoliUtente("USER", utente1);
				ruService.inserisciRuoliUtente(ru1);
		}
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
