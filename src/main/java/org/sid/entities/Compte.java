package org.sid.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// cette classe est abtract car on aurait soit un compte courant ou bien epargne
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // on doit specit qu il s agit d un etable parent, en suuit on defini la strategy, c ad dire comment les  table enfants vont etre enregistre
@DiscriminatorColumn(name="TYPE_CPTE",
discriminatorType = DiscriminatorType.STRING, length = 2)// on definit le type qui identifier chaque table enfant
public abstract class Compte implements Serializable {
	

	@Id
	private String codeCompte;
	private Date dateCreation;
	private double solde;
	
	@ManyToOne
	@JoinColumn(name = "CODE_CLI")// le nom de la colonne qui va representer table client dans le compte
	private Client client; // un compte appartient a un client
	
	@OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
	private Collection<Operation> operations; // pour un compte on peut avoir plusieur Operation


	public Compte() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Compte( String codeCompte, Date dateCreation, double solde, Client client) {
		super();
		
		this.codeCompte = codeCompte;
		this.dateCreation = dateCreation;
		this.solde = solde;
		this.client = client;
	}
	public String getCodeCompte() {
		return codeCompte;
	}
	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Collection<Operation> getOperations() {
		return operations;
	}
	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}
	
	
	
	

}
