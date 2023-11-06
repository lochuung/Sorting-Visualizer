package util;

import util.tree.BinaryTree;
import util.tree.Node;

import java.awt.*;
import java.util.List;

public class Canvas {
    public static final int DIM_W = 600;
    public static final int DIM_H = 600;
    private static final String barLayout = "Bar";
    private static final String SKIN_COLOR = "#fbd3a6";
    private static final String BLUE = "#1da4e4";
    private static final String LIGHT_BLUE = "#c9e1f5";
    private static final String GREEN = "#73b369";
    private static final String BLACK = "#46535e";
    public static final int HORIZON = 350;
    public static final int VERT_INC = 15;
    public static int HOR_INC;
    public static final int CONTROL_BUTTON_HEIGHT = 30;

    public static void paintArray(Graphics g, String layout,
                                  List<Integer> list,
                                  List<Integer> pointer) {
        assert list != null && pointer != null && pointer.size() <= 3;

        HOR_INC = DIM_W / list.size();
        drawItems(g, layout, list, pointer);
        drawPointers(g, layout, pointer);
    }

    public static void drawTree(Graphics g, Node<List<Integer>> root,
                                BinaryTree currentTree,
                                Node<List<Integer>> currentNode) {
        if (root == null || currentNode != null
                && currentNode.isParent(root))
            return;

        int heightLevel = currentTree.getCurrentHeightLevel(root);
        int widthLevel = currentTree.getCurrentWidth(root);
        int maxHeightLevel = currentTree.getMaxHeightLevel();
        paintNode(g, heightLevel, widthLevel, maxHeightLevel, root,
                currentNode == root);

        if (currentNode == null)
            return;

        if (currentNode == root)
            paintChildren(g, currentTree, root, heightLevel);


        if (root.left != null && root.left.isPainted)
            drawTree(g, root.left, currentTree, currentNode);
        if (root.right != null && root.right.isPainted)
            drawTree(g, root.right, currentTree, currentNode);
    }

    private static void paintChildren(Graphics g, BinaryTree currentTree,
                                      Node<List<Integer>> root, int heightLevel) {
        if (root.left != null)
            paintNode(g, heightLevel + 1,
                    currentTree.getCurrentWidth(root.left), currentTree.getMaxHeightLevel(),
                    root.left,
                    false);

        if (root.right != null)
            paintNode(g, heightLevel + 1,
                    currentTree.getCurrentWidth(root.right), currentTree.getMaxHeightLevel(), root.right,
                    false);
    }

    private static void paintNode(Graphics g, int heightLevel,
                                  int widthLevel, int maxHeightLevel, Node<List<Integer>> node,
                                  boolean isCurrent) {
        List<Integer> list = node.data;
        int treeHeight = (maxHeightLevel + 1) * 3 * VERT_INC;
        int maxWidthLevel = 1 << (heightLevel - 1);
        int arrayWidth = DIM_W / maxWidthLevel / 2;
        for (int i = 0; i < list.size(); i++) {
            int itemWidth = arrayWidth / list.size();
            int x = i * itemWidth
                    + (widthLevel - 1) * arrayWidth * 2
                    + arrayWidth / 2;
            int y = (heightLevel - 1) * 3 * VERT_INC + (DIM_H - treeHeight) / 2;
            drawArray(g, x, y, itemWidth, list.get(i), i, isCurrent,
                    Color.decode(LIGHT_BLUE));
        }
    }

    private static void drawPointers(Graphics g, String layout,
                                     List<Integer> pointer) {
        Color[] pointerColors = {Color.RED, Color.BLUE, Color.GREEN};
        for (int i = 0; i < pointer.size(); i++) {
            int x = pointer.get(i) * HOR_INC + HOR_INC / 2;
            int y = (DIM_H + HORIZON) / 2 + VERT_INC;
            if (!layout.equals(barLayout))
                y = DIM_H / 2 + VERT_INC * 4;
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
                                  List<Integer> pointer) {
        int maxElement = list.stream()
                .max(Integer::compareTo)
                .orElse(0);

        String[] elementColors = {SKIN_COLOR, LIGHT_BLUE, GREEN};
        for (int i = 0; i < list.size(); i++) {
            if (pointer.contains(i)) {
                drawItem(g, layout, maxElement, list.get(i), i, true,
                        Color.decode(elementColors[pointer.indexOf(i)]));
            } else {
                drawItem(g, layout, maxElement, list.get(i), i, false, null);
            }
        }
    }

    private static void drawItem(Graphics g, String layout,
                                 int maxElement, int item,
                                 int index, boolean isCurrent,
                                 Color currentColor) {
        if (layout.equals(barLayout)) {
            drawBar(g, maxElement, item, index, isCurrent, currentColor);
        } else {
            int x = index * HOR_INC;
            int y = HORIZON - VERT_INC * 2;
            drawArray(g, x, y, HOR_INC, item, index, isCurrent, currentColor);
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

    private static void drawArray(Graphics g, int x, int y, int HOR_INC,
                                  int item, int index,
                                  boolean isCurrent,
                                  Color currentColor) {
        if (isCurrent) {
            g.setColor(currentColor);
            g.fillRect(x, y, HOR_INC, Canvas.VERT_INC);
        }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, HOR_INC, Canvas.VERT_INC);

        String number = String.valueOf(item);
        int stringWidth = g.getFontMetrics().stringWidth(number);
        int stringHeight = g.getFontMetrics().getAscent();
        g.drawString(number, x + (HOR_INC - stringWidth) / 2,
                y + (Canvas.VERT_INC + stringHeight) / 2);

        String indexStr = String.valueOf(index);
        int indexWidth = g.getFontMetrics().stringWidth(indexStr);
        g.drawString(indexStr, x + (HOR_INC - indexWidth) / 2,
                y + Canvas.VERT_INC + stringHeight);
    }
}
