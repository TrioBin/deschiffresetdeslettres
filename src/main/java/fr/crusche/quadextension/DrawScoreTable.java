package fr.crusche.quadextension;

import com.quad.core.CacheStorage;
import com.quad.core.Renderer;
import com.quad.core.fx.Font;

public class DrawScoreTable {
    public static void drawScoreTable(int x, int y, int textHeight, CacheStorage cache, Renderer r) {
        r.setFont(new Font("Arial", "normal", textHeight));
        for (int i = 1; i <= cache.playerNumber; i++) {
            if (i == cache.currentPlayer) {
                r.drawString("Player " + i, 0xff0000ff, x, y + (i - 1) * textHeight);
                r.drawString(cache.scores.get(i - 1).getScore() + "", 0xff0000ff, x + 200, y + (i - 1) * textHeight);
            } else {
                r.drawString("Player " + i, 0xffffffff, x, y + (i - 1) * textHeight);
                r.drawString(cache.scores.get(i - 1).getScore() + "", 0xffffffff, x + 200, y + (i - 1) * textHeight);
            }
        }
    }
}
