package it.uniroma3.galleria.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.galleria.model.RuoloUtente;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.service.RuoloUtenteService;
import it.uniroma3.galleria.service.UtenteService;

@Controller
public class AdminController {

	@Autowired 
	private UtenteService service;
	private RuoloUtenteService ruService;
	
	@PostMapping(value="/modifica")
	public String modifica(@Valid @ModelAttribute Utente utente,BindingResult results, HttpServletRequest request, @RequestParam(value="ruoliEsistenti",required= false) String ruolo,@RequestParam(value="RuoloUtente", required=false)String check, Model model){
		Long id=utente.getId();
		Utente utente1 = service.getOneUtente(id);
		System.out.println(utente1.getUsername());

		RuoloUtente ru1=ruService.getByUsername(utente1.getUsername());
		System.out.println(ru1.getRuolo());

		if(results.hasErrors()){
		    List<FieldError> errors = results.getFieldErrors();
		    for (FieldError error : errors ) {
		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		        
		    }
			return "redirect:/dettagliUtente?id=" + String.valueOf(id);
			
		} else {
			if(check!=null){
				
				
			RuoloUtente ru= ruService.getByUsername(service.getOneUtente(id).getUsername());
			utente.setRuoliUtente(ru);
			service.modificaUtente(utente);
			return "redirect:/dettagliUtente?id=" + String.valueOf(id);
		}
			return "redirect:/dettagliUtente?id=" + String.valueOf(id);
		}

	}
	@GetMapping(value="/modificaUtente")
	public String modifica(Model model,@ModelAttribute("id") Long id, BindingResult results){
		Utente utente= service.getOneUtente(id);
		model.addAttribute(utente);
		return "/admin/modifica";
		
	}
	
	@GetMapping(value="/rimuoviUtente")
	public String rimuovi(Model model,@ModelAttribute("id") Long id, BindingResult results){
		if(results.hasErrors()){
			List<Utente> utenti =service.findAll();
			model.addAttribute("utenti", utenti);
			return"/admin/ControlPanel";
		}else{
		model.addAttribute("utente", service.getOneUtente(id));
		service.delete(service.getOneUtente(id));
		model.addAttribute("cancellato", true);
		List<Utente> utenti =service.findAll();
		model.addAttribute("utenti", utenti);
		return"/admin/ControlPanel";
	}
}
	
	@GetMapping(value="/admin")
	public String admin (Model model){
		List<Utente> utenti= service.findAll();
		model.addAttribute("utenti",utenti);
	return "admin/ControlPanel";
	}
	@GetMapping(value = "/dettagliUtente")
	public String dettagliUtente(@ModelAttribute("id") Long id, BindingResult results, Model model){
		if(results.hasErrors()){
			return "/admin/ControlPanel";
		}
		Utente utente= service.getOneUtente(id);
		model.addAttribute("utente", utente);
	
	return "/admin/dettagliUtente";

	}
}
