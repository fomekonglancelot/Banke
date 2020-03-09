package org.sid.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Client implements Serializable {
	
	//Entity  identifi cette classe coe un table dans la base de onnee
	@Id @GeneratedValue
	private Long code;//Id permet de dire que c est la cle Primar et qu il doit etre genere
	private String nom, email;
	
	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)// on defini ici le type de liason entre la table compte et lazy va gere cette liason entre les deux table
	private Collection<Compte> comptes;//pour indiquer la liaison entre le compte et le client. car un client peux avoir plusieur comopte

	

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Client(String nom, String email) {
		super();
		this.nom = nom;
		this.email = email;
	}


	public Long getCode() {
		return code;
	}


	public void setCode(Long code) {
		this.code = code;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Collection<Compte> getComptes() {
		return comptes;
	}


	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	}
	
	

}
