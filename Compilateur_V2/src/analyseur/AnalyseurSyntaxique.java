package analyseur;

import java.util.ArrayList;

import other.Noeud;
import other.Operateur;
import type.Type_noeud;
import type.Type_token;

public class AnalyseurSyntaxique {

	AnalyseurLexicale analyseurLexicale; 
	ArrayList<Operateur> listeOperateurs;

	
	public AnalyseurSyntaxique(AnalyseurLexicale analyseurLexicale) {
		listeOperateurs = new ArrayList<Operateur>();
		this.analyseurLexicale = analyseurLexicale;
		this.init_operateurs();
	}
	
	public Noeud run_analyseSyntaxique() {
		Noeud n;
		
		this.analyseurLexicale.accept(Type_token.INT);
		this.analyseurLexicale.accept(Type_token.identificateur);
		n = new Noeud(Type_noeud.fonction, this.analyseurLexicale.previous_token.valeur);
		this.analyseurLexicale.accept(Type_token.parenthese_gauche);
		
		while(this.analyseurLexicale.check(Type_token.INT)) {
			this.analyseurLexicale.accept(Type_token.identificateur);
			n.enfants.add(new Noeud(Type_noeud.declaration, this.analyseurLexicale.previous_token.valeur));
			if (this.analyseurLexicale.check(Type_token.virgule)) {
				continue;
			}
			else {
				break;
			}
		}
		
		this.analyseurLexicale.accept(Type_token.parenthese_droite);
		Noeud i = instruction();
		n.enfants.add(i);
		
		return n;
	}
	
	Noeud instruction() {
		Noeud n;
		
		if (this.analyseurLexicale.check(Type_token.point_virgule)) {
			return new Noeud(Type_noeud.vide);
		}
		else if (this.analyseurLexicale.check(Type_token.acolade_gauche)) {
			n = new Noeud(Type_noeud.block);
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
		else if (this.analyseurLexicale.check(Type_token.RETURN)) {
			n = expression(0);
			this.analyseurLexicale.accept(Type_token.point_virgule);
			return new Noeud(Type_noeud.RETURN, n);
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
		else if (this.analyseurLexicale.check(Type_token.IF)) {
			this.analyseurLexicale.accept(Type_token.parenthese_gauche);
			Noeud e = this.expression(0);
			this.analyseurLexicale.accept(Type_token.parenthese_droite);
			Noeud i1 = this.instruction();
			
			Noeud i2 = null;
			if (this.analyseurLexicale.check(Type_token.ELSE)) {
				i2 = instruction();
			}
			
			n = new Noeud(Type_noeud.condition, e, i1);
			if (i2 != null) {
				n.enfants.add(i2);
			}
			
			return n;
		}
		else if (this.analyseurLexicale.check(Type_token.WHILE)) {
			this.analyseurLexicale.accept(Type_token.parenthese_gauche);
			Noeud e = this.expression(0);
			this.analyseurLexicale.accept(Type_token.parenthese_droite);
			Noeud instruction = this.instruction();
			Noeud loop = new Noeud(Type_noeud.loop);
			Noeud condition = new Noeud(Type_noeud.condition);
			
			loop.enfants.add(new Noeud(Type_noeud.target));
			loop.enfants.add(condition);
			condition.enfants.add(e);
			condition.enfants.add(instruction);
			condition.enfants.add(new Noeud(Type_noeud.BREAK));
			
			return loop;
		}
        else if (this.analyseurLexicale.check(Type_token.FOR)) {
            this.analyseurLexicale.accept(Type_token.parenthese_gauche);
            Noeud e1 = this.expression(0);
            this.analyseurLexicale.accept(Type_token.point_virgule);
            Noeud e2 = this.expression(0);
            this.analyseurLexicale.accept(Type_token.point_virgule);
            Noeud e3 = this.expression(0);
            this.analyseurLexicale.accept(Type_token.parenthese_droite);

            Noeud sequence1 = new Noeud(Type_noeud.sequence);
            Noeud sequence2 = new Noeud(Type_noeud.sequence);
            Noeud instruction = this.instruction();
            Noeud loop = new Noeud(Type_noeud.loop);
            Noeud condition = new Noeud(Type_noeud.condition);
            Noeud target = new Noeud(Type_noeud.target);
            Noeud drop1 = new Noeud(Type_noeud.drop);
            Noeud drop2 = new Noeud(Type_noeud.drop);

            sequence1.enfants.add(drop1);
            sequence1.enfants.add(loop);
            drop1.enfants.add(e1);
            loop.enfants.add(condition);

            condition.enfants.add(e2);
            condition.enfants.add(sequence2);
            condition.enfants.add(new Noeud(Type_noeud.BREAK));

            sequence2.enfants.add(instruction);
            sequence2.enfants.add(target);
            sequence2.enfants.add(drop2);

            drop2.enfants.add(e3);

            return sequence1;
        }
		else if (this.analyseurLexicale.check(Type_token.INT)) {
			n = new Noeud(Type_noeud.sequence);
			do {
				this.analyseurLexicale.accept(Type_token.identificateur);
				n.enfants.add(new Noeud(Type_noeud.declaration, this.analyseurLexicale.previous_token.valeur));
			} while(this.analyseurLexicale.check(Type_token.virgule));
			
			this.analyseurLexicale.accept(Type_token.point_virgule);
			return n;
		}
		else if(this.analyseurLexicale.check(Type_token.BREAK)) {
			this.analyseurLexicale.accept(Type_token.point_virgule);
			return new Noeud(Type_noeud.BREAK); // a verifier 
		}
		else if(this.analyseurLexicale.check(Type_token.continue_token)) {
			this.analyseurLexicale.accept(Type_token.point_virgule);
			return new Noeud(Type_noeud.CONTINUE); // a verifier 
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
				n = new Noeud(op.token, n, m);
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
		else if (analyseurLexicale.check(Type_token.etoile)) {
			n = prefixe();
			return new Noeud (Type_noeud.indirection, analyseurLexicale.previous_token.valeur, n);
		}
		else {
			return suffixe();
		}
	}
	
	Noeud suffixe() {
		Noeud n1 = atome();
		
		if (analyseurLexicale.check(Type_token.parenthese_gauche)) {
			Noeud n2 = new Noeud(Type_noeud.appel, n1);
			while (!analyseurLexicale.check(Type_token.parenthese_droite)) {
				n2.enfants.add(this.expression(0));
				if (analyseurLexicale.check(Type_token.parenthese_droite)) {
					break;
				}
				analyseurLexicale.check(Type_token.virgule);
			}
			
			return n2;
		}
		else if (analyseurLexicale.check(Type_token.crochet_gauche)) {
			Noeud n3 = expression(0);
			analyseurLexicale.accept(Type_token.crochet_droit);
			
			Noeud add = new Noeud(Type_noeud.addition,n1,n3);
			Noeud ind = new Noeud(Type_noeud.indirection,add);
			return ind;
		}
 
		return n1;
	}
	
	Noeud atome() {
		Noeud n;
		
		if (analyseurLexicale.check(Type_token.constante)) {
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
		else {
			System.err.println("ERREUR FATALE atome, current_token type not found : " + analyseurLexicale.current_token.type);
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
		listeOperateurs.add(new Operateur(Type_token.simple_egal, Type_noeud.affectation, 1, 1));
		listeOperateurs.add(new Operateur(Type_token.double_barre_verticale, Type_noeud.ou, 2, 0));
		listeOperateurs.add(new Operateur(Type_token.double_esperluette, Type_noeud.et, 3, 0));
		listeOperateurs.add(new Operateur(Type_token.double_egal, Type_noeud.double_egal, 4, 0));
		listeOperateurs.add(new Operateur(Type_token.different, Type_noeud.different, 4, 0));
		listeOperateurs.add(new Operateur(Type_token.inferieur, Type_noeud.inferieur, 5, 0));
		listeOperateurs.add(new Operateur(Type_token.superieur, Type_noeud.superieur, 5, 0));
		listeOperateurs.add(new Operateur(Type_token.superieur_egal, Type_noeud.superieur_egal, 5, 0));
		listeOperateurs.add(new Operateur(Type_token.inferieur_egal, Type_noeud.inferieur_egal, 5, 0));
		listeOperateurs.add(new Operateur(Type_token.plus, Type_noeud.addition, 6, 0));
		listeOperateurs.add(new Operateur(Type_token.moins, Type_noeud.soustraction, 6, 0));
		listeOperateurs.add(new Operateur(Type_token.etoile, Type_noeud.multiplication, 7, 0));
		listeOperateurs.add(new Operateur(Type_token.slash, Type_noeud.division, 7, 0));
		listeOperateurs.add(new Operateur(Type_token.pourcentage, Type_noeud.modulo, 7, 0));
	}
}
