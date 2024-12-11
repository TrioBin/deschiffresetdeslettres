package fr.brisse.deschiffres;

import java.util.ArrayList;

public class NumberList {
    public ArrayList<Integer> card;

    public NumberList() {
        this.card = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            this.card.add(i);
            this.card.add(i);
        }
        this.card.add(25);
        this.card.add(50);
        this.card.add(75);
        this.card.add(100);
    }

    public ArrayList<Integer> getGenerateList(int length) {
        if (length >= this.card.size()) {
            length = this.card.size();
        }

        ArrayList<Integer> hand = new ArrayList<Integer>();
        ArrayList<Integer> tempCard = new ArrayList<>(this.card);
        for (int i = 0; i < length; i++) {
            if (tempCard.isEmpty()) {
                tempCard = new ArrayList<>(this.card);
            }
            int index = (int) (Math.random() * tempCard.size());
            hand.add(tempCard.get(index));
            tempCard.remove(index);
        }

        return hand;
    }
}
