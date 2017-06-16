package it.uniroma3.galleria.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.galleria.model.Autore;

public interface AutoreRepository extends CrudRepository <Autore, Long> {
	

	Autore findByCognome(String cognome);
	Autore findByNome(String nome);
	List<Autore> findByNazionalita(String nazionalita);
	List<Autore> findAll();
	
    @Query("SELECT a FROM Autore a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%',:searchTerm,'%'))")
    public List<Autore> searchNomeWithJPQLQuery(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT a FROM Autore a WHERE LOWER(a.cognome) LIKE LOWER(CONCAT('%',:searchTerm,'%'))")
    public List<Autore> searchCognomeWithJPQLQuery(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT a FROM Autore a WHERE LOWER(a.nazionalita) LIKE LOWER(CONCAT('%',:searchTerm,'%'))")
    public List<Autore> searchNazionalitaWithJPQLQuery(@Param("searchTerm") String searchTerm);
	

}
