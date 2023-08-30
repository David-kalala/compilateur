package compilateur;

public enum Type {
	constante, identificateur, plus, moins, exclamation, parenthese_gauche, parenthese_droite, acolade_gauche, acolade_droite,
	crochet_gauche, crochet_droit, inferieur, superieur, /*superieur_egal, inferieur_egal*/ egal, /*double_egal,*/  virgule, slash, 
	esperluette, /*double_esperluette,pas_egal,*/  point_virgule, while_token, break_token, return_token, int_token, continue_token,
	if_token, EOF;
}
