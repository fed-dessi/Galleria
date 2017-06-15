package it.uniroma3.galleria.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.galleria.annotazioni.UsernameExistsException;
import it.uniroma3.galleria.model.RuoloUtente;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.repository.UtenteRepository;

@Service
@Transactional
public class UtenteService implements InterfacciaUtenteService{
	
	@Autowired    
	private UtenteRepository repository;
	
	
	
	public UtenteService() {
		
	}
	

	
	public Utente getOneUtente(Long id) {
		Utente utente = repository.findOne(id);
		return utente;
	}

	public void delete(Utente u){
		repository.delete(u);
	}
	
	public Utente getUtenteByEmail(String email){
		Utente utente = repository.findByEmail(email);
		return utente;
	}
	
	public Utente getUtenteByUsername(String username){
		Utente utente = repository.findByUsername(username);
		return utente;
	}
	public List<Utente> findAll(){
		List<Utente> utenti= repository.findAll();
		return utenti;
	}
	public void modificaUtente(Utente utente){
		repository.save(utente);
	}
	
	//Lascio controllare alla classe service se esiste gia' un utente con lo stesso username nel database,
	//se lo trova solleva un'eccezione che possiamo catturare nei nostri messages.properties e mostrarla all'utente.
	//I permessi dell'utente so gia' che possono essere o USER o ADMIN, dato che avro' solo una form allora posso pre-impostarli io a USER
	@Override
    public Utente registraNuovoUtente(Utente utente, RuoloUtente ru) throws UsernameExistsException {
         
        if (usernameEsiste(utente.getUsername())) {   
            throw new UsernameExistsException("Username gia' esistente");
        }
        utente.setRuoloUtente(ru);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(utente.getPassword());
		utente.setPassword(password);
        return repository.save(utente);       
    }
    
    //Metodo privato di supporto: controlla se username e' gia presente nel DB e ritorna TRUE/FALSE
    private boolean usernameEsiste(String username) {
        Utente utente = repository.findByUsername(username);
        if (utente != null) {
            return true;
        }
        return false;
    }
	
}
	

