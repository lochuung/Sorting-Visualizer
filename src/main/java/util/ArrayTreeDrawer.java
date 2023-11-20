package util;

import util.tree.BinaryTree;
import util.tree.Node;

import java.awt.*;
import java.util.List;

import static util.Canvas.*;

public class ArrayTreeDrawer {
    public static void drawArrayTree(Graphics g, Node root,
                                     BinaryTree currentTree,
                                     Node currentNode,
                                     boolean isMergeStep,
                                     int startX, int endX) {
        if (root == null || currentNode != null
                && currentNode.isParent(root))
            return;

        int heightLevel = currentTree.getCurrentHeightLevel(root);
        int maxHeightLevel = currentTree.getMaxHeightLevel();
        boolean isCurrent = currentNode == root;
        paintNode(g, heightLevel, maxHeightLevel,
                root,
                !isMergeStep && isCurrent,
                false, startX, endX);

        if (currentNode == null)
            return;

        if (currentNode == root)
            paintChildren(g, currentTree, root, heightLevel,
                    isMergeStep,
                    startX, endX);


        if (root.left != null && root.left.isPainted)
            drawArrayTree(g, root.left, currentTree, currentNode,
                    isMergeStep,
                    startX, (startX + endX) / 2);
        if (root.right != null && root.right.isPainted)
            drawArrayTree(g, root.right, currentTree, currentNode,
                    isMergeStep,
                    (startX + endX) / 2, endX);
    }

    private static void paintChildren(Graphics g, BinaryTree currentTree,
                                      Node root, int heightLevel,
                                      boolean isMergeStep,
                                      int startX, int endX) {
        if (root.left != null)
            paintChildNode(g, currentTree, root.left,
                    heightLevel, isMergeStep, root.right,
                    startX, (startX + endX) / 2);

        if (root.right != null) {
            assert root.left != null;
            paintChildNode(g, currentTree, root.right,
                    heightLevel, isMergeStep, root.left,
                    (startX + endX) / 2, endX);
        }
    }

    private static void paintChildNode(Graphics g, BinaryTree currentTree,
                                       Node childNode,
                                       int heightLevel,
                                       boolean isMergeStep,
                                       Node siblingNode,
                                       int startX, int endX) {
        boolean isHighlighted = isMergeStep && isNodeHighlighted(childNode,
                siblingNode);
        paintNode(g, heightLevel + 1,
                currentTree.getMaxHeightLevel(),
                childNode,
                isMergeStep,
                isHighlighted,
                startX, endX);
    }

    private static boolean isNodeHighlighted(Node node, Node siblingNode) {
        return siblingNode.data.size() == siblingNode.currentIndex
                || node.currentIndex >= 0
                && node.currentIndex < node.data.size()
                && siblingNode.currentIndex >= 0
                && siblingNode.currentIndex < siblingNode.data.size()
                && node.data.get(node.currentIndex)
                < siblingNode.data.get(siblingNode.currentIndex);
    }

    private static void paintNode(Graphics g, int heightLevel,
                                  int maxHeightLevel,
                                  Node node,
                                  boolean isCurrent,
                                  boolean isChild,
                                  int startX, int endX) {
        List<Integer> list = node.data;
        int verticalOffset = VERT_INC;
        int treeHeight = (maxHeightLevel + 1) * (verticalOffset
        + g.getFontMetrics().getHeight() * 2);
        for (int i = 0; i < list.size(); i++) {
            int x = startX + (endX - startX - list.size()*verticalOffset) / 2
                    + i * verticalOffset;
            int y = (heightLevel - 1) * (verticalOffset
                    + g.getFontMetrics().getHeight() * 2)
                    + (DIM_H - treeHeight) / 2
                    + g.getFontMetrics().getHeight() * 2;
            int item = list.get(i);
            String color = GREEN;
            boolean flag = isCurrent;
            if (i == node.currentIndex && isChild) {
                color = LIGHT_BLUE;
                flag = true;
            }
            drawArray(g, x, y, verticalOffset, item, i,
                    item >= 0, flag,
                    Color.decode(color));
        }
    }
}
