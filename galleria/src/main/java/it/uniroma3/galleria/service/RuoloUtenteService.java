package it.uniroma3.galleria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.galleria.model.RuoloUtente;
import it.uniroma3.galleria.repository.RuoloUtenteRepository;

@Service
@Transactional
public class RuoloUtenteService {

	@Autowired
	private RuoloUtenteRepository repository;
	
	public RuoloUtenteService(){
		
	}
	
	public void inserisciRuoloUtente(RuoloUtente ru){
		repository.save(ru);
	}
	public RuoloUtente getByUsername(String username){	
	RuoloUtente ru= repository.findByUsername(username);
		return ru;
		
	}
}
