package fr.crusche.listmanagement;

import java.util.ArrayList;

import fr.crusche.beziermanagement.Point;

public class ListManagement {
    public static <T> void ShowList(ArrayList<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\n");
        }
    }
    public static void ShowListPoint(ArrayList<Point> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getX() + " " + list.get(i).getY());
        }
    }
}