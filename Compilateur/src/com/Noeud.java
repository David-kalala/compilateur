package com;

import java.util.ArrayList;

public class Noeud {
	
	public Type_noeud type;
	public String valeur;
	public ArrayList<Noeud> enfants;
	public Symbole symbole;

	public Noeud() {}
	
	public Noeud(Type_noeud type) {
		this.type = type;
		this.enfants = new ArrayList<Noeud>();
	}
	
	public Noeud(Type_noeud type, String valeur) {
		this.type = type;
		this.valeur = valeur;
		this.enfants = new ArrayList<Noeud>();
	}

	public Noeud(Type_noeud type, Noeud enfant) {
		this.type = type;
		this.enfants = new ArrayList<Noeud>();
		this.enfants.add(enfant);
	}
	
	public Noeud(Type_noeud type, String valeur, Noeud enfant) {
		this.type = type;
		this.valeur = valeur;
		this.enfants = new ArrayList<Noeud>();
		this.enfants.add(enfant);
	}
	
	public Noeud(Type_noeud type, Noeud enfant1, Noeud enfant2) {
		this.type = type;
		this.enfants = new ArrayList<Noeud>();
		this.enfants.add(enfant1);
		this.enfants.add(enfant2);
	}
	
	public String toString() {
		return "Type noeud : " + type + ", valeur : " + valeur;	
	}
}
