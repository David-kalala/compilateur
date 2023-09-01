package compilateur;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import compilateur.type.Type_noeud;

public class GenerationCode {
	
	String generationCode = "";
    PrintWriter writer;

    // Constructeur qui ouvre le fichier pour l'écriture
    public GenerationCode(String filename) throws IOException {
        this.writer = new PrintWriter(new File(filename));
    }

    // Fermez le fichier à la fin
    public void close() {
        if (writer != null) {
            writer.close();
        }
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
		else {
			System.err.println("ERREURE FATALE : type non reconnu");
			System.exit(0);
		}
	}
}
