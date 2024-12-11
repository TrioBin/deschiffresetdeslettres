package fr.fmuzaqi.deslettres;

import java.util.ArrayList;

public class LetterList {
    public static void main(String[] args) {
        ArrayList<String> consonne = new ArrayList<String>();
        ArrayList<String> voyelle = new ArrayList<String>();

        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        for (String letter : letters) {
            if (letter.equals("a") || letter.equals("e") || letter.equals("i") || letter.equals("o") || letter.equals("u") || letter.equals("y")) {
                voyelle.add(letter);
            } else {
                consonne.add(letter);
            }
        }
        
        System.out.println("Voyelles : " + voyelle);
        System.out.println("Consonnes : " + consonne);
    }
}
