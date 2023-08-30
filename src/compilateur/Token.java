package compilateur;

public class Token {
	
	String valeur;
	Type type;
	
	Token(String valeur, Type type) {
		this.valeur = valeur;
		this.type = type;
	}
}
