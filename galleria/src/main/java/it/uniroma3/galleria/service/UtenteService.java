package it.uniroma3.galleria.service;



import java.util.List;

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
	
	public void inserisciUtente(Utente utente) {
		repository.save(utente);
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
	
	public Utente getUtenteByUsername(String username){
		Utente utente = repository.findByUsername(username);
		return utente;
	}
	public List<Utente> findAll(){
		List<Utente> utenti= repository.findAll();
		return utenti;
	}
	public void modificaUtente(Utente utente){
		repository.save(utente);
	}
	
	}
	

