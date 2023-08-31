package compilateur;

import compilateur.type.Type_noeud;
import compilateur.type.Type_token;

public class Operateur {

	Type_token cle;
	Type_noeud token;
	int priorite;
	int assoDroite;
	
	public Operateur(Type_token cle, Type_noeud token, int priorite, int assoDroite) {
		super();
		this.cle = cle;
		this.token = token;
		this.priorite = priorite;
		this.assoDroite = assoDroite;
	}
}