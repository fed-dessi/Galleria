package it.uniroma3.galleria.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Quadro;
import it.uniroma3.galleria.service.QuadroService;

@Controller
public class HomeController {
	
	@Autowired
	QuadroService service;
	
	@GetMapping(value = "/")
	public String index(){
		return "index";
	}
	
	@GetMapping(value = "/inserimento")
	public String inserimentoPagina(){
		return "inserimento";
	}
	
	//Inseriamo prima le informazioni del quadro
	@PostMapping(value = "/inserimento")
	public String inserimento(@Valid @ModelAttribute Quadro quadro, BindingResult bindingResult, Model model){
		
		if(bindingResult.hasErrors()){
			return "inserimento";
		}
		
		model.addAttribute("nome", quadro.getTitolo());
		model.addAttribute("quadro", quadro);
		return "inserimentoAutore";
	}
	
	//Dopo aver inserito le informazioni del quadro inseriamo quelle dell'autore e poi lo assegnamo al quadro, ci pensera' L'ORM a fare un persist a tutti e due
	@PostMapping(value = "/inserimentoAutore")
	public String inserimentoAutore(@Valid @ModelAttribute Autore autore, BindingResult bindingResult, Model model, HttpServletRequest request){
		if(bindingResult.hasErrors()){
			return "inserimentoAutore";
		}
		
		Quadro quadro = (Quadro)request.getAttribute("quadro");
		quadro.setAutore(autore);
		service.inserisciQuadro(quadro);
		model.addAttribute("nome", quadro.getTitolo());
		model.addAttribute("inseritoCorrettamente", true);
		return "inserimento";
	}
}
