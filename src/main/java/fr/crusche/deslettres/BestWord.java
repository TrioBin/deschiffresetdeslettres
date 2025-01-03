package fr.crusche.deslettres;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import fr.fmuzaqi.deslettres.LetterList;

public class BestWord {
    public static String getBestWord(char[] tirage, float difficulity) {
        ArrayList<String> possibilities = new ArrayList<String>();

        String Dictionnaire = "Dictionnaire.txt";
        File fichier = null;
        try {
            fichier = getFileFromResource(Dictionnaire); // On crée un objet File pour représenter le fichier à lire.
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Scanner sc = null;
        try {
            sc = new Scanner(fichier); // On crée un objet de type Scanner pour lire le fichier Dictionnaire.txt
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()) {
            String ligne = sc.nextLine().toLowerCase(); // On lit chaque ligne du fichier Dictionnaire.txt
            char[] temp = tirage.clone();
            int validité = 0;
            // System.out.println(Arrays.toString(tirage));
            for (int i = 0; i < ligne.length(); i++) {
                for (int j = 0; j < temp.length; j++) {
                    // System.out.println(ligne.charAt(i) + " " + temp[j]);
                    if (ligne.charAt(i) == temp[j]) {
                        temp[j] = '0';
                        validité += 1;
                    }
                }
            }
            // System.out.println(validité + " " + ligne.length());
            if (validité == ligne.length()) {
                System.out.println(ligne);
                if (possibilities.size() == 0) {
                    possibilities.add(ligne);
                } else {
                    if (Math.random() < difficulity * (1 + difficulity)) {
                        if (ligne.length() == possibilities.get(0).length()) {
                            possibilities.add(ligne);
                        } else if (ligne.length() > possibilities.get(0).length()) {
                            possibilities.clear();
                            possibilities.add(ligne);
                        }
                    }
                }
            }
        }
        sc.close(); // On ferme le scanner

        return possibilities.get(0);
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
