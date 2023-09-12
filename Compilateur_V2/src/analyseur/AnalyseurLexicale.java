package analyseur;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import other.Token;
import type.Type_token;

public class AnalyseurLexicale {

	public Token current_token;
	Token previous_token;
	String programme;
	int index = 0;
	ArrayList<Token> token_liste;

	public AnalyseurLexicale(String programme) {
		this.programme = programme;
		token_liste = new ArrayList<Token>();
	}

	void afficheListeTokens() {
		System.out.println("Nombre de tokens dans la liste : " + token_liste.size());
		for (Token t : token_liste) {
			System.out.println("Valeur : " + t.valeur + " | Type : " + t.type);
			System.out.println();
		}
	}

	public boolean check(Type_token type) {
		if (current_token.type == type) {
			next();
			return true;
		}
		else {
			return false;
		}
	}

	public void accept(Type_token type) {
		if (!check(type)) {
			System.err.println("ERREUR FATALE check : expected : " + type + ", real : " + current_token.type);
			System.exit(0);
		}
	}

	public void next() {
		previous_token = current_token;
		current_token = this.lectureProgramme();
		token_liste.add(current_token);
		System.out.println("current_token : " + current_token.type);
	}
	
	
	boolean isKeyword(String keyword) {
	    if (index + keyword.length() <= programme.length() && programme.substring(index, index + keyword.length()).equals(keyword)) {
	        char nextChar = (index + keyword.length() < programme.length()) ? programme.charAt(index + keyword.length()) : ' ';
	        return !Character.isLetterOrDigit(nextChar);
	    } else {
	        return false;
	    }
	}
	
	Token lectureProgramme() {
		Token tokenTmp = null;
		
		while (index < programme.length() && programme.charAt(index) == ' ') {
			index++;
		}
		

		
		if (index >= programme.length()) {
			return new Token("EOF", Type_token.EOF);
		}
		else if (isKeyword("if")) {
	        tokenTmp = new Token("if", Type_token.IF);
	        index += 2;
	        return tokenTmp;
	    } else if (isKeyword("else")) {
	        tokenTmp = new Token("else", Type_token.ELSE);
	        index += 4;
	        return tokenTmp;
	    } else if (isKeyword("while")) {
	        tokenTmp = new Token("while", Type_token.WHILE);
	        index += 5;
	        return tokenTmp;
	    } else if (isKeyword("for")) {
	        tokenTmp = new Token("for", Type_token.FOR);
	        index += 3;
	        return tokenTmp;
	    } else if (isKeyword("int")) {
	        tokenTmp = new Token("int", Type_token.INT);
	        index += 3;
	        return tokenTmp;
	    } else if (isKeyword("float")) {
	        tokenTmp = new Token("float", Type_token.FLOAT);
	        index += 5;
	        return tokenTmp;
	    } else if (isKeyword("double")) {
	        tokenTmp = new Token("double", Type_token.DOUBLE);
	        index += 6;
	        return tokenTmp;
	    }
	    else if (isKeyword("debug")) {
	        tokenTmp = new Token("double", Type_token.debug);
	        index += 5;
	        return tokenTmp;
	    }
		else if (programme.charAt(index) == '+') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.plus);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '-') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.moins);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '!') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.exclamation);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '(') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.parenthese_gauche);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == ')') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.parenthese_droite);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '{') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.acolade_gauche);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '}') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.acolade_droite);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '[') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.crochet_gauche);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == ']') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.crochet_droit);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '>') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.superieur);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '<') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.inferieur);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '=') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.egal);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == ',') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.virgule);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '/') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.slash);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == ';') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type_token.point_virgule);
			index++;
			return tokenTmp;
		}
		else if (Pattern.matches("[&]", Character.toString(programme.charAt(index)))) {
			StringBuilder numberBuilder = new StringBuilder();
			while (index < programme.length() && Pattern.matches("[&]", Character.toString(programme.charAt(index)))) {
				numberBuilder.append(programme.charAt(index));
				index++;
			}
			if (numberBuilder.toString().length() == 1) {
				tokenTmp = new Token(numberBuilder.toString(), Type_token.simple_esperluette);
				return tokenTmp;
			}
			else if (numberBuilder.toString().length() == 2) {
				tokenTmp = new Token(numberBuilder.toString(), Type_token.double_esperluette); // Ne fonctionne pas
				return tokenTmp;
			}
			else {
				System.out.println("ERREUR FATALE LECTURE PROGRAMME : &");
				System.exit(0);
			}
		}
		else if (Pattern.matches("[0-9]", Character.toString(programme.charAt(index)))) {
			StringBuilder numberBuilder = new StringBuilder();
			while (index < programme.length() && Character.isDigit(programme.charAt(index))) {
				numberBuilder.append(programme.charAt(index));
				index++;
			}
			tokenTmp = new Token(numberBuilder.toString(), Type_token.constante);
			return tokenTmp;
		}
		else if (Pattern.matches("[a-zA-Z0-9]", Character.toString(programme.charAt(index)))) {
			StringBuilder alphaNumeric = new StringBuilder();
			alphaNumeric.append(programme.charAt(index));
			index++;

			while (index < programme.length() && Pattern.matches("[a-zA-Z0-9]", Character.toString(programme.charAt(index)))){
				alphaNumeric.append(programme.charAt(index));
				index++;
			}
			tokenTmp = new Token(alphaNumeric.toString(), Type_token.identificateur);
			return tokenTmp;
		}
		else {
			System.err.println("ERREUR FATALE LECTURE PROGRAMME");
			System.exit(0);
			return null;
		}
		return null;
	}
}
