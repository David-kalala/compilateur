package compilateur;

import java.util.ArrayList;

import compilateur.type.Type_noeud;

public class Noeud {
	Type_noeud type;
	String valeur;
	ArrayList<Noeud> enfants;

	Noeud() {}
	
	Noeud(Type_noeud type, String valeur) {
		this.type = type;
		this.valeur = valeur;
	}

	Noeud(Type_noeud type, Noeud enfant) {
		this.type = type;
		this.enfants = new ArrayList<Noeud>();
		this.enfants.add(enfant);
	}
	
	Noeud(Type_noeud type, String valeur, Noeud enfant) {
		this.type = type;
		this.valeur = valeur;
		this.enfants = new ArrayList<Noeud>();
		this.enfants.add(enfant);
	}
	
	Noeud(Type_noeud type, Noeud enfant1, Noeud enfant2) {
		this.type = type;
		this.enfants = new ArrayList<Noeud>();
		this.enfants.add(enfant1);
		this.enfants.add(enfant2);
	}
}
