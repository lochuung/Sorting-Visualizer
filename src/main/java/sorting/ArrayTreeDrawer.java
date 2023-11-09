package sorting;

import util.Canvas;
import util.tree.BinaryTree;
import util.tree.Node;

import java.awt.*;
import java.util.List;

import static util.Canvas.*;

public class ArrayTreeDrawer {
    public static void drawArrayTree(Graphics g, Node root,
                                     BinaryTree currentTree,
                                     Node currentNode,
                                     boolean isMergeStep) {
        if (root == null || currentNode != null
                && currentNode.isParent(root))
            return;

        int heightLevel = currentTree.getCurrentHeightLevel(root);
        int widthLevel = currentTree.getCurrentWidth(root);
        int maxHeightLevel = currentTree.getMaxHeightLevel();
        boolean isCurrent = currentNode == root;
        paintNode(g, heightLevel, widthLevel, maxHeightLevel, root,
                !isMergeStep && isCurrent,
                false);

        if (currentNode == null)
            return;

        if (currentNode == root)
            paintChildren(g, currentTree, root, heightLevel,
                    isMergeStep);


        if (root.left != null && root.left.isPainted)
            drawArrayTree(g, root.left, currentTree, currentNode, isMergeStep);
        if (root.right != null && root.right.isPainted)
            drawArrayTree(g, root.right, currentTree, currentNode, isMergeStep);
    }

    private static void paintChildren(Graphics g, BinaryTree currentTree,
                                      Node root, int heightLevel,
                                      boolean isMergeStep) {
        if (root.left != null)
            paintChildNode(g, currentTree, root.left,
                    heightLevel, isMergeStep, root.right);

        if (root.right != null)
            paintChildNode(g, currentTree, root.right,
                    heightLevel, isMergeStep, root.left);
    }

    private static void paintChildNode(Graphics g, BinaryTree currentTree,
                                       Node childNode,
                                       int heightLevel, boolean isMergeStep,
                                       Node siblingNode) {
        boolean isHighlighted = isMergeStep && isNodeHighlighted(childNode,
                siblingNode);
        paintNode(g, heightLevel + 1,
                currentTree.getCurrentWidth(childNode),
                currentTree.getMaxHeightLevel(),
                childNode,
                isMergeStep,
                isHighlighted);
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
                                  int widthLevel, int maxHeightLevel,
                                  Node node,
                                  boolean isCurrent,
                                  boolean isLessChild) {
        List<Integer> list = node.data;
        int verticalOffset = 4 * VERT_INC;
        int treeHeight = (maxHeightLevel + 1) * verticalOffset;
        int maxWidthLevel = 1 << (heightLevel - 1);
        int arrayWidth = DIM_W / maxWidthLevel / 2;
        for (int i = 0; i < list.size(); i++) {
            int itemWidth = arrayWidth / list.size();
            int x = i * itemWidth
                    + (widthLevel - 1) * arrayWidth * 2
                    + arrayWidth / 2;
            int y = (heightLevel - 1) * verticalOffset
                    + (DIM_H - treeHeight) / 2;
            int item = list.get(i);
            String color = GREEN;
            boolean flag = isCurrent;
            if (i == node.currentIndex && isLessChild) {
                color = LIGHT_BLUE;
                flag = true;
            }
            drawArray(g, x, y, itemWidth, item, i,
                    item >= 0, flag,
                    Color.decode(color));
        }
    }
}
