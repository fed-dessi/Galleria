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

import it.uniroma3.galleria.annotazioni.UsernameExistsException;
import it.uniroma3.galleria.model.RuoloUtente;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.service.UtenteService;

@Controller
public class RegistrazioneController {


	@Autowired
	private UtenteService uService;
	
	@GetMapping(value = "/register")
	public String register(Utente utente, Model model){
		return "register";
	}
	
	//Abbiamo affidato allo UserService il compito di gestire la verifica dei campi di un form register cosi' da snellire il codice,
	//inoltre ci siamo creati una custom Exception che viene lanciata quando lo username e' gia' presente nel database
	@PostMapping(value = "/register")
	public String registraUtente(@Valid @ModelAttribute Utente utente, BindingResult bindingResult, HttpServletRequest request, Model model){

        
		utente.setEnabled(true);
		RuoloUtente ru = new RuoloUtente("USER", utente.getUsername());
	    Utente registrante = new Utente();
	    
	    //controllo se i campi di utente non hanno errori con le @Annotazioni
	    if (!bindingResult.hasErrors()) {
	        registrante = creaAccountUtente(utente, bindingResult, ru);
	    }
	    //Se lo username esiste gia' significa che registrante e' stato impostato a null, quindi aggiungo l'errore dentro BindingResult
	    if (registrante == null) {
	    	bindingResult.rejectValue("username", "Username.Existing");
	    }
		//catturo il resto degli errori, ovviamente dovro' ritornare utente altrimenti i campi non mi verranno ricompilati
		if(bindingResult.hasErrors()){
		    List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage() + " - " + error.getField() + " - " + error.getCode());
		    }
		    model.addAttribute("utente", utente);
			return "/register";
		}else {
			model.addAttribute("utente", registrante);
			model.addAttribute("utenteInserito", true);
			return "/index";
	    }

	}
	
	//metodo privato di supporto per la creazione dell'utente
	private Utente creaAccountUtente(Utente utente, BindingResult result, RuoloUtente ru) {
	    Utente registrante = null;
	    try {
	        registrante = uService.registraNuovoUtente(utente, ru);
	    } catch (UsernameExistsException e) {
	        return null;
	    }
	    return registrante;
	}
}
