package it.uniroma3.galleria.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Quadro;
import it.uniroma3.galleria.service.AutoreService;
import it.uniroma3.galleria.service.QuadroService;
import it.uniroma3.galleria.upload.ImmagineUpload;

@Controller
public class IndexController {
	
	@Autowired
	private ImmagineUpload upload;
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
	public String ricerca(@ModelAttribute("selectRicerca") String scelta, @ModelAttribute("searchBox") String ricerca, RedirectAttributes ra, Model model){
		List<Quadro> quadri = new ArrayList<Quadro>();
		List<Autore> autori = new ArrayList<Autore>();
		
		//Popoliamo le liste da visualizzare in base alla scelta effettuata sul menu a tendina
		switch(scelta){
			case "1": quadri = service.searchByTitolo(ricerca);
					  break;
			case "2": quadri = service.getQuadroByAnno(Integer.valueOf(ricerca));
					  break;
			case "3": autori = aService.searchByAutore(ricerca);
					  break;
			case "4": autori = aService.searchByNazionalita(ricerca);
					  break;
		}
		
		//Se entrambe le liste sono vuote allora non ho ottenuto alcun risultato dalla ricerca e lo indico all'utente
		//aggiungendo un FlashAttribute che viene appeso alla sessione e viene distrutto automaticamente dopo la prima volta che si usa
		if(autori.isEmpty() && quadri.isEmpty()){
			ra.addFlashAttribute("nessunRisultato", true);
			return "redirect:/lista";
		//Altrimenti prima controllo se ho cercato per autore
		}else if(!autori.isEmpty()){
			for(Autore autore: autori){
				for(Quadro quadro: service.getQuadriByAutore(autore)){
					quadri.add(quadro);
				}
			}
		//Altrimenti ho cercato per quadro
		}else{
			for(Quadro quadro: quadri){
				autori.add(quadro.getAutore());
			}
		}
		model.addAttribute("autori",autori);
		model.addAttribute("quadri",quadri);
		return "listaQuadri";
	}
	
    @GetMapping(value = "/quadro/{nomeImmagine}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "nomeImmagine") String nomeImmagine) throws IOException {
        File serverFile = upload.getPathFile(nomeImmagine+ ".jpg");

        return Files.readAllBytes(serverFile.toPath());
    }
    
    
    // Login form
    @RequestMapping("/login")
    public String login() {
    	return "login";
    }
   
}
