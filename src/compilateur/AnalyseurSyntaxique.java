package compilateur;

import java.util.ArrayList;

import compilateur.type.Type_noeud;
import compilateur.type.Type_token;

public class AnalyseurSyntaxique {

	AnalyseurLexicale analyseurLexicale; 
	ArrayList<Operateur> listeOperateurs;
	
	AnalyseurSyntaxique(AnalyseurLexicale analyseurLexicale) {
		this.analyseurLexicale = analyseurLexicale;
		listeOperateurs = new ArrayList<Operateur>();
		this.init_operateurs();
	}
	
	Noeud run_analyse() {
		return expression(0);		
	}
	
	Noeud expression(int priorite_min) {
		Noeud n = prefixe();
		
		if (this.getOperateurFromListe(analyseurLexicale.current_token.type) == null) {
			System.out.println("renvoi null");
		}
		if (!this.operateurContainType(analyseurLexicale.current_token.type)) {
			System.out.println("Token n'exite pas ");
		}
		System.out.println("analyseurLexicale.current_token.type : " + analyseurLexicale.current_token.type);
		
		while (this.operateurContainType(analyseurLexicale.current_token.type)) {
			Operateur op = getOperateurFromListe(analyseurLexicale.current_token.type);
			System.out.println(op);
			
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
			return new Noeud(Type_noeud.moins_unaire, n);
		}
		else if (analyseurLexicale.check(Type_token.exclamation)) {
			n = prefixe();
			return new Noeud(Type_noeud.not, n);
		}
		else if (analyseurLexicale.check(Type_token.plus)) {
			return prefixe();
		}
		else {
			return atome();
		}
	}
	
	Noeud atome() {
		Noeud n;
		
		if (analyseurLexicale.check(Type_token.EOF)) {
			return new Noeud (Type_noeud.EOF, analyseurLexicale.previous_token.valeur);
		}
		else if (analyseurLexicale.check(Type_token.constante)) {
			return new Noeud (Type_noeud.constante, analyseurLexicale.previous_token.valeur);
		}
		else if (analyseurLexicale.check(Type_token.identificateur)) {
//			System.out.println("Not yet done");
//			System.exit(0);
			return new Noeud (Type_noeud.identificateur, analyseurLexicale.previous_token.valeur);
		}
		else if (analyseurLexicale.check(Type_token.parenthese_gauche)) {
			n = expression(0);
			analyseurLexicale.accept(Type_token.parenthese_droite);
			return n;
		}
		else {
			System.out.println("Erreur fatale");
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
		listeOperateurs.add(new Operateur(Type_token.et, Type_noeud.et, 3, 0));
		listeOperateurs.add(new Operateur(Type_token.double_egal, Type_noeud.double_egale, 4, 0));
		listeOperateurs.add(new Operateur(Type_token.different, Type_noeud.different, 4, 0));
		listeOperateurs.add(new Operateur(Type_token.inferieur, Type_noeud.inferieur, 5, 0));
		listeOperateurs.add(new Operateur(Type_token.superieur, Type_noeud.superieur, 5, 0));
		listeOperateurs.add(new Operateur(Type_token.plus, Type_noeud.plus, 6, 0));
		listeOperateurs.add(new Operateur(Type_token.moins, Type_noeud.moins, 6, 0));
		listeOperateurs.add(new Operateur(Type_token.etoile, Type_noeud.multiplication, 7, 0));
		listeOperateurs.add(new Operateur(Type_token.slash, Type_noeud.divise, 7, 0));
		listeOperateurs.add(new Operateur(Type_token.pourcentage, Type_noeud.modulo, 7, 0));

	}
}
