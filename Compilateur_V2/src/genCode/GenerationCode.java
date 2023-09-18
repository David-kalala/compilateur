package genCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import other.Noeud;
import type.Type_noeud;
import type.Type_symbole;

public class GenerationCode {

	PrintWriter writerASM;
	String programmeName;
	int nbLabel = 0;
	int label_continue = 0;
	int label_break =0;

	public GenerationCode() {
		try {
			this.writerASM = new PrintWriter(new File("src/code_assembleur.asm"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setFilesNames(String programmeName) {
		this.programmeName = programmeName;
	}
	
	public void startGenCode() {
		writerASM.print(".start \n");
		writerASM.print("prep init \n");
		writerASM.print("call 0 \n");
		writerASM.print("prep main \n");
		writerASM.print("call 0 \n");
		writerASM.print("halt \n");
	}

	public void close() {
		if (writerASM != null) {
			writerASM.close();
		}
	}

	public StringBuffer chargerFichier() {
		StringBuffer sb = null;

		try {
			File file = new File(programmeName);    
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
			writerASM.print("push " + n.valeur + "\n");
		}
		else if (n.type == Type_noeud.not) {
			this.genCode(n.enfants.get(0));
			writerASM.print("not \n");
		}
		else if (n.type == Type_noeud.soustraction) {
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writerASM.print("sub \n");
		}
		else if (n.type == Type_noeud.addition) {
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writerASM.print("add \n");
		}
		else if (n.type == Type_noeud.multiplication) {
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writerASM.print("mul \n");
		}
		else if (n.type == Type_noeud.division) {
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writerASM.print("??????" + "\n");
		}
		else if (n.type == Type_noeud.moins_unaire) {
			writerASM.print("push 0 \n");
			this.genCode(n.enfants.get(0));
			writerASM.print("sub \n");
		}
		else if (n.type == Type_noeud.plus_unaire) {
			this.genCode(n.enfants.get(0));
		}
		else if (n.type == Type_noeud.pointeur_adresse) {
			this.genCode(n.enfants.get(0));
			System.out.println("ERREURE FATALE, pas implemente");
			System.exit(0);
		}
		else if (n.type == Type_noeud.et) {
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writerASM.print("and" + "\n");
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
			writerASM.print( "dbg" + "\n");
		}else if (n.type == Type_noeud.RETURN) {
			this.genCode(n.enfants.get(0));
			writerASM.print( "ret" + "\n");
		}		
		else if (n.type == Type_noeud.drop) {
			this.genCode(n.enfants.get(0));
			writerASM.print( "drop 1" + "\n");
		}
		else if (n.type == Type_noeud.declaration) {

		}
		else if (n.type == Type_noeud.block || n.type == Type_noeud.sequence) {
			for (Noeud noeud : n.enfants) {
				this.genCode(noeud);
			}
		}
		else if (n.type == Type_noeud.double_egal) { // A tester
			int l1, l2;
			l1 = nbLabel++;
			l2 = nbLabel++;
			
			this.genCode(n.enfants.get(0));
			this.genCode(n.enfants.get(1));
			writerASM.print("sub" + "\n");
			writerASM.print( "jumpf l" + l1 + "\n");
			writerASM.print( "drop 1\n");
			writerASM.print( "push 0\n");
			writerASM.print( "jump l" + l2 + "\n");
			writerASM.print( ".l" + l1 + "\n");
			writerASM.print( "push 1\n");
			writerASM.print( ".l" + l2 + "\n");
			
//			System.err.println("ERREUR FATALE : double equal not implemented yet");
//			System.exit(0);
		}
		else if (n.type == Type_noeud.different) { // A tester je ne suis pas sûr 
			System.err.println("ERREUR FATALE : different not implemented yet");
			System.exit(0);
		}
		else if (n.type == Type_noeud.inferieur) { // A tester je ne suis pas sûr 
			System.err.println("ERREUR FATALE : inferieur not implemented yet");
			System.exit(0);
		}
		else if (n.type == Type_noeud.superieur) { // A tester je ne suis pas sûr 
			System.err.println("ERREUR FATALE : superieur not implemented yet");
			System.exit(0);
		}
		else if (n.type == Type_noeud.reference) {
			if (n.symbole.type == Type_symbole.variable_locale) {
				writerASM.print( "get " + n.symbole.position + "\n");
			}
			else {
				System.err.println("ERREUR FATALE : genCode reference node, symbole type : local variable expected");
				System.exit(0);
			}
		}
		else if (n.type == Type_noeud.affectation) {
			this.genCode(n.enfants.get(1));
			writerASM.print( "dup" + "\n");

			if (n.enfants.get(0).type == Type_noeud.reference && n.enfants.get(0).symbole.type == Type_symbole.variable_locale) {
				writerASM.print( "set " + n.enfants.get(0).symbole.position + "\n");
			}
			else if(n.enfants.get(0).type == Type_noeud.indirection) {
				this.genCode(n.enfants.get(0).enfants.get(0));
				writerASM.print( "write \n");
			}
			else {
				System.err.println("ERREUR FATALE : genCode affectation");
				System.exit(0);
			}
		}
		else if (n.type == Type_noeud.condition) {
			int l1, l2;
			if(n.enfants.size() == 2) {
				l1 = nbLabel++;
				this.genCode(n.enfants.get(0));
				writerASM.print( "jumpf l" + l1 + "\n");
				this.genCode(n.enfants.get(1));
				writerASM.print( ".l" + l1 + "\n");
			}
			else { 
				l1 = nbLabel++; 
				l2 = nbLabel++;
				this.genCode(n.enfants.get(0));
				writerASM.print( "jumpf l" + l1 + "\n");
				this.genCode(n.enfants.get(1));
				writerASM.print( "jump l" + l2 + "\n");
				writerASM.print( ".l" + l1 + "\n");
				this.genCode(n.enfants.get(2));
				writerASM.print( ".l" + l2 + "\n");
			}
		}
		else if(n.type == Type_noeud.CONTINUE) {
			writerASM.print( ".l" + label_continue+ "\n");
		}
		else if(n.type == Type_noeud.BREAK) {
			writerASM.print( "jump l" + label_break + "\n");
		}
		else if(n.type == Type_noeud.target) {
			writerASM.print( "jump l" + label_continue + "\n");
		}
		else if(n.type == Type_noeud.fonction) {
			writerASM.print( "." + n.valeur + "\n");
			writerASM.print( "resn " + n.symbole.nbVars + "\n");
			this.genCode(n.enfants.get(n.enfants.size() - 1));
			writerASM.print( "push 0" + "\n");
			writerASM.print( "ret" + "\n");
		}
		else if(n.type == Type_noeud.loop) {
				label_debut = nbLabel++;
				int save_continue = label_continue;
				label_break = nbLabel++;
				label_continue = nbLabel++;
				writerASM.print( ".l" + label_debut + "\n");
				
				for(int i = 0; i < n.enfants.size(); i++) {
					this.genCode(n.enfants.get(i));
				}
				
				writerASM.print( "jump l" + label_debut + "\n");
				writerASM.print( ".l" + label_break + "\n");
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
			
			writerASM.print( "prep " + n.enfants.get(0).valeur + "\n");
			writerASM.print( "call 0 \n"); // j'ai modifiï¿½ ca 
			for (int i = 1; i < n.enfants.size(); i++) {
				this.genCode(n.enfants.get(i));
			}
			writerASM.print( "call" + (n.enfants.size() - 1) + "\n");

		}
		else if(n.type == Type_noeud.indirection) {
			this.genCode(n.enfants.get(0));
			writerASM.print( "read \n");
		}
		else {
			System.err.println("ERREUR FATALE, gen code unknown type : " + n.type);
			System.exit(0);
		}
	}
}
