package it.uniroma3.galleria.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class RegistrazioneController {


	@Autowired
	UtenteService uService;
	@Autowired
	RuoloUtenteService ruService;
	
	@GetMapping(value = "/register")
	public String register(Utente utente, Model model){
		return "register";
	}
	
	@PostMapping(value = "/register")
	public String registraUtente(@Valid @ModelAttribute Utente utente, BindingResult bindingResult, HttpServletRequest request, @RequestParam(value = "ConfermaPassword", required = false) String confermaPassword,Model model){
		
		if(bindingResult.hasErrors()){
		    List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		    }
			return "/register";
		}
		if(uService.getUtenteByUsername(utente.getUsername()) != null){
			if(uService.getUtenteByUsername(utente.getUsername()).getUsername().equals(utente.getUsername())){
				model.addAttribute("usernameEsistente", true);
				model.addAttribute("confermaPassword", confermaPassword);
				return "/register";
			}
		}else{
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String password = passwordEncoder.encode(utente.getPassword());
			utente.setPassword(password);
			utente.setEnabled(true);
			RuoloUtente ru = new RuoloUtente("USER");
			ru.setUsername(utente.getUsername());
			utente.setRuoliUtente(ru);
			uService.inserisciUtente(utente);
			model.addAttribute(utente);
			model.addAttribute("utenteInserito", true);
			return "/index";
		}
		return "/register";
	}
}
