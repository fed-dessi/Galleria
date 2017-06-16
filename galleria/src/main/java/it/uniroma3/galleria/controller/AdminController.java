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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Quadro;
import it.uniroma3.galleria.model.RuoloUtente;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.service.AutoreService;
import it.uniroma3.galleria.service.QuadroService;
import it.uniroma3.galleria.service.UtenteService;

@Controller
public class AdminController {

	@Autowired 
	private UtenteService service;
	@Autowired
	private AutoreService aService;
	@Autowired
	private QuadroService qService;
	
	@GetMapping(value="/admin")
	public String admin (Model model){
		List<Utente> utenti= service.findAll();
		model.addAttribute("utenti",utenti);
		
		return "admin/ControlPanel";
	}
	
	@GetMapping(value = "/dettagliUtente")
	public String dettagliUtente(@ModelAttribute("id") Long id, Model model){
		Utente utente= service.getOneUtente(id);
		model.addAttribute("utente", utente);
	
		return "/admin/dettagliUtente";
	}
	
	/*
	 * METODI PER LA MODIFICA/RIMOZIONE DI UTENTE
	 */
	
	@PostMapping(value="/modifica")
	public String modifica(@Valid @ModelAttribute Utente utente,BindingResult results, HttpServletRequest request, @RequestParam(value="ruoloEsistenti",required= false) String ruolo,@RequestParam(value="RuoloUtente", required=false)String check, Model model){
		Long id=utente.getId();
		RuoloUtente ru= service.getOneUtente(id).getRuoliUtente();
		String username=service.getOneUtente(id).getUsername();
		//Controllo se l'oggetto utente ha errori
		if(results.hasErrors()){
		    
			List<FieldError> errors = results.getFieldErrors();
		    for (FieldError error : errors ) {
		    	System.out.println (error.getObjectName() + " - " + error.getDefaultMessage() + " - " + error.getField() + " - " + error.getCode());
		    }
		}
		//controlla che lo username non sia cambiato, se è lo stesso non fa niente,
		//se è diverso controlla che non esiste già, se esiste già return alla pagina ed esce dal metodo
		//se non esiste setUsername sull'oggetto utente
		
		if(!username.equals(utente.getUsername())){
			if(service.getUtenteByUsername(utente.getUsername()) != null){
				model.addAttribute(utente);
				model.addAttribute("usernameEsistente", true);
				return "/admin/modificaUtente";
			}else{ 
				ru.setUsername(utente.getUsername());
				
			}
		}
		
		//controllo se la password va cambiata (dafault = password non inserita, non va cambiata)
		if(utente.getPassword().equals("")|| utente.getPassword()==null){
			String vecchiaPassword= service.getOneUtente(id).getPassword();
			utente.setPassword(vecchiaPassword);
		}else{
			String password = new BCryptPasswordEncoder().encode(utente.getPassword());
			utente.setPassword(password);
		}
		//se lo username e' disponibile controllo se devo aggiornare i permessi dell'utente
		//Caso permessi da NON modificare
		if(check == null){
				utente.setRuoloUtente(ru);
		}else{ //caso permessi DA modificare
			ru.setRuolo(ruolo);
			utente.setRuoloUtente(ru);
		}
		utente.setEnabled(true);
		service.modificaUtente(utente);
		return "redirect:/dettagliUtente?id=" + String.valueOf(id);
	}
	
	@GetMapping(value="/modificaUtente")
	public String modifica(Model model,@ModelAttribute("id") Long id, BindingResult results){
		//passo l'utente come attribute del modello cosi' l'amministratore ha gia'i campi precompilati con i vecchi dati
		Utente utente= service.getOneUtente(id);
		model.addAttribute(utente);
		
		return "/admin/modificaUtente";
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
			List<Utente> utenti = service.findAll();
			model.addAttribute("utenti", utenti);
		
			return"/admin/ControlPanel";
			}
	}
	
	/*
	 * METODI PER LA MODIFICA/RIMOZIONE DI UN AUTORE
	 */
	
	@GetMapping(value="/modificaAutore")
	public String dettagliAutore(@ModelAttribute("id") Long id, Model model){

		Autore autore = aService.getOneAutore(id);
		model.addAttribute("autore", autore);
	
		return "/admin/modificaAutore";
	}
	
	@PostMapping(value="/modificaAutore")
	public String modificaAutore(@Valid @ModelAttribute Autore autore,BindingResult results, RedirectAttributes ra,Model model){
		if(results.hasErrors()){
		}
		aService.inserisciAutore(autore);
		ra.addFlashAttribute("modificatoCorrettamente", true);
		return "redirect:/dettagliAutore?id="+String.valueOf(autore.getId());

	}
	
	@GetMapping(value="/rimuoviAutore")
	public String rimoviAutore(@ModelAttribute("id") Long id, RedirectAttributes ra, Model model){
		Autore autore = aService.getOneAutore(id);
		aService.delete(autore);
		ra.addFlashAttribute("autoreCancellatoCorrettamente",true);
		return "redirect:/lista";
	}
	
	
	/*
	*	METODI PER LA MODIFICA/RIMOZIONE DI UN QUADRO
	*/
	
	
	
	@GetMapping(value="/modificaQuadro")
	public String dettagliQuadro(@ModelAttribute("id") Long id, Model model){
		Quadro quadro = qService.getOneQuadro(id);
		model.addAttribute("quadro", quadro);
	
		return "/admin/modificaQuadro";
	}
	
	@PostMapping(value="/modificaQuadro")
	public String modificaQuadro(@Valid @ModelAttribute Quadro quadro,BindingResult results, RedirectAttributes ra, Model model){
		if(results.hasErrors()){
		}
		Autore autore = aService.getOneAutore(quadro.getAutore().getId());
		quadro.setAutore(autore);
		qService.inserisciQuadro(quadro);
		ra.addFlashAttribute("modificatoCorrettamente",true);
		
	
		return "redirect:/dettagliQuadro?id="+String.valueOf(quadro.getId());

	}
	
	@GetMapping(value="/rimuoviQuadro")
	public String rimoviQuadro(@ModelAttribute("id") Long id, RedirectAttributes ra,Model model){
		Quadro quadro = qService.getOneQuadro(id);
		qService.delete(quadro);
		ra.addFlashAttribute("quadroCancellatoCorrettamente", true);
		return "redirect:/lista";
	}
}
