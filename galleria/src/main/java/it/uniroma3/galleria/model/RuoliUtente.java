package it.uniroma3.galleria.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class RuoliUtente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Utente utente;
	@NotNull
	private String ruoli;
	private String username;
	
	public RuoliUtente(){
		
	}
	
	public RuoliUtente(String ruoli, Utente utente){
		this.ruoli = ruoli;
		this.utente = utente;
		this.username = utente.getUsername();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public String getRuoli() {
		return ruoli;
	}
	public void setRuoli(String ruoli) {
		this.ruoli = ruoli;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
