package it.uniroma3.galleria.model;

import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class RuoloUtente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany (mappedBy="ruoloUtente")
	private List<Utente> utenti;
	@NotNull
	private String ruolo;
	private String username;
	
	public RuoloUtente(){
		
	}
	public RuoloUtente(String ruolo){
		this.ruolo=ruolo;
		
	}
	
	public RuoloUtente(String ruolo, List<Utente> utenti){
		this.ruolo = ruolo;
		this.utenti = utenti;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Utente> getUtenti() {
		return utenti;
	}
	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
