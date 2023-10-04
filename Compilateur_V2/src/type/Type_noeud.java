package type;

public enum Type_noeud {
	
	EOF,
	plus_unaire, moins_unaire, 
	constante,  identificateur, 
	
	pointeur_adresse,
	vide, bloc, debug, drop, reference, sequence, declaration, block, 
	affectation, condition, loop, BREAK, target, CONTINUE,
	fonction, appel, RETURN, indirection,
	
	// Arthmetique et logique 
	addition, multiplication, division, soustraction, modulo,
	ou, et, not,
	
	// Comparaisons
	different, inferieur, superieur, double_egal,
}
 