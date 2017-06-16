package it.uniroma3.galleria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.repository.AutoreRepository;

@Service
@Transactional
public class AutoreService {

	@Autowired
	private AutoreRepository repository;

	public AutoreService() {
		
	}
	
	public Autore inserisciAutore(Autore autore) {
		repository.save(autore);
		return autore;
	}

	public List<Autore> getAutori() {
		List<Autore> autori = repository.findAll();
		return autori;
	}
	
	public Autore getOneAutore(Long id) {
		Autore autore = repository.findOne(id);
		return autore;
	}

	public void delete(Autore a){
		repository.delete(a);
	}

	public List<Autore> searchByAutore(String searchTerm){
		List<Autore> autori = repository.searchAutoreWithJPQLQuery(searchTerm);
		return autori;
	}
	
	public List<Autore> searchByNazionalita(String searchTerm){
		List<Autore> autori = repository.searchNazionalitaWithJPQLQuery(searchTerm);
		return autori;
	}
}
