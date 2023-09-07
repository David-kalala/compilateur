package compilateur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import compilateur.type.Type_noeud;
import compilateur.type.Type_token;

public class GenerationCode {
	
	String generationCode = "";
    PrintWriter writer;
    String programme;

    public GenerationCode(String filename, String programme) throws IOException {
        this.writer = new PrintWriter(new File(filename));
        this.programme = programme;
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
    
    public StringBuffer chargerFichier() {
    	StringBuffer sb = null;
    	
    	try {
			File file = new File(programme);    
			FileReader fr;
			fr = new FileReader(file);
			        
			BufferedReader br = new BufferedReader(fr);  
			sb = new StringBuffer();    
			String line;
			
			while((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(" ");
			}
			
			fr.close();    
			System.out.println("Contenu du fichier: ");
			System.out.println(sb.toString()); 
			
		} catch (IOException e) {
			e.printStackTrace();
		}  
    	
    	return sb;
    }
	
	public void genCode(Noeud n) {
		if (n.type == Type_noeud.constante) {
			writer.print("push " + n.valeur + "\n");
		}
		else if (n.type == Type_noeud.not) {
			this.genCode(n.enfants.get(0));
			writer.print("not" + "\n");
		}
		else if (n.type == Type_noeud.soustraction) {
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writer.print("sub" + "\n");
		}
		else if (n.type == Type_noeud.addition) {
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writer.print("add" + "\n");
		}
		else if (n.type == Type_noeud.moins_unaire) {
			this.genCode(n.enfants.get(0));
			writer.print("moins" + "\n");
		}
		else if (n.type == Type_noeud.plus_unaire) {
			this.genCode(n.enfants.get(0));
			writer.print("plus" + "\n");
		}
		else if (n.type == Type_noeud.pointeur_adresse) {
			this.genCode(n.enfants.get(0));
			System.out.println("ERREURE FATALE, pas implemente");
			System.exit(0);
			writer.print( "PAS IMPLEMENTE");
		}
		else if (n.type == Type_noeud.et) {
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writer.print( "and" + "\n");
		}
		else if (n.type == Type_noeud.vide) {

		}
		else if (n.type == Type_noeud.bloc) {
			for(Noeud noeud : n.enfants ) {
				this.genCode(noeud);
			}
		}
		else if (n.type == Type_noeud.debug) {
			this.genCode(n.enfants.get(0));
			writer.print( "dbg");
		}
		else if (n.type == Type_noeud.drop) {
			this.genCode(n.enfants.get(0));
			writer.print( "drop" + "\n");
		}
		else if (n.type == Type_noeud.declaration) {
		
		}
		else if (n.symbole.type == Type_noeud.block || n.type == Type_noeud.sequence) {
			System.err.println("ERREURE FATALE : reste a faire");
			System.exit(0);
			//writer.print( "drop" + "\n");
		}
		else if (n.type == Type_noeud.reference) {
			if (n.symbole.type == Type_noeud.variable_locale) {
				writer.print( "get" + n.symbole.position + "\n");
			}
			else {
				System.err.println("ERREURE FATALE : genCode reference");
				System.exit(0);
			}
		}
		else if (n.type == Type_noeud.affectation) {
			this.genCode(n.enfants.get(1));
			writer.print( "drop" + "\n");
			if (n.enfants.get(0).type != Type_noeud.reference) {
				System.err.println("ERREURE FATALE : genCode affectation");
				System.exit(0);
			}
			else if (n.enfants.get(0).type == Type_noeud.variable_locale) {
				writer.print( "set " + n.enfants.get(0).symbole.type + "\n");
			}
			else {
				System.err.println("ERREURE FATALE : genCode affectation else");
				System.exit(0);
			}
		}
		else {
			System.err.println("ERREURE FATALE : type non reconnu GEN CODE");
			System.exit(0);
		}
	}
}
