package util;

import java.awt.*;
import java.util.List;

public class Canvas {
    private static final String barLayout = "Bar";
    private static final String SKIN_COLOR = "#fbd3a6";
    private static final String BLUE = "#1da4e4";
    private static final String LIGHT_BLUE = "#c9e1f5";
    private static final String GREEN = "#73b369";
    private static final String BLACK = "#46535e";
    public static final int DIM_W = 500;
    public static final int DIM_H = 500;
    public static final int HORIZON = 350;
    public static final int VERT_INC = 15;
    public static int HOR_INC;

    public static void paintArray(Graphics g, String layout,
                                  List<Integer> list, List<Integer> pointer) {
        int maxElement = list.stream()
                .max(Integer::compareTo)
                .orElse(0);
        HOR_INC = DIM_W / list.size();
        String[] colors = {SKIN_COLOR, LIGHT_BLUE, GREEN};
        for (int i = 0; i < list.size(); i++) {
            if (pointer.contains(i)) {
                drawItem(g, layout, maxElement, list.get(i), i, true,
                        Color.decode(colors[pointer.indexOf(i)]));
            } else {
                drawItem(g, layout, maxElement, list.get(i), i, false, null);
            }
        }
    }

    public static void paintArray(Graphics g, int heightLevel, int widthLevel, List<Integer> list, boolean isCurrent) {
        int maxWidthLevel = 1 << (heightLevel - 1);
        int arrayWidth = DIM_W / maxWidthLevel;
        for (int i = 0; i < list.size(); i++) {
            int itemWidth = arrayWidth / 2 / list.size();
            int x = i * itemWidth
                    + (widthLevel - 1) * arrayWidth
                    + arrayWidth / 4;
            int y = heightLevel * 3 * VERT_INC;
            drawArray(g, x, y, itemWidth, list.get(i), i, isCurrent,
                    Color.decode(LIGHT_BLUE));
        }
    }

    public static void paintPointer(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        int[] xPoints = {x, x - 10, x + 10};
        int[] yPoints = {y, y + 10, y + 10};
        g.fillPolygon(xPoints, yPoints, 3);
        g.drawLine(x, y,
                x, y + 20);
    }

    private static void drawItem(Graphics g, String layout, int maxElement, int item, int index, boolean isCurrent, Color currentColor) {
        if (layout.equals(barLayout)) {
            drawBar(g, maxElement, item, index, isCurrent, currentColor);
        } else {
            int x = index * HOR_INC;
            int y = HORIZON - VERT_INC * 2;
            drawArray(g, x, y, HOR_INC, item, index, isCurrent, currentColor);
        }
    }

    private static void drawBar(Graphics g, int maxElement, int item, int index, boolean isCurrent, Color currentColor) {
        int height = (int) ((double) item / maxElement * HORIZON);
        int x = index * HOR_INC;
        int y = HORIZON - height;
        if (isCurrent) {
            g.setColor(currentColor);
        } else {
            g.setColor(Color.decode(BLUE));
        }
        g.fillRect(x, y, HOR_INC, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, HOR_INC, height);

        g.setColor(Color.decode(BLACK));
        String number = String.valueOf(item);
        int stringWidth = g.getFontMetrics().stringWidth(number);
        int stringHeight = g.getFontMetrics().getAscent();
        g.drawString(number, x + (HOR_INC - stringWidth) / 2, y + (height + stringHeight) / 2);
    }

    private static void drawArray(Graphics g, int x, int y, int HOR_INC, int item, int index, boolean isCurrent, Color currentColor) {
        if (isCurrent) {
            g.setColor(currentColor);
            g.fillRect(x, y, HOR_INC, Canvas.VERT_INC);
        }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, HOR_INC, Canvas.VERT_INC);

        String number = String.valueOf(item);
        int stringWidth = g.getFontMetrics().stringWidth(number);
        int stringHeight = g.getFontMetrics().getAscent();
        g.drawString(number, x + (HOR_INC - stringWidth) / 2, y + (Canvas.VERT_INC + stringHeight) / 2);

        String indexStr = String.valueOf(index);
        int indexWidth = g.getFontMetrics().stringWidth(indexStr);
        g.drawString(indexStr, x + (HOR_INC - indexWidth) / 2, y + Canvas.VERT_INC + stringHeight);
    }
}
