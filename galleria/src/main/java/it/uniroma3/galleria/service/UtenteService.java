package it.uniroma3.galleria.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.repository.UtenteRepository;

@Service
@Transactional
public class UtenteService {
	
	@Autowired    
	private UtenteRepository repository;

	public UtenteService() {
		
	}
	
	public Utente inserisciUtente(Utente utente) {
		repository.save(utente);
		return utente;
	}

	
	public Utente getOneUtente(Long id) {
		Utente utente = repository.findOne(id);
		return utente;
	}

	public void delete(Utente u){
		repository.delete(u);
	}
	
	public Utente getUtenteByEmail(String email){
		Utente utente = repository.findByEmail(email);
		return utente;
	}
}
