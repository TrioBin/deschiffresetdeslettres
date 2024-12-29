package fr.fmuzaqi.deslettres;

import java.util.ArrayList;

public class LetterList {
    public static void tirages(char[] lettres_finales) {
        int i,j,l;
        char k;
        char[] tirage_consonnes = new char[5]; // tableau qui va contenir les 5 consonnes tirées aléatoirement
        char[] tirage_voyelles = new char[3]; // tableau qui va contenir les 3 voyelles tirées aléatoirement
        char[] tirage_lettres_supp = new char[2]; // tableau qui va contenir les 2 lettres supplémentaires tirées
                                                  // aléatoirement
        char[] consonnes1 = { 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W',
                'X', 'Z' }; // tableau des consonnes
        ArrayList<Character> consonnes2 = new ArrayList<Character>(); // On converti notre tableau consonnes1 en
                                                                      // ArrayList afin de pouvoir mélanger la liste de
                                                                      // manière aléatoire
        for (char c : consonnes1) {
            consonnes2.add(c);
        }
        java.util.Collections.shuffle(consonnes2); // On mélange la liste consonnes2 de manière aléatoire en utilisant
                                                   // la méthode shuffle de la classe Collections
        for (i = 0; i < 5; i++) {
            tirage_consonnes[i] = consonnes2.get(i); // On ajoute les 5 premières consonnes de la liste consonnes2 dans
                                                     // le tableau tirage_consonnes
            }
        char[] voyelles1 = { 'A', 'E', 'I', 'O', 'U', 'Y' }; // tableau des voyelles
        ArrayList<Character> voyelles2 = new ArrayList<Character>(); // On converti notre tableau voyelles1 en
                                                                     // ArrayList afin de pouvoir mélanger la liste de
                                                                     // manière aléatoire
        for (char c : voyelles1) {
            voyelles2.add(c);
        }
        java.util.Collections.shuffle(voyelles2); // On mélange la liste voyelles2 de manière aléatoire en utilisant
                                                  // la méthode shuffle de la classe Collections
        for (l = 0; l < 3; l++) {
            tirage_voyelles[l] = voyelles2.get(l); // On ajoute les 3 premières voyelles de la liste voyelles2 dans le
                                                   // tableau tirage_voyelles
        }
        ArrayList<Character> alphabet = new ArrayList<Character>(); // On crée une liste alphabet qui va contenir les 26
                                                                    // lettres de l'alphabet
        for (k = 'A'; k <= 'Z'; k++) { 
            alphabet.add(k);
        }
        java.util.Collections.shuffle(alphabet); // On mélange la liste alphabet de manière aléatoire
        for (j = 0; j < 2; j++) {
            tirage_lettres_supp[j] = alphabet.get(j); // On ajoute les 2 premières lettres de la liste alphabet dans le
                                                      // tableau tirage_lettres_supp
        }
        for (i = 0; i < 5; i++) {
            lettres_finales[i] = tirage_consonnes[i];
        }
        for (i = 5; i < 8; i++) {
            lettres_finales[i] = tirage_voyelles[i - 5];
        }
        for (i = 8; i < 10; i++) {
            lettres_finales[i] = tirage_lettres_supp[i - 8];
        }
        // System.out.println("Les lettres tirées sont : ");
        // for (i = 0; i < 10; i++) {
        //     System.out.print(lettres_finales[i] + " ");
        // }
    }
}
