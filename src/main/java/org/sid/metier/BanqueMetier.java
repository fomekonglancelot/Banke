package org.sid.metier;


import java.util.Date;
import java.util.List;

import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.Operation;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;   // Spring g�re les transactions 
 

@Service // est utilise pour les objet de la couche metier
@Transactional // pour demande a Spring de gerer les transaction
public class BanqueMetier implements IBanqueMetier{
		
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;

	@Override
	public Compte consulterCompte(String codeCpte) {
		
		Compte compte= compteRepository.findById(codeCpte).get();
		 if (compte==null) throw new RuntimeException("Compte introuvable"); // c'est une exception non surveiller
		return compte;
		
	}

	@Override
	public void verser(String codeCpte, double montant) {
		//on consulte d abord le compte avant de faire un versement
		
		Compte cp = consulterCompte(codeCpte);
		
		Versement v = new Versement(new Date(), montant, cp);
		operationRepository.save(v);
		
		cp.setSolde(cp.getSolde()+montant);
		
		compteRepository.save(cp);
		
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		
	//on consulte d abord le compte avant de faire un versement
		
		Compte cp = consulterCompte(codeCpte);
		
		double facilitesCaisse=0;
		
		//on verifie le type de compte avec instanceof
		
		if(cp instanceof CompteCourant) facilitesCaisse = ((CompteCourant) cp).getDecouvert();
		
		if (cp.getSolde()+facilitesCaisse < montant) {
			
			 throw new RuntimeException("Solde Insuffissant");
			
		}
		
		Retrait r = new Retrait(new Date(), montant, cp);
		
		operationRepository.save(r);
		
		cp.setSolde(cp.getSolde()-montant);
		
		compteRepository.save(cp);
		
		
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double montant) {
		
		
		if (codeCpte1.equals(codeCpte2)) {
			
			 throw new RuntimeException("imposible de faire un virement dans le meme compte");
			
		}
		retirer(codeCpte1, montant);
		
		
		verser(codeCpte2, montant);
		
	}

	@Override
	public Page<Operation> listOperation(String codeCpte, int page, int size) {
		
		return operationRepository.listOperation(codeCpte, PageRequest.of(page, size));
	}



}
