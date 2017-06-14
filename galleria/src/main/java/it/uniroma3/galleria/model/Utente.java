package it.uniroma3.galleria.model;




import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import it.uniroma3.galleria.annotazioni.ValidEmail;


@Entity
public class Utente{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min = 1)
	private String username;
	
	@NotNull
	@NotBlank
	private String password;
	
	
	private boolean enabled;
	
	@NotNull
	@ValidEmail
	private String email;
	
	@Size(min = 1)
	@NotNull
	private String nome;
	
	@Size(min = 1)
	@NotNull
	private String cognome;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private RuoloUtente ruoloUtente;

	
	public Utente(String email, String password, String nome, String cognome, String permessi, boolean enabled, RuoloUtente ruoloUtente){
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.enabled = enabled;
		this.ruoloUtente = ruoloUtente;
	}

	public Utente(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public Utente(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public RuoloUtente getRuoliUtente() {
		return ruoloUtente;
	}

	public void setRuoloUtente(RuoloUtente ruoloUtente) {
		this.ruoloUtente = ruoloUtente;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
}
