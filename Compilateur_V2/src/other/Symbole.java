package other;

import type.Type_symbole;

public class Symbole {

	public String nom;
	public String valeur;
	public int position;
	public Type_symbole type;
	public int nbVars;
	
	Symbole(String nom, String valeur) {
		this.nom = nom;
		this.valeur = valeur;
	}
	
	public Symbole(String nom) {
		this.nom = nom;
	}
}
