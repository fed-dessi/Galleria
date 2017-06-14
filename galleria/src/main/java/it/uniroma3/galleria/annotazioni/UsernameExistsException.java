package it.uniroma3.galleria.annotazioni;

@SuppressWarnings("serial")
public class UsernameExistsException extends Throwable {

    public UsernameExistsException(final String message) {
        super(message);
    }

}