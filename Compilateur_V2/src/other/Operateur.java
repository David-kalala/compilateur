package other;

import type.Type_noeud;
import type.Type_token;

public class Operateur {

	public Type_token cle;
	public Type_noeud token;
	public int priorite;
	public int assoDroite;
	
	public Operateur(Type_token cle, Type_noeud token, int priorite, int assoDroite) {
		super();
		this.cle = cle;
		this.token = token;
		this.priorite = priorite;
		this.assoDroite = assoDroite;
	}
}