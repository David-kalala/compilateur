package genCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import other.Noeud;
import type.Type_noeud;
import type.Type_symbole;

public class GenerationCode {

	String generationCode = "";
	PrintWriter writer;
	String programme;
	int nbLabel = 0;
	int label_continue = 0;
	int label_break =0;

	public GenerationCode(String filename, String programme) throws IOException {
		this.writer = new PrintWriter(new File(filename));
		this.programme = programme;
		writer.print(".start \n");
	}
	
	public void endGenCode() {
		writer.print("halt \n");
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
		int label_debut =0;
		
		System.out.println("Type noeud genCode : " + n.type);
		
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
			System.err.println("ERREUR FATALE genCode, pointer not implemented");
			System.exit(0);
		}
		else if (n.type == Type_noeud.et) {
			System.err.println("ERREUR FATALE genCode, 'and' not implemented and do not have son");
			System.exit(0);
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
			writer.print( "dbg" + "\n");
		}
		else if (n.type == Type_noeud.drop) {
			this.genCode(n.enfants.get(0));
			writer.print( "drop" + "\n");
		}
		else if (n.type == Type_noeud.declaration) {

		}
		else if (n.type == Type_noeud.block || n.type == Type_noeud.sequence) {
			System.err.println("ERREURE FATALE genCode, node block and sequence not implemented");
			System.exit(0);
			//writer.print( "drop" + "\n");
		}
		else if (n.type == Type_noeud.reference) {
			if (n.symbole.type == Type_symbole.variable_locale) {
				writer.print( "get" + n.symbole.position + "\n");
			}
			else {
				System.err.println("ERREUR FATALE : genCode reference");
				System.exit(0);
			}
		}
		else if (n.type == Type_noeud.affectation) {
			this.genCode(n.enfants.get(1));
			writer.print( "drop" + "\n");
			if (n.enfants.get(0).type != Type_noeud.reference) {
				System.err.println("ERREUR FATALE : genCode affectation");
				System.exit(0);
			}
			else if (n.enfants.get(0).symbole.type == Type_symbole.variable_locale) {
				writer.print( "set " + n.enfants.get(0).symbole.type + "\n");
			}
			else {
				System.err.println("ERREUR FATALE : genCode affectation else");
				System.exit(0);
			}
		}
		else if (n.type == Type_noeud.condition) {
			int l1, l2;
			if(n.enfants.size() == 2) {
				l1 = nbLabel++;
				this.genCode(n.enfants.get(0));
				writer.print( "jumpf l" + l1 + "\n");
				this.genCode(n.enfants.get(1));
				writer.print( ".l" + l1 + "\n");
			}
			else { 
				l1 = nbLabel++; 
				l2 = nbLabel++;
				System.out.println("SIZE : " + n.enfants.size());
				this.genCode(n.enfants.get(0));
				writer.print( "jumpf l" + l1 + "\n");
				this.genCode(n.enfants.get(1));
				writer.print( "jump l" + l2 + "\n");
				writer.print( ".l" + l1 + "\n");
				this.genCode(n.enfants.get(2));
				writer.print( ".l" + l2 + "\n");
			}
		}
		else if(n.type == Type_noeud.CONTINUE) {
			writer.print( ".l" + label_continue+ "\n");

		}
		else if(n.type == Type_noeud.BREAK) {
			writer.print( "jump l" + label_break + "\n");
		}
		else if(n.type == Type_noeud.target) {
			writer.print( "jump l" + label_continue + "\n");
		}
		else if(n.type == Type_noeud.fonction) {
			writer.print( "." + n.valeur + "\n");
			writer.print( "resn " + n.symbole.nbVars + "\n");
			this.genCode(n.enfants.get(n.enfants.size() - 1));
			writer.print( "push 0" + "\n");
			writer.print( "ret" + "\n");
		}
		else if(n.type == Type_noeud.loop) {
				label_debut = nbLabel++;
				int save_continue = label_continue;
				label_break = nbLabel++;
				label_continue = nbLabel++;
				writer.print( ".l" + label_debut + "\n");
				for(int i = 0; i < n.enfants.size(); i++) {
					this.genCode(n.enfants.get(i));
				}
				writer.print( "jump l" + label_debut + "\n");
				writer.print( ".l" + label_break + "\n");
				label_continue = save_continue;
		}
		else if(n.type == Type_noeud.appel) {
			if(n.enfants.get(0).type != Type_noeud.reference) {
				System.err.println("ERREUR FATALE : gen code, noeud appel, type reference exprected");
				System.exit(0);
			}
			else if(n.enfants.get(0).symbole.type != Type_symbole.fonction) {
				System.err.println("ERREUR FATALE : gen code, noeud appel, type fonction exprected");
				System.exit(0);
			}
			writer.print( "prep" + n.enfants.get(0).valeur + "\n");
			writer.print( "call 0" + label_break + "\n");
			for (int i = 1; i < n.enfants.size(); i++) {
				this.genCode(n.enfants.get(i));
			}
			writer.print( "call" + (n.enfants.size() - 1) + "\n");

	}
		else {
			System.err.println("ERREUR FATALE : gen code unknown type");
			System.exit(0);
		}
	}
}
