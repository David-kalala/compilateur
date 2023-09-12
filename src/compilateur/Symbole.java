package compilateur;

import compilateur.type.Type_noeud;
import compilateur.type.Type_symbole;

public class Symbole {

	String nom;
	String valeur;
	int position;
	Type_symbole type;
	int nbVars;
	
	Symbole(String nom, String valeur) {
		this.nom = nom;
		this.valeur = valeur;
	}
	
	Symbole(String nom) {
		this.nom = nom;
	}
}
