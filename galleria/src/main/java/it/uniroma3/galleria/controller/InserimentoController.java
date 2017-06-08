package it.uniroma3.galleria.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Quadro;
import it.uniroma3.galleria.service.QuadroService;

@Controller
@SessionAttributes("quadro")
public class InserimentoController {
	
	@Autowired
	QuadroService service;
	
	@GetMapping(value = "/inserimento")
	public String inserimentoPagina(Quadro quadro){
		return "inserimento";
	}
	
	//Inseriamo prima le informazioni del quadro
	@PostMapping(value = "/inserimento")
	public String inserimento(@Valid @ModelAttribute Quadro quadro, BindingResult bindingResult, Model model){

		if(bindingResult.hasErrors()){
			
		    List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		    }
		    return "inserimento";
		}
		//Oltre ad aggiungere quadro al model, poiche' ho dichiarato il parametro "quadro" come @SessionAttributes allora verra' catturato nella sessione
		model.addAttribute(quadro);
		Autore autore = new Autore();
		model.addAttribute(autore);
		return "inserimentoAutore";
	}
	

	
	//Dopo aver inserito le informazioni del quadro inseriamo quelle dell'autore e poi lo assegnamo al quadro, ci pensera' L'ORM a fare un persist a tutti e due
	@PostMapping(value = "/inserimentoAutore")
	public String inserimentoAutore(@Valid @ModelAttribute Autore autore, BindingResult bindingResult, Quadro quadro, Model model, SessionStatus status){

		if(bindingResult.hasErrors()){
		    List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		    }
			return "inserimentoAutore";
		}
		

		//Ottengo i dati del quadro dal parametro di sessione "quadro"
		quadro.setAutore(autore);
		service.inserisciQuadro(quadro);
		//distruggo i dati di quadro dentro la sessione
		status.setComplete();
		//reinserisco i dati di quadro per poter visualizzare il messaggio di successo
		model.addAttribute(quadro);
		model.addAttribute("inseritoCorrettamente", true);
		return "inserimento";
	}
}
