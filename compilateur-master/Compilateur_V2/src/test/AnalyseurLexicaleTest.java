package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import analyseur.AnalyseurLexicale;
import type.Type_token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyseurLexicaleTest {

    private AnalyseurLexicale analyseur;

    @BeforeEach
    public void setUp() {
        analyseur = new AnalyseurLexicale("");
    }
    
    @Test
    public void testKeywordInt() {
        analyseur = new AnalyseurLexicale("int");
        assertEquals(Type_token.INT, analyseur.lectureProgramme().type);
    }
    
    @Test
    public void testKeywordFloat() {
        analyseur = new AnalyseurLexicale("float");
        assertEquals(Type_token.FLOAT, analyseur.lectureProgramme().type);
    }
    
    @Test
    public void testKeywordDouble() {
        analyseur = new AnalyseurLexicale("double");
        assertEquals(Type_token.DOUBLE, analyseur.lectureProgramme().type);
    }

    @Test
    public void testKeywordReturn() {
        analyseur = new AnalyseurLexicale("return");
        assertEquals(Type_token.RETURN, analyseur.lectureProgramme().type);
    }
    
    @Test
    public void testKeyworddoubleEsperluette() {
        analyseur = new AnalyseurLexicale("&&");
        assertEquals(Type_token.double_esperluette, analyseur.lectureProgramme().type);
    }
    
    @Test
    public void testKeywordIf() {
        analyseur = new AnalyseurLexicale("if");
        assertEquals(Type_token.IF, analyseur.lectureProgramme().type);
    }

    @Test
    public void testKeywordElse() {
        analyseur = new AnalyseurLexicale("else");
        assertEquals(Type_token.ELSE, analyseur.lectureProgramme().type);
    }
    
    @Test
    public void testKeywordWhile() {
        analyseur = new AnalyseurLexicale("while");
        assertEquals(Type_token.WHILE, analyseur.lectureProgramme().type);
    }
    
    @Test
    public void testKeywordFor() {
        analyseur = new AnalyseurLexicale("for");
        assertEquals(Type_token.FOR, analyseur.lectureProgramme().type);
    }

    @Test
    public void testOperatorPlus() {
        analyseur = new AnalyseurLexicale("+");
        assertEquals(Type_token.plus, analyseur.lectureProgramme().type);
    }
    @Test
    public void testOperatorMoins() {
        analyseur = new AnalyseurLexicale("-");
        assertEquals(Type_token.moins, analyseur.lectureProgramme().type);
    }
    @Test
    public void testOperatorDivision() {
        analyseur = new AnalyseurLexicale("/");
        assertEquals(Type_token.division, analyseur.lectureProgramme().type);
    }
    @Test
    public void testOperatorMultiplication() {
        analyseur = new AnalyseurLexicale("*");
        assertEquals(Type_token.multiplication, analyseur.lectureProgramme().type);
    }
    
    @Test
    public void testSymboleParentheseGauche() {
        analyseur = new AnalyseurLexicale("(");
        assertEquals(Type_token.parenthese_gauche, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleParentheseDroite() {
        analyseur = new AnalyseurLexicale(")");
        assertEquals(Type_token.parenthese_droite, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleAcoladeGauche() {
        analyseur = new AnalyseurLexicale("{");
        assertEquals(Type_token.acolade_gauche, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleAcoladeDroite() {
        analyseur = new AnalyseurLexicale("}");
        assertEquals(Type_token.acolade_droite, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleCrochetDroit() {
        analyseur = new AnalyseurLexicale("]");
        assertEquals(Type_token.crochet_droit, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleCrochetGauche() {
        analyseur = new AnalyseurLexicale("[");
        assertEquals(Type_token.crochet_gauche, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleInferieur() {
        analyseur = new AnalyseurLexicale("<");
        assertEquals(Type_token.inferieur, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleSuperieur() {
        analyseur = new AnalyseurLexicale(">");
        assertEquals(Type_token.superieur, analyseur.lectureProgramme().type);
    }
    public void testSymboleEgal() {
        analyseur = new AnalyseurLexicale("=");
        assertEquals(Type_token.egal, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleVirgule() {
        analyseur = new AnalyseurLexicale(",");
        assertEquals(Type_token.virgule, analyseur.lectureProgramme().type);
    }
    
    public void testSymbolePointVirgule() {
        analyseur = new AnalyseurLexicale(";");
        assertEquals(Type_token.point_virgule, analyseur.lectureProgramme().type);
    }
    @Test
    public void testConstant() {
        analyseur = new AnalyseurLexicale("1234");
        assertEquals(Type_token.constante, analyseur.lectureProgramme().type);
    }
    
    public void testSymboleSimpleEsperluette() {
        analyseur = new AnalyseurLexicale("&");
        assertEquals(Type_token.simple_esperluette, analyseur.lectureProgramme().type);
    }

    
    @Test
    public void testIdentifier() {
        analyseur = new AnalyseurLexicale("variableName123");
        assertEquals(Type_token.identificateur, analyseur.lectureProgramme().type);
    }
   
}

