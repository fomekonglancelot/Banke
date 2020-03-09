package org.sid.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


//cette classe est abtract car on aurait soit un compte courant ou bien epargne
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // on doit specit qu il s agit d un etable parent, en suuit on defini la strategy, c ad dire comment les  table enfants vont etre enregistre
@DiscriminatorColumn(name="TYPE_OP",
discriminatorType = DiscriminatorType.STRING, length = 1)// on definit le type qui identifier chaque table enfant
public abstract  class Operation implements Serializable {
	//Entity  identifi cette classe coe un table dans la base de onnee

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long numero;
	
	
	private Date dateOperation;
	private Double montant;
	

	@ManyToOne
	@JoinColumn(name = "CODE_CPTE")// le nom de la colonne qui va representer table client dans le compte
	private Compte compte;// Chaque operation est lie a un compte(Associatiion)


	

	public Long getNumero() {
		return numero;
	}


	public void setNumero(Long numero) {
		this.numero = numero;
	}


	public Date getDateOperation() {
		return dateOperation;
	}


	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}


	public Double getMontant() {
		return montant;
	}


	public void setMontant(Double montant) {
		this.montant = montant;
	}


	public Compte getCompte() {
		return compte;
	}


	public void setCompte(Compte compte) {
		this.compte = compte;
	}


	public Operation() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Operation(Date dateOperation, Double montant, Compte compte) {
		super();
		this.dateOperation = dateOperation;
		this.montant = montant;
		this.compte = compte;
	}


	
	

}
