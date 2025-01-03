package fr.crusche.quadextension;

import com.quad.core.CacheStorage;
import com.quad.core.Renderer;
import com.quad.core.fx.Font;

public class DrawScoreTable {
    public static void drawScoreTable(int x, int y, int textHeight, CacheStorage cache, Renderer r) {
        r.setFont(new Font("Arial", "normal", textHeight));
        for (int i = 1; i <= cache.playerNumber; i++) {
            if (i == cache.currentPlayer) {
                r.drawString("Player " + i, 0xaaaaaa, x, y + (i - 1) * textHeight);
                r.drawString(cache.scores.get(i - 1).getScore() + "", 0xaaaaaa, x + 200, y + (i - 1) * textHeight);
            } else {
                r.drawString("Player " + i, 0xffffff, x, y + (i - 1) * textHeight);
                r.drawString(cache.scores.get(i - 1).getScore() + "", 0xffffff, x + 200, y + (i - 1) * textHeight);
            }
        }
    }

    public static void drawScoreTable(int x, int y, int textHeight, CacheStorage cache, Renderer r, boolean isBot) {
        r.setFont(new Font("Arial", "normal", textHeight));
        if (isBot) {
            r.drawString("Player", 0xaaaaaa, x, y);
            r.drawString(cache.scores.get(0).getScore() + "", 0xaaaaaa, x + 200, y);
            r.drawString("Bot", 0xffffff, x, y + textHeight);
            r.drawString(cache.scores.get(1).getScore() + "", 0xffffff, x + 200, y + textHeight);
        } else {
            for (int i = 1; i <= cache.playerNumber; i++) {
                if (i == cache.currentPlayer) {
                    r.drawString("Player " + i, 0xaaaaaa, x, y + (i - 1) * textHeight);
                    r.drawString(cache.scores.get(i - 1).getScore() + "", 0xaaaaaa, x + 200,
                            y + (i - 1) * textHeight);
                } else {
                    r.drawString("Player " + i, 0xffffff, x, y + (i - 1) * textHeight);
                    r.drawString(cache.scores.get(i - 1).getScore() + "", 0xffffff, x + 200,
                            y + (i - 1) * textHeight);
                }
            }
        }
    }
}
