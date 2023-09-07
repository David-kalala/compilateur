package compilateur;

import compilateur.type.Type_noeud;

public class Symbole {

	String nom;
	String valeur;
	int position;
	Type_noeud type;
	
	Symbole(String nom, String valeur) {
		this.nom = nom;
		this.valeur = valeur;
	}
	
	Symbole(String nom) {
		this.nom = nom;
	}
}
