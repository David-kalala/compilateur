package compilateur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import compilateur.type.Type_token;

public class Main {

	public static void main(String[] args) {

		try {
			// Le fichier d'entrée
			File file = new File("programme.txt");    
			// Créer l'objet File Reader
			FileReader fr;
			fr = new FileReader(file);
			
			// Créer l'objet BufferedReader        
			BufferedReader br = new BufferedReader(fr);  
			StringBuffer sb = new StringBuffer();    
			String line;
			
			while((line = br.readLine()) != null) {
				// ajoute la ligne au buffer
				sb.append(line);
				sb.append(" ");
			}
			
			fr.close();    
			System.out.println("Contenu du fichier: ");
			System.out.println(sb.toString()); 
			
			Noeud arbre = new Noeud();
			//AnalyseurLexicale analyseLexicale = new AnalyseurLexicale("&2 2+3");
			AnalyseurLexicale analyseLexicale = new AnalyseurLexicale(sb.toString());
			AnalyseurSyntaxique analyseSyntaxique = new AnalyseurSyntaxique(analyseLexicale);
			GenerationCode generationCode = new GenerationCode("code_assembleur.c");

			analyseLexicale.next();

			while(analyseLexicale.current_token.type != Type_token.EOF) {
				arbre = analyseSyntaxique.run_analyse();
				generationCode.genCode(arbre);
			}

			analyseLexicale.afficheListeTokens();
			
			System.out.println("Code assembleur : ");
			System.out.println(generationCode.writer.toString());
		
			generationCode.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  


	}
}
