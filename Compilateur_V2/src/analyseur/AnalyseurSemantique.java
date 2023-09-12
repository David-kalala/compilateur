package analyseur;

import java.util.ArrayList;

import other.Noeud;
import other.Symbole;
import type.Type_symbole;

public class AnalyseurSemantique {
	
	ArrayList<Symbole> listeVariables;
	AnalyseurSyntaxique analyseSyntaxique;
	int nbVariables;
	
	public AnalyseurSemantique(AnalyseurSyntaxique analyseSyntaxique) {
		listeVariables = new ArrayList<Symbole>();
		this.analyseSyntaxique = analyseSyntaxique;
	}
	
	public void run_analyseSemantique(Noeud n) {
		switch(n.type) {
			default : 
				for (Noeud noeud : n.enfants) {
					this.run_analyseSemantique(noeud);
				} 
				break;
				
			case block :
				this.begin();
				for(Noeud noeud: n.enfants) {
					this.run_analyseSemantique(noeud);
				}
				this.end();
				break;
				
			case reference :
				n.symbole = this.chercher(n.valeur);
				break;	
				
			case declaration :
				Symbole s = this.declarer(n.valeur);
				s.position = nbVariables;
				nbVariables++;
				s.type = Type_symbole.variable_locale;
				break;	
			
			case fonction :
				nbVariables = 0;
				this.begin();
				
				for (int i = 0; i < n.enfants.size(); i++) {
					this.run_analyseSemantique(n.enfants.get(i));
				}
				
				this.end();
				Symbole s1 = this.declarer(n.valeur);
				s1.type = Type_symbole.fonction;
				s1.nbVars = nbVariables - (n.enfants.size() - 1);
				n.symbole = s1;
				break;
		}
	}
	
	public void resetNbVariables() {
		nbVariables = 0;
	}

	Symbole chercher(String nom) {
		for (int i = listeVariables.size() - 1; i >= 0; i--) {
			if (listeVariables.get(i).nom.equals(nom)) {
				return listeVariables.get(i);
			}
		}
		
		System.err.println("ERREURE FATALE : chercher");
		System.exit(0);
		return null;
	}
	
	Symbole declarer(String nom) {
		for (int i = listeVariables.size() - 1; i >= 0; i--) {
			if (listeVariables.get(i).nom.equals(nom)) {
				System.err.println("ERREURE FATALE : declarer");
				System.exit(0);
			}
			if (listeVariables.get(i).nom.equals("")) {
				break;
			}
		}
		
		Symbole s = new Symbole(nom);
		listeVariables.add(s);
		return s;
	}
	
	void begin() {
		listeVariables.add(new Symbole(""));
	}
	
	void end() {
		while (!listeVariables.get(listeVariables.size()-1).nom.equals("")) {
			listeVariables.remove(listeVariables.size()-1);
		}
	}
}
