package sorting;

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
        currentNode = null;
        pushStep();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (currentTree == null) {
            return;
        }
        g.clearRect(0, 0, getWidth(), getHeight());
        Canvas.drawArrayTree(g, currentTree.root, currentTree, currentNode);
    }


    @Override
    protected void nextStep() {
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
        if (currentNode == null) {
            currentNode = currentTree.root;
            currentNode.isPainted = true;
            if (currentNode.left != null)
                currentNode.left.isPainted = true;
            if (currentNode.right != null)
                currentNode.right.isPainted = true;
            pushStep();
            return;
        }

        if (currentNode.left == null && currentNode.right == null) {
            Node<List<Integer>> parentNode = nodeStack.peek();
            currentNode = parentNode.left == currentNode ?
                    parentNode.right : parentNode.left;
            if (parentNode.left == currentNode) {
                parentNode.data = merge(parentNode.left.data,
                        parentNode.right.data);
                parentNode.left = null;
                parentNode.right = null;
                currentNode = parentNode;
                nodeStack.pop();
                if (currentTree.root.left == null
                        && currentTree.root.right == null) {
                    nextButton.setEnabled(false);
                    doneButton.setEnabled(false);
                }
            } else {
                currentNode.isPainted = true;
                if (currentNode.left != null)
                    currentNode.left.isPainted = true;
                if (currentNode.right != null)
                    currentNode.right.isPainted = true;
            }
            pushStep();
            return;
        }
        nodeStack.push(currentNode);
        currentNode = currentNode.left;
        currentNode.isPainted = true;
        if (currentNode.left != null)
            currentNode.left.isPainted = true;
        if (currentNode.right != null)
            currentNode.right.isPainted = true;
        pushStep();
    }

    @Override
    protected void prevStep() {
        nextButton.setEnabled(true);
        doneButton.setEnabled(true);
        if (steps.isEmpty()) {
            return;
        }
        steps.pop();
        MergeSortTuple prevStep = steps.peek();
        currentNode = prevStep.currentNode;
        currentTree = prevStep.tree;
        nodeStack = prevStep.nodeStack;
        if (steps.size() == 1) {
            steps.clear();
            pushStep();
            prevButton.setEnabled(false);
            restartButton.setEnabled(false);
        }
    }

    @Override
    protected void restart() {
        while (steps.size() > 1) {
            prevStep();
        }

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
                currentNode == null ?
                        null : newTree.findNode(currentNode.data),
                newTree,
                newTree.copyNodeStack(nodeStack)));
    }
}
