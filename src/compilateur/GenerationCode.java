package compilateur;

import compilateur.type.Type_noeud;

public class GenerationCode {
	
	void genCode(Noeud n) {
		if (n.type == Type_noeud.constante) {
			System.out.println("push " + n.valeur);
		}
		else if (n.type == Type_noeud.not) {
			this.genCode(n.enfants.get(0));
			System.out.println("not");
		}
	}
}
