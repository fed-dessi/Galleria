package it.uniroma3.galleria.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.galleria.model.RuoloUtente;

public interface RuoloUtenteRepository extends CrudRepository<RuoloUtente, Long> {
	
	 RuoloUtente findByUsername(String username);
	
}
