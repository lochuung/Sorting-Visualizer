package util;

import java.awt.*;
import java.util.List;

public class Canvas {
    public static final int DIM_W = 500;
    public static final int DIM_H = 500;
    public static final int HORIZON = 350;
    public static final int VERT_INC = 15;
    private static int HOR_INC;

    public static void paintArray(Graphics g, String layout, List<Integer> list) {
        int maxElement = list.stream()
                .max(Integer::compareTo)
                .orElse(0);
        HOR_INC = DIM_W / list.size();

        for (int i = 0; i < list.size(); i++) {
            drawItem(g, layout, maxElement, list.get(i), i);
        }
    }

    public static void paintPointer(Graphics g, Color color, int index) {
        g.setColor(color);
        g.drawLine(index * HOR_INC + HOR_INC / 2, HORIZON,
                index * HOR_INC + HOR_INC / 2, HORIZON + 20);
    }

    private static void drawItem(Graphics g, String layout, int maxElement, int item, int index) {
        if (layout.equals("Bar")) {
            drawBar(g, maxElement, item, index);
        } else {
            drawArray(g, item, index);
        }
    }

    private static void drawBar(Graphics g, int maxElement, int item, int index) {
        int height = (int) ((double) item / maxElement * HORIZON);
        int x = index * HOR_INC;
        int y = HORIZON - height;
        g.setColor(Color.CYAN);
        g.fillRect(x, y, HOR_INC, height);
        g.setColor(Color.BLUE);
        String number = String.valueOf(item);
        int stringWidth = g.getFontMetrics().stringWidth(number);
        int stringHeight = g.getFontMetrics().getAscent();
        g.drawString(number, x + (HOR_INC - stringWidth) / 2, y + (height + stringHeight) / 2);
    }

    private static void drawArray(Graphics g, int item, int index) {
        int x = index * HOR_INC;
        int y = (DIM_H - VERT_INC) / 2;
        g.drawRect(x, y, HOR_INC, VERT_INC);

        String number = String.valueOf(item);
        int stringWidth = g.getFontMetrics().stringWidth(number);
        int stringHeight = g.getFontMetrics().getAscent();
        g.drawString(number, x + (HOR_INC - stringWidth) / 2, y + (VERT_INC + stringHeight) / 2);

        String indexStr = String.valueOf(index);
        int indexWidth = g.getFontMetrics().stringWidth(indexStr);
        g.drawString(indexStr, x + (HOR_INC - indexWidth) / 2, y + VERT_INC + stringHeight);
    }
}
