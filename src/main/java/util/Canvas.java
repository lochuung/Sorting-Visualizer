package util;

import java.awt.*;
import java.util.List;

public class Canvas {
    public static final int DIM_W = 600;
    public static final int DIM_H = 600;
    public static final String barLayout = "Bar";
    public static final String SKIN_COLOR = "#fbd3a6";
    public static final String BLUE = "#1da4e4";
    public static final String LIGHT_BLUE = "#c9e1f5";
    public static final String GREEN = "#73b369";
    public static final String BLACK = "#46535e";
    public static final String LIGHT_GREEN = "#c1f0ad";
    public static final int HORIZON = 350;
    public static int VERT_INC = 15;
    public static int HOR_INC;
    public static final int CONTROL_BUTTON_HEIGHT = 30;
    private static final int EMPTY_SPACE = 10;
    private static final int RADIUS = 10;
    private static int size = 0;

    public static void paintArray(Graphics g, String layout,
                                  List<Integer> list,
                                  List<Integer> pointer,
                                  int startSorted,
                                  int sizeSorted) {
        assert list != null && pointer != null && pointer.size() <= 3;

        HOR_INC = DIM_W / list.size();
        drawItems(g, layout, list, pointer, startSorted, sizeSorted);
        drawPointers(g, layout, pointer);
    }

    private static void drawPointers(Graphics g, String layout,
                                     List<Integer> pointer) {
        Color[] pointerColors = {Color.RED, Color.BLUE, Color.GREEN};
        int width = layout.equals(barLayout)
                ? HOR_INC :
                (DIM_W - 2 * EMPTY_SPACE) / size;
        for (int i = 0; i < pointer.size(); i++) {
            int x = pointer.get(i) * width + width / 2;
            int y = (DIM_H + HORIZON) / 2 + VERT_INC;
            if (!layout.equals(barLayout)) {
                x += EMPTY_SPACE;
                y = DIM_H / 2 + VERT_INC * 4 + 10;
            }
            drawPointer(g, pointerColors[i], x, y);
        }
    }

    private static void drawPointer(Graphics g, Color color, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        int[] xPoints = {x, x - 10, x + 10};
        int[] yPoints = {y, y + 10, y + 10};
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x, y + 5,
                x, y + 20);
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setStroke(new BasicStroke(1));
    }

    private static void drawItems(Graphics g, String layout,
                                  List<Integer> list,
                                  List<Integer> pointer,
                                  int startSorted, int sizeSorted) {
        int maxElement = list.stream()
                .max(Integer::compareTo)
                .orElse(0);

        String[] elementColors = {SKIN_COLOR, LIGHT_BLUE, LIGHT_GREEN};
        size = list.size();
        for (int i = 0; i < list.size(); i++) {
            if (i >= startSorted
                    && i < startSorted + sizeSorted)
                drawItem(g, layout, maxElement, list.get(i), i, true,
                        Color.decode(GREEN));
            else
                drawItem(g, layout, maxElement, list.get(i), i, false,
                        null);
            if (pointer.contains(i))
                drawItem(g, layout, maxElement, list.get(i), i, true,
                        Color.decode(elementColors[pointer.indexOf(i)]));
        }
    }

    private static void drawItem(Graphics g, String layout,
                                 int maxElement, int item,
                                 int index, boolean isCurrent,
                                 Color currentColor) {
        if (layout.equals(barLayout)) {
            drawBar(g, maxElement, item, index, isCurrent, currentColor);
        } else {
            int width = (DIM_W - 2 * EMPTY_SPACE) / size;
            int x = index * width + EMPTY_SPACE;
            int y = (DIM_H - HOR_INC) / 2;
            drawArray(g, x, y, width, item, index,
                    isCurrent, currentColor);
        }
    }

    private static void drawBar(Graphics g, int maxElement, int item,
                                int index, boolean isCurrent,
                                Color currentColor) {
        int height = (int) ((double) item / maxElement * HORIZON);
        int x = index * HOR_INC;
        int y = (DIM_H + HORIZON) / 2 - height;
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
        g.drawString(number, x + (HOR_INC - stringWidth) / 2,
                y + (height + stringHeight) / 2);
    }

    public static void drawArray(Graphics g, int x, int y, int HOR_INC,
                                 int item, int index,
                                 boolean isCurrent,
                                 Color currentColor) {
        drawArray(g, x, y, HOR_INC, item, index, true,
                isCurrent, currentColor);
    }

    public static void drawArray(Graphics g, int x, int y, int HOR_INC,
                                 int item, int index, boolean isDrawItem,
                                 boolean isCurrent,
                                 Color currentColor) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (isCurrent) {
            g2d.setColor(currentColor);
            g2d.fillRoundRect(x, y, HOR_INC, HOR_INC,
                    RADIUS, RADIUS);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x, y, HOR_INC, HOR_INC,
                RADIUS, RADIUS);

        int stringHeight = g2d.getFontMetrics().getAscent();
        if (isDrawItem) {
            String number = String.valueOf(item);
            int stringWidth = g2d.getFontMetrics().stringWidth(number);
            g2d.drawString(number, x + (HOR_INC - stringWidth) / 2,
                    y + (HOR_INC + stringHeight) / 2);
        }

        String indexStr = String.valueOf(index);
        int indexWidth = g2d.getFontMetrics().stringWidth(indexStr);
        g2d.drawString(indexStr, x + (HOR_INC - indexWidth) / 2,
                y + HOR_INC + stringHeight);
    }
}
