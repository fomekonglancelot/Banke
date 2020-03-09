package org.sid;

import java.util.Date;

import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.CompteEpargne;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.sid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BanqueApplication implements CommandLineRunner{
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private IBanqueMetier banqueMetier;

	
	public static void main(String[] args) {
		SpringApplication.run(BanqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	
		  Client c1 = clientRepository.save(new Client("Guy", "lancelot@yahoo.fr"));
		  Client c2 = clientRepository.save(new Client("lancelot", "guy@yahoo.fr"));
		  
		  Compte cp1 = compteRepository.save( new CompteCourant("c1", new Date(), 9000.0, c1, 6000.0));
		  
		  Compte cp2 = compteRepository.save( new CompteEpargne("c2", new Date(),
		  10000.0, c2, 7));
		  
		  operationRepository.save(new Versement(new Date(), 100000.0, cp1));
		  
		  operationRepository.save(new Versement(new Date(), 4000.0, cp1));
		  operationRepository.save(new Retrait(new Date(), 2000.0, cp2));
		  operationRepository.save(new Versement(new Date(), 8000.0, cp1));
		  
		  operationRepository.save(new Versement(new Date(), 4000.0, cp2));
		  
		  operationRepository.save(new Versement(new Date(), 2000.0, cp2));
		  operationRepository.save(new Retrait(new Date(), 3000.0, cp1));
		  operationRepository.save(new Versement(new Date(), 7000.0, cp2));
		  
		  banqueMetier.verser("c1", 111111.0);
		  banqueMetier.verser("c1", 300025.0);
		 
		
	}

}
