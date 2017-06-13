package it.uniroma3.galleria.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Autore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(min = 1)
	@NotNull
	private String nome;
	
	@Size(min = 1)
	@NotNull
	private String cognome;
	
	@Size(min = 1)
	@NotNull
	private String nazionalita;
	
	@NotNull
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dob;
	
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dod;
	
	@OneToMany(mappedBy = "autore")
	private List<Quadro> quadri;
	
	public Autore(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDod() {
		return dod;
	}

	public void setDod(Date dod) {
		this.dod = dod;
	}

	public List<Quadro> getQuadri() {
		return quadri;
	}

	public void setQuadri(List<Quadro> quadri) {
		this.quadri = quadri;
	}
	
}
