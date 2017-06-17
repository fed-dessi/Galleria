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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Quadro;
import it.uniroma3.galleria.service.AutoreService;
import it.uniroma3.galleria.service.QuadroService;
import it.uniroma3.galleria.upload.ImmagineUpload;

@Controller
@SessionAttributes({"quadro"})
public class InserimentoController {

	@Autowired
	private QuadroService service;
	@Autowired
	private AutoreService aService;
	@Autowired
	private ImmagineUpload upload;
	
	@GetMapping(value = "/inserimento")
	public String inserimentoPagina(Quadro quadro, Model model){
		List<Autore> autori = aService.getAutori();
		model.addAttribute("autori", autori);
		return "/inserimento/inserimento";
	}
	
	//Inseriamo prima le informazioni del quadro
	@PostMapping(value = "/inserimento")
	public String inserimento(@Valid @ModelAttribute Quadro quadro, BindingResult bindingResult, @RequestParam(value = "autoriEsistenti", required = false) Long autoriEsistenti, @RequestParam(value = "inserisciAutore", required = false) String inserisciAutore, @RequestParam(value = "immagineQuadro", required = false) MultipartFile immagineQuadro, SessionStatus status, Model model){

		if(bindingResult.hasErrors()){
			
		    List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		    	System.out.println (error.getObjectName() + " - " + error.getDefaultMessage() + " - " + error.getField() + " - " + error.getCode());
		    }
			List<Autore> autori = aService.getAutori();
			model.addAttribute("autori", autori);
		    return "/inserimento/inserimento";
		    
		//Controllo se l'utente ha deciso di usare il menu a tendina per scegliere un autore gia' esistente
		} 

		if(inserisciAutore != null){
			//Se si e' scelto di inserire un immagine, creao il file, e carico il nome dentro al database
			if (!immagineQuadro.isEmpty()) {
				upload.creaDirectoryImmaginiSeNecessario();
				upload.creaFileImmagine(immagineQuadro.getOriginalFilename(), immagineQuadro);
				quadro.setImmagine(immagineQuadro.getOriginalFilename());
			}
			Autore autore = aService.getOneAutore(autoriEsistenti);
			quadro.setAutore(autore);
			service.inserisciQuadro(quadro);
			model.addAttribute("inseritoCorrettamente", true);
			model.addAttribute(quadro);
			model.addAttribute(autore);
			status.setComplete();
		}else{
			//Oltre ad aggiungere quadro al model, poiche' ho dichiarato il parametro "quadro" come @SessionAttributes allora verra' catturato nella sessione
			model.addAttribute(quadro);
			Autore autore = new Autore();
			model.addAttribute(autore);
		}
		return "/inserimento/inserimentoAutore";
	}
	

	
	//Dopo aver inserito le informazioni del quadro inseriamo quelle dell'autore e poi lo assegnamo al quadro, ci pensera' L'ORM a fare un persist a tutti e due
	@PostMapping(value = "/inserimentoAutore")
	public String inserimentoAutore(@Valid @ModelAttribute Autore autore, BindingResult bindingResult, Quadro quadro,  @RequestParam(value = "immagineQuadro", required = false) MultipartFile immagineQuadro, Model model, SessionStatus status){

		if(bindingResult.hasErrors()){
		    List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		    	System.out.println (error.getObjectName() + " - " + error.getDefaultMessage() + " - " + error.getField() + " - " + error.getCode());
		    }
			return "/inserimento/inserimentoAutore";
		}
		System.out.println(immagineQuadro.getOriginalFilename());
		//Controllo se in precedenza e' stato scelto un file da caricare come immagine
		if (!immagineQuadro.isEmpty()) {
			upload.creaDirectoryImmaginiSeNecessario();
			upload.creaFileImmagine(immagineQuadro.getOriginalFilename(), immagineQuadro);
			quadro.setImmagine(immagineQuadro.getOriginalFilename());
		}
		//Ottengo i dati del quadro dal parametro di sessione "quadro"
		quadro.setAutore(autore);
		service.inserisciQuadro(quadro);
		//distruggo i dati di quadro dentro la sessione
		status.setComplete();
		//reinserisco i dati di quadro per poter visualizzare il messaggio di successo
		model.addAttribute(quadro);
		model.addAttribute("inseritoCorrettamente", true);
		return "/inserimento/inserimentoAutore";
	}

}
