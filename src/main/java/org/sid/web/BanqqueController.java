package org.sid.web;

import org.sid.entities.Compte;
import org.sid.entities.Operation;
import org.sid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanqqueController {
	
	@Autowired
	private IBanqueMetier banqueMetier;
	
	@RequestMapping("/operations")
	public String index() {
		
		
		return"comptes";
	}
	

	@RequestMapping("/consulterCompte")
	public String consulter(Model model, String codeCompte,
			@RequestParam(name="page", defaultValue = "0") int page, @RequestParam(name="size", defaultValue = "5")int size) {
		//afin d afficher le compte a consulter dans la vue ou moteur de recherche
		model.addAttribute("codeCompte", codeCompte);
		
		try {
			

			Compte cp = banqueMetier.consulterCompte(codeCompte);
			
			Page<Operation> pageOperations = banqueMetier.listOperation(codeCompte, page, size);
			
			model.addAttribute("listOperations",pageOperations.getContent());//getcontent permet de retourne la liste des operations
			
			//retournee le nomnbre de page
			int[] pages = new int[pageOperations.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("compte",cp);
			
			
		} catch (Exception e) {
			//lorsqu il y aura une erreur on va l afficher a la vue
			model.addAttribute("exception", e);
		}
		
		
		return"comptes";
	}
	
	
	@RequestMapping(value = "/saveOperation", method= RequestMethod.POST)
	public String saveOperation(Model model, String  typeOperation, 
			String codeCompte, Double montant, String codeCompte2 ) {
		
	try {
		
		if (typeOperation.equals("VERS")) {
			
			banqueMetier.verser(codeCompte, montant);
			
		} else if (typeOperation.equals("RETR")) {
			banqueMetier.retirer(codeCompte, montant);

		}else if (typeOperation.equals("VIR")) {
			banqueMetier.virement(codeCompte, codeCompte2, montant);

		}
		
		
		
	} catch (Exception e) {

			model.addAttribute("error", e);
			
			return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();  
	}
		
		
	//on le redirige ver consulterCompte avcec le codecompte a consulter
		
	return "redirect:/consulterCompte?codeCompte="+codeCompte;  
	}
	
	
	


}
