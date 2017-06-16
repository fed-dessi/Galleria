package it.uniroma3.galleria.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.galleria.model.Autore;

public interface AutoreRepository extends CrudRepository <Autore, Long> {
	

	List<Autore> findAll();
	
    @Query("SELECT a FROM Autore a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%',:searchTerm,'%')) OR LOWER(a.cognome) LIKE LOWER(CONCAT('%',:searchTerm,'%'))")
    public List<Autore> searchAutoreWithJPQLQuery(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT a FROM Autore a WHERE LOWER(a.nazionalita) LIKE LOWER(CONCAT('%',:searchTerm,'%'))")
    public List<Autore> searchNazionalitaWithJPQLQuery(@Param("searchTerm") String searchTerm);
	

}
