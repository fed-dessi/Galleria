package it.uniroma3.galleria.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.galleria.model.RuoliUtente;
import it.uniroma3.galleria.model.Utente;

public interface RuoliUtenteRepository extends CrudRepository<RuoliUtente, Long> {
	
	List<RuoliUtente> findByUtente(Utente utente);
	List<RuoliUtente> findByUsername(String username);
}
