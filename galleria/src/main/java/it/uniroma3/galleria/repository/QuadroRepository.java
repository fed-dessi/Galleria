package it.uniroma3.galleria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Quadro;

public interface QuadroRepository  extends CrudRepository <Quadro, Long>  {

	Quadro findByTitolo(String titolo);
	List<Quadro> findByAnno(Integer anno);
	List<Quadro> findByAutore(Autore autore);
	List<Quadro> findAll();
	
    @Query("SELECT q FROM Quadro q WHERE LOWER(q.titolo) LIKE LOWER(CONCAT('%',:searchTerm,'%'))")
    public List<Quadro> searchTitoloWithJPQLQuery(@Param("searchTerm") String searchTerm);
    
}
