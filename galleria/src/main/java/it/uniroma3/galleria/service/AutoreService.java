package it.uniroma3.galleria.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import it.uniroma3.galleria.model.Autore;


public class AutoreService {

	@PersistenceContext(unitName = "unit-jee-galleria")
	private EntityManager em;

	public AutoreService() {
		
	}
	
	public Autore inserisciAutore(Autore autore) {
		em.persist(autore);
		return autore;
	}

	public List<Autore> getAutori() {
		TypedQuery<Autore> query = em.createNamedQuery("findAll", Autore.class);
		List<Autore> autori = query.getResultList();
		return autori;
	}
	
	public Autore getOneAutore(Long id) {
		Autore autore = em.find(Autore.class, id);
		return autore;
	}


	public void delete(Autore a){
	em.remove(em.contains(a)? a: em.merge(a));
	}
}
