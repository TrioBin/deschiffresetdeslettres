package fr.fmuzaqi.deslettres;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class LetterList {
    public static void tirages(char[] lettres_finales) {
        int i, j, l;
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
        // System.out.print(lettres_finales[i] + " ");
        // }
    }

    public static void validité(char[] lettres, String mot) {
        int length, i, k = 0;
        String MOT;
        char[] mot_formé = new char[10]; // tableau qui va contenir les lettres du mot saisi par l'utilisateur
        System.out.println("Saisir le mot le plus long possible à partir des lettres tirées");
        length = mot.length(); // On récupère la longueur du mot saisi par l'utilisateur
        MOT = mot; // On stocke le mot saisi par l'utilisateur dans la variable MOT
        for (i = 0; i < length; i++) {
            mot_formé[i] = mot.charAt(i); // On ajoute chaque lettre du mot saisi par l'utilisateur dans le tableau
                                          // mot_formé
        }
        for (i = length; i < 10; i++) {
            mot_formé[i] = '0'; // On complète le tableau mot_formé avec le charactère '0' pour avoir un tableau
                                // de 10 cases
        }
        for (i = 0; i < length; i++) {
            for (int j = 0; j < 10; j++) {
                if (mot_formé[i] == lettres[j]) { // On vérifie si chaque lettre du mot saisi par l'utilisateur est bien
                                                  // présente dans le tableau lettres
                    mot_formé[i] = '0'; // Si la lettre est présente, on la remplace par le charactère '0'
                    lettres[j] = '0'; // On remplace la lettre du tableau lettres par le charactère '0' pour ne pas la
                                      // réutiliser
                }
            }
        }
        for (i = 0; i < 10; i++) {
            if (mot_formé[i] == '0') { // On compte le nombre de charactère '0' dans le tableau mot_formé
                k = k + 1;
            }
        }
        String Dictionnaire = "Dictionnaire.txt";
        try {
            File fichier = getFileFromResource(Dictionnaire); // On crée un objet File pour représenter le fichier à
                                                              // lire.
            Scanner sc = new Scanner(fichier); // On crée un objet de type Scanner pour lire le fichier Dictionnaire.txt
            while (sc.hasNextLine()) {
                String ligne = sc.nextLine(); // On lit chaque ligne du fichier Dictionnaire.txt
                if (ligne.equals(MOT.toLowerCase()) && (k == 10)) { // On vérifie si le mot saisi par l'utilisateur est
                                                                    // présent dans le fichier et si le mot est bien
                                                                    // formé à partir des lettres tirées
                    System.out.println("Le mot " + MOT + " est bien présent dans le dictionnaire");
                    System.out.println("Félicitations, vous avez formé un mot de " + length + " lettres valide");
                } else if (ligne.equals(MOT.toLowerCase()) && (k != 10)) {
                    System.out.println("Le mot " + MOT
                            + " est bien présent dans le dictionnaire mais il n'est pas formé à partir des lettres tirées");
                } else if (!ligne.equals(MOT.toLowerCase()) && (k == 10)) {
                    System.out.println("Le mot " + MOT
                            + " n'est pas présent dans le dictionnaire mais il est bien formé à partir des lettres tirées");
                } else if (!ligne.equals(MOT.toLowerCase()) && (k != 10)) {
                    System.out.println("Le mot " + MOT
                            + " n'est pas présent dans le dictionnaire et il n'est pas formé à partir des lettres tirées");
                }
                sc.close(); // On ferme le scanner
            }
        } catch (Exception e) {
            System.out.println("Erreur le dictionnaire n'a pas été trouvé");
        }
    }

    private static File getFileFromResource(String fileName) throws URISyntaxException { // Méthode pour récupérer un
                                                                                         // fichier à partir de son nom
        ClassLoader classLoader = LetterList.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            // return new File(resource.getFile());

            return new File(resource.toURI());
        }
    }
}
