package it.uniroma3.galleria.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Quadro;
import it.uniroma3.galleria.service.AutoreService;
import it.uniroma3.galleria.service.QuadroService;

@Controller
public class IndexController {
	
	@Autowired
	private QuadroService service;
	@Autowired
	private AutoreService aService;
	
	@GetMapping(value = "/")
	public String indexSlash(){
		return "index";
	}
	
	@GetMapping(value = "/index")
	public String index(SessionStatus status){
		status.setComplete();
		return "index";
	}
	
	@GetMapping(value = "/lista")
	public String paginaLista(Model model){
		List<Quadro> quadri = service.getQuadri();
		model.addAttribute("quadri",quadri);
		List<Autore> autori = aService.getAutori();
		model.addAttribute("autori",autori);
		return "listaQuadri";
	}
	
	@GetMapping(value = "/dettagliQuadro")
	public String dettagliQuadro(@ModelAttribute("id") Long id, BindingResult results, Model model){
		
		if(results.hasErrors()){
			return "listaQuadri";
		}
		
		Quadro quadro = service.getOneQuadro(id);
		model.addAttribute(quadro);
		return "dettagliQuadro";
	}
	
	@GetMapping(value = "dettagliAutore")
	public String dettagliAutore(@ModelAttribute("id") Long id, BindingResult results, Model model){
		
		if(results.hasErrors()){
			return "listaQuadri";
		}
		
		Autore autore = aService.getOneAutore(id);
		model.addAttribute(autore);
		List<Quadro> quadri = service.getQuadriByAutore(autore);
		model.addAttribute("quadri",quadri);
		return "/dettagliAutore";
	}
	
	@PostMapping(value = "/ricerca")
	public String ricerca(@ModelAttribute("selectRicerca") String scelta, @ModelAttribute("searchBox") String ricerca, Model model){
		List<Quadro> quadri = new ArrayList<Quadro>();
		List<Autore> autori = new ArrayList<Autore>();
		
		switch(scelta){
			case "1": quadri = service.searchByTitolo(ricerca);
					  break;
			case "2": quadri = service.getQuadroByAnno(Integer.valueOf(ricerca));
					  break;
			case "3": autori = aService.searchByNome(ricerca);
					  break;
			case "4": autori = aService.searchByCognome(ricerca);
					  break;
			case "5": autori = aService.searchByNazionalita(ricerca);
					  break;
		}
		
		if(!autori.isEmpty()){
			for(Autore autore: autori){
				for(Quadro quadro: service.getQuadriByAutore(autore)){
					quadri.add(quadro);
				}
			}
		}else{
			for(Quadro quadro: quadri){
				autori.add(quadro.getAutore());
			}
		}
		
		model.addAttribute("autori",autori);
		model.addAttribute("quadri",quadri);
		return "listaQuadri";
	}
	
    // Login form
    @RequestMapping("/login")
    public String login() {
    	return "login";
    }
}
