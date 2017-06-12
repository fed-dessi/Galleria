package it.uniroma3.galleria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.galleria.model.RuoliUtente;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.repository.RuoliUtenteRepository;

@Service
@Transactional
public class RuoliUtenteService {

	@Autowired
	private RuoliUtenteRepository repository;
	
	public RuoliUtenteService(){
		
	}
	
	public void inserisciRuoliUtente(RuoliUtente ru){
		repository.save(ru);
	}
	
	public List<RuoliUtente> getRuoliUtenteByUtente(Utente utente){
		List<RuoliUtente> ruoli = repository.findByUtente(utente);
		return ruoli;
	}
	
	public List<RuoliUtente> getRuoliUtenteByUsername(String username){
		List<RuoliUtente> ruoli = repository.findByUsername(username);
		return ruoli;
	}
}
