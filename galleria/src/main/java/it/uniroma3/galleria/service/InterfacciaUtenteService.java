package it.uniroma3.galleria.service;

import it.uniroma3.galleria.annotazioni.UsernameExistsException;
import it.uniroma3.galleria.model.RuoloUtente;
import it.uniroma3.galleria.model.Utente;

public interface InterfacciaUtenteService {
    Utente registraNuovoUtente(Utente utente, RuoloUtente ru) throws UsernameExistsException;
}