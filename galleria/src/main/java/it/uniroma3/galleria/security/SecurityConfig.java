package it.uniroma3.galleria.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private DataSource dataSource;
 
    //Indico a Spring Security dove andare a prendere gli utenti, e dove andare a prendere i ruoli dei vari utenti, e che deve usare BCrypt per decriptare gli hash dal database
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
		
		.passwordEncoder(new BCryptPasswordEncoder())
		.usersByUsernameQuery("SELECT username,password,1 FROM utente where username=?")
		.authoritiesByUsernameQuery("SELECT username,ruoli FROM ruoli_utente where username=?");
	}
	
	/*In configure() sto indicando al filtro(Spring Security) che pagine sono disponibili a chiunque, e che pagine bisogna effettuare il login,
	 * inoltre specifico le pagine di login e la modalita' di logout (e' standard). 
	 * 
	 * Con .antMatchers("") sto indicando una pagina oppure un path dei miei template/static resources("/path/**") sul quale effettuare
	 * qualche operazione del filtro.
	 * 
	 * Con .anyRequest sto dicendo che tutte le richieste a pagine non specificate prima gli va assegnata un'operazione(in questo caso authenticated()
	 * ovvero voglio che siano tutte autenticate tramite login).
	 * 
	 * Con .formLogin sto "configurando" il modo di login: gli indico una pagina specifica invece di usare quella standard di spring con .loginPage("pathLogin")
	 * e gli dico che deve essere accessibile a tutti; faccio la stessa cosa con il logout.
	 * 
	 * !!Da notare che .access() use hasAuthority e non hasRole, questo perche' se uso hasRole devo per forza indicare i ruoli come ROLE_USER oppure ROLE_ADMIN
	 *   in questo modo invece posso definire io i miei nomi, e dovro' fare la stessa cosa dentro il tag di thymeleaf (sec:authorize = "hasAuthority(RUOLO)").		
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
        .authorizeRequests()
        	.antMatchers("/", "/register").permitAll()
        	.antMatchers("/inserimento/**").access("hasAuthority('ADMIN')")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .failureUrl("/?error")
            .and()
        .logout()
        	.logoutSuccessUrl("/?logout")
            .permitAll()
            .and()
        .sessionManagement()
        	.maximumSessions(1)
        	.expiredUrl("/login?expired");
    }
}