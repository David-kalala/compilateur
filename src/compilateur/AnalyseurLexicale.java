package compilateur;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import compilateur.type.Type_token;

public class AnalyseurLexicale {

	Token current_token;
	Token previous_token;
	String programme;
	int index = 0;
	ArrayList<Token> token_liste;

	AnalyseurLexicale(String programme) {
		this.programme = programme;
		token_liste = new ArrayList<Token>();
//		current_token = new Token("START", Type_token.START);
//		previous_token = new Token("START", Type_token.START);
	}

//	void runAnalyse() {
//		while (index < programme.length()) {
//			next();
//			token_liste.add(current_token);
//		}
//	}

	void afficheListeTokens() {
		System.out.println("Nombre de tokens dans la liste : " + token_liste.size());
		for (Token t : token_liste) {
			System.out.println("Valeur : " + t.valeur + " | Type : " + t.type);
			System.out.println();
		}
	}

	boolean check(Type_token type) {
		if (current_token.type == type) {
			next();
			return true;
		}
		else {
			return false;
		}
	}

	void accept(Type_token type) {
		if (!check(type)) {
			System.err.println("check : ERREUR FATALE !");
			System.exit(0);
		}
	}

	void next() {
		previous_token = current_token;
		current_token = this.lectureProgramme();
		token_liste.add(current_token);
		System.out.println("current_token : " + current_token.type);
	}
	
//	boolean isKeyword(String keyword) {
//		if(index + keyword.length() <= programme.length() && programme.substring(index, index + keyword.length()).equals(keyword)) {
//			char nextChar = (index + keyword.length() < programme.length()) ? programme.charAt(index + keyword.length()) :' ';
//			return !Character.isLetterOrDigit(nextChar);
//		}
//		else {
//			return false;
//		}
//	}

	Token lectureProgramme() {
		Token tokenTmp = null;
		
		while (index < programme.length() && programme.charAt(index) == ' ') {
			index++;
		}
		
//		System.out.println("Index courant : " + index);
		
		if (index >= programme.length()) {
			return new Token("EOF", Type_token.EOF);
		}
//		else if (isKeyword("if")) {
//			tokenTmp = new Token("if", Type_token.plus);
//			index+=2;
//			return tokenTmp;
//		}
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
				System.out.println("IN");
				tokenTmp = new Token(numberBuilder.toString(), Type_token.double_esperluette);
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
		// else if (Pattern.matches(".*\\bif\\b.*", Character.toString(programme.charAt(index)))) {
//		else if (index + 1 < programme.length() && programme.charAt(index) == 'i' && programme.charAt(index + 1) == 'f' && Pattern.matches(".*[ (].*", Character.toString(programme.charAt(index+2)))) {
////			StringBuilder ifBuilder = new StringBuilder();
////			while (index < programme.length() && !Character.isSpaceChar(programme.charAt(index))){
////				ifBuilder.append(programme.charAt(index));
////				index++;
////			}
//			tokenTmp = new Token("if", Type.if_token);
//			index += 2;
//			return tokenTmp;
//		}
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
			System.out.println("ERREUR FATALE LECTURE PROGRAMME");
			System.exit(0);
			return null;
		}
		return null;
	}
}
