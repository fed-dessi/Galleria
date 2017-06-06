package it.uniroma3.galleria.repository;


import org.springframework.data.repository.CrudRepository;


import it.uniroma3.galleria.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

	Utente findByEmail(String email);
	

}
