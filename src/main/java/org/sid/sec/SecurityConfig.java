package org.sid.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@Configuration //pour dire que c est une classe de configuration
@EnableWebSecurity //pour activer la securite web
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		BCryptPasswordEncoder bcpe = getBCPE();
		System.out.println("votre mot de pass: " +bcpe.encode("1234"));
		// on definir la maniere avec la quelle on doit chercher les utiilitsateur
		
		//on a definir ici les type d utilisateur que nous aurions et leur role
		/*
		 * auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles(
		 * "ADMIN", "USER");
		 * auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles(
		 * "USER");
		 */
		
		
		//usersByUsernameQuery specifier la requet que spring devrait uliser pour recuperer les info dans la base de donnee en suite on chercche le role des utilisateur
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?").authoritiesByUsernameQuery("select username as principal, roles as role  from users_roles "
						+ "where username=?")
		.rolePrefix("ROLE_")
		.passwordEncoder(getBCPE());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login"); // ici on  besoin d une securite base sur le formulaire
		
		//securise les resource de l applicaition de la maniere suivante
		http.authorizeRequests().antMatchers("/operations", "/consulterCompte").hasRole("USER");
		http.authorizeRequests().antMatchers("/saveOperation").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");// s il n a pas le droit d acceder on le renvoi a ceci
		
		/*
		 * http.authorizeRequests().antMatchers("/operations",
		 * "/consulterCompte").hasRole("ADMIN");
		 * http.authorizeRequests().antMatchers("/saveOperation").hasRole("USER");
		 */
	}
	
	@Bean   //permet de dire que l object va ettre placer dans le contest de spring
	BCryptPasswordEncoder getBCPE() {
	 
	 return new BCryptPasswordEncoder();
 }
	
}
