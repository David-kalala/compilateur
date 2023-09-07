package compilateur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import compilateur.type.Type_noeud;
import compilateur.type.Type_token;

public class AnalyseurSyntaxique {

	AnalyseurLexicale analyseurLexicale; 
	ArrayList<Operateur> listeOperateurs;

	
	AnalyseurSyntaxique(AnalyseurLexicale analyseurLexicale) {
		listeOperateurs = new ArrayList<Operateur>();
		this.analyseurLexicale = analyseurLexicale;
		this.init_operateurs();
	}
	
	Noeud run_analyseSyntaxique() {
		return instruction();
	}
	
	Noeud instruction() {
		Noeud n;
		
		if (this.analyseurLexicale.check(Type_token.point_virgule)) {
			return new Noeud(Type_noeud.vide);
		}
		else if (this.analyseurLexicale.check(Type_token.acolade_gauche)) {
			n = new Noeud(Type_noeud.bloc);
			while (!this.analyseurLexicale.check(Type_token.acolade_droite)) {
				n.enfants.add(instruction());
			}
			return n;
		}
		else if (this.analyseurLexicale.check(Type_token.debug)) {
			n = expression(0);
			this.analyseurLexicale.accept(Type_token.point_virgule);
			return new Noeud(Type_noeud.debug, n);
		}
		else if (this.analyseurLexicale.check(Type_token.virgule)) {
			n = new Noeud (Type_noeud.sequence);
			
			do {
				this.analyseurLexicale.accept(Type_token.identificateur);
				n.enfants.add(new Noeud(Type_noeud.declaration, this.analyseurLexicale.previous_token.valeur));
			} while(this.analyseurLexicale.check(Type_token.virgule));
			
			this.analyseurLexicale.accept(Type_token.point_virgule);
			return n;
		}
		else {
			n = expression(0);
			this.analyseurLexicale.accept(Type_token.point_virgule);
			return new Noeud(Type_noeud.drop, n);
		}
	}
	
	Noeud expression(int priorite_min) {
		Noeud n = prefixe();
		
		while (this.operateurContainType(analyseurLexicale.current_token.type)) {
			Operateur op = getOperateurFromListe(analyseurLexicale.current_token.type);
			
			if (op.priorite > priorite_min) {
				analyseurLexicale.next();
				Noeud m = expression(op.priorite - op.assoDroite);
				n = new Noeud (op.token, n, m);
			}
			else {
				break;
			}
		}
		
		return n;
	}
	
	Noeud prefixe() {
		Noeud n;
		
		if (analyseurLexicale.check(Type_token.moins)) {
			n = prefixe();
			return new Noeud(Type_noeud.moins_unaire, "-", n);
		}
		else if (analyseurLexicale.check(Type_token.exclamation)) {
			n = prefixe();
			return new Noeud(Type_noeud.not, n);
		}
		else if (analyseurLexicale.check(Type_token.plus)) {
			return prefixe();
		}
		else if (analyseurLexicale.check(Type_token.simple_esperluette)) {
			n = prefixe();
			return new Noeud (Type_noeud.pointeur_adresse, analyseurLexicale.previous_token.valeur, n);
		}
		else {
			return atome();
		}
	}
	
	Noeud atome() {
		Noeud n;
		
		if (analyseurLexicale.check(Type_token.double_esperluette)) {
			System.out.println("DOUBLE ESPER : " + analyseurLexicale.previous_token.valeur);
			return new Noeud (Type_noeud.et, analyseurLexicale.previous_token.valeur);
		}
		else if (analyseurLexicale.check(Type_token.constante)) {
			return new Noeud (Type_noeud.constante, analyseurLexicale.previous_token.valeur);
		}
		else if (analyseurLexicale.check(Type_token.identificateur)) {
			return new Noeud (Type_noeud.reference, analyseurLexicale.previous_token.valeur);
		}
		else if (analyseurLexicale.check(Type_token.parenthese_gauche)) {
			n = expression(0);
			analyseurLexicale.accept(Type_token.parenthese_droite);
			return n;
		}
		else if (analyseurLexicale.check(Type_token.double_esperluette)) {
			System.out.println("DOUBLE ESPER : " + analyseurLexicale.previous_token.valeur);
			return new Noeud (Type_noeud.et, analyseurLexicale.previous_token.valeur);
		}
		else {
			System.err.println("atome : ERREUR FATALE");
			System.err.println("analyseurLexicale current_token type : " + analyseurLexicale.current_token.type);
			System.exit(0);
			return null;
		}
	}
	
	public boolean operateurContainType(Type_token type) {
		for (Operateur op : listeOperateurs) {
			if (op.cle.equals(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Operateur getOperateurFromListe(Type_token type) {
		for (Operateur op : listeOperateurs) {
			if (op.cle.equals(type)) {
				return op;
			}
		}
		
		System.err.println("Aucun operateur trouve");
		return null;
	}
	
	public void init_operateurs() {
		listeOperateurs.add(new Operateur(Type_token.egal, Type_noeud.egal, 1, 1));
		listeOperateurs.add(new Operateur(Type_token.ou, Type_noeud.ou, 2, 0));
		listeOperateurs.add(new Operateur(Type_token.double_esperluette, Type_noeud.et, 3, 0));
		listeOperateurs.add(new Operateur(Type_token.double_egal, Type_noeud.double_egale, 4, 0));
		listeOperateurs.add(new Operateur(Type_token.different, Type_noeud.different, 4, 0));
		listeOperateurs.add(new Operateur(Type_token.inferieur, Type_noeud.inferieur, 5, 0));
		listeOperateurs.add(new Operateur(Type_token.superieur, Type_noeud.superieur, 5, 0));
		listeOperateurs.add(new Operateur(Type_token.plus, Type_noeud.addition, 6, 0));
		listeOperateurs.add(new Operateur(Type_token.moins, Type_noeud.soustraction, 6, 0));
		listeOperateurs.add(new Operateur(Type_token.etoile, Type_noeud.multiplication, 7, 0));
		listeOperateurs.add(new Operateur(Type_token.slash, Type_noeud.division, 7, 0));
		listeOperateurs.add(new Operateur(Type_token.pourcentage, Type_noeud.modulo, 7, 0));
	}
}
