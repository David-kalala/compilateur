package type;

public enum Type_noeud {
	EOF,
	addition, multiplication, division, soustraction, modulo,
	plus_unaire, moins_unaire, not,
	constante,  identificateur, 
	ou, et, different, inferieur, superieur,
	pointeur_adresse,
	vide, bloc, debug, drop, reference, sequence, declaration, block, 
	affectation, condition, loop, BREAK, target, CONTINUE,
	fonction, appel, RETURN, indirection, double_egal
}
 