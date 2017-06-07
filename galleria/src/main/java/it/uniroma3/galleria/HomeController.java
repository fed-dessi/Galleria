package it.uniroma3.galleria;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.service.AutoreService;

/**
 * Handles requests for the application home page.
 */

public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private AutoreService autoreService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@GetMapping(value = "/")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@GetMapping(value = "/utente")
	public String loginPage(Locale locale, Model model) {
		return "utente";
	}
	
	@PostMapping(value = "/login")
	public String login(@Validated Utente user, Model model) {
		model.addAttribute("email", user.getEmail());
		return "user";
	}
	
	@GetMapping(value = "/inserimento")
	public String inserimentoPage(Locale locale, Model model) {
		return "inserimento";
	}
	
	@PostMapping(value = "/inserimento")
	public String inserimento(Autore autore, Model model) {
		Autore a = new Autore();
		a.setNome(autore.getNome());
		a.setCognome(autore.getCognome());
		a.setNazionalita(autore.getNazionalita());
		autoreService.inserisciAutore(a);
		model.addAttribute("autore", a);
		return "inserito";
	}
	
}
