package compilateur;

import compilateur.type.Type_token;

public class Token {
	
	String valeur;
	Type_token type;
	
	Token(String valeur, Type_token type) {
		this.valeur = valeur;
		this.type = type;
	}
}
