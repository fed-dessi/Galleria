package it.uniroma3.galleria;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.service.AutoreService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private AutoreService autoreService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/utente", method = RequestMethod.GET)
	public String loginPage(Locale locale, Model model) {
		return "utente";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Validated Utente user, Model model) {
		model.addAttribute("email", user.getEmail());
		return "user";
	}
	
	@RequestMapping(value = "/inserimento", method = RequestMethod.GET)
	public String inserimentoPage(Locale locale, Model model) {
		return "inserimento";
	}
	
	@RequestMapping(value = "/inserimento", method = RequestMethod.POST)
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
