package sortingpanel;

import util.Canvas;
import util.tuple.MergeSortTuple;
import util.tree.BinaryTree;
import util.tree.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MergeSort extends SortingPanel {
    private final Stack<MergeSortTuple> steps;
    private BinaryTree currentTree;
    private Node<List<Integer>> currentNode;
    private Stack<Node<List<Integer>>> nodeStack;
    public MergeSort(List<Integer> values, String layout) {
        super(values, layout);

        steps = new Stack<>();
        nodeStack = new Stack<>();
        currentTree = new BinaryTree(values);
        currentNode = currentTree.root;
        nodeStack.push(currentNode);
        pushStep();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (currentTree == null) {
            return;
        }
        g.clearRect(0, 0, getWidth(), getHeight());
        drawTree(g, currentTree.root);
    }

    private void drawTree(Graphics g, Node<List<Integer>> root) {
        if (root == null)
            return;
        int heightLevel = currentTree.getCurrentHeightLevel(root);
        int widthLevel = currentTree.getCurrentWidth(root);
        Canvas.paintArray(g, heightLevel, widthLevel, root.data, currentNode == root);
        drawTree(g, root.left);
        drawTree(g, root.right);
    }

    @Override
    protected void nextStep() {
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
        if (currentNode == null || currentTree.root.left == null && currentTree.root.right == null) {
            nextButton.setEnabled(false);
            doneButton.setEnabled(false);
            return;
        }
        if (currentNode.left == null && currentNode.right == null) {
            Node<List<Integer>> parentNode = nodeStack.peek();
            Node<List<Integer>> siblingNode = parentNode.left == currentNode ? parentNode.right : parentNode.left;
            if (siblingNode.left == null && siblingNode.right == null) {
                parentNode.data = merge(currentNode.data, siblingNode.data);
                parentNode.left = null;
                parentNode.right = null;
                currentNode = parentNode;
                nodeStack.pop();
                pushStep();
                return;
            }

            currentNode = siblingNode;
            pushStep();
            return;
        }
        nodeStack.push(currentNode);
        currentNode = currentNode.left;
        pushStep();
    }

    @Override
    protected void prevStep() {
        nextButton.setEnabled(true);
        doneButton.setEnabled(true);
        if (steps.size() == 1) {
            currentTree = new BinaryTree(values);
            currentNode = currentTree.root;
            nodeStack.clear();
            nodeStack.push(currentNode);

            prevButton.setEnabled(false);
            restartButton.setEnabled(false);
            return;
        }
        MergeSortTuple prevStep = steps.pop();
        currentNode = prevStep.currentNode;
        currentTree = prevStep.tree;
        nodeStack = prevStep.nodeStack;
    }

    @Override
    protected void restart() {
        currentTree = new BinaryTree(values);
        currentNode = currentTree.root;
        nodeStack.clear();
        nodeStack.push(currentNode);
        steps.clear();
        pushStep();

        prevButton.setEnabled(false);
        restartButton.setEnabled(false);
        nextButton.setEnabled(true);
        doneButton.setEnabled(true);
    }

    private List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) {
            merged.add(left.get(i++));
        }
        while (j < right.size()) {
            merged.add(right.get(j++));
        }
        return merged;
    }

    private void pushStep() {
        BinaryTree newTree = new BinaryTree(currentTree);
        steps.push(new MergeSortTuple(
                newTree.findNode(currentNode.data),
                newTree,
                newTree.copyNodeStack(nodeStack)));
    }
}
