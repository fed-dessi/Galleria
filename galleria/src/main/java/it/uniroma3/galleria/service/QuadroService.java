package it.uniroma3.galleria.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.galleria.model.Autore;
import it.uniroma3.galleria.model.Quadro;
import it.uniroma3.galleria.repository.QuadroRepository;

@Service
@Transactional
public class QuadroService {

	@Autowired
	private QuadroRepository repository;

	public QuadroService() {
		
	}
	
	public Quadro inserisciQuadro(Quadro quadro) {
		repository.save(quadro);
		return quadro;
	}

	public List<Quadro> getQuadri() {
		List<Quadro> autori = repository.findAll();
		return autori;
	}
	
	public Quadro getOneQuadro(Long id) {
		Quadro quadro = repository.findOne(id);
		return quadro;
	}

	public void delete(Quadro q){
		repository.delete(q);
	}
	
	public List<Quadro> getQuadriByAutore(Autore autore){
		List<Quadro> quadri = repository.findByAutore(autore);
		return quadri;
	}
	
	public Quadro getQuadroByTitolo(String titolo){
		Quadro quadro = repository.findByTitolo(titolo);
		return quadro;
	}
	
	public List<Quadro> getQuadroByTecnica(String tecnica){
		List<Quadro> quadri = repository.findByTecnica(tecnica);
		return quadri;
	}
}
