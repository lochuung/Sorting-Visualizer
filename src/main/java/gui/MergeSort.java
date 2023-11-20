package gui;

import util.tree.BinaryTree;
import util.tree.Node;
import util.tuple.MergeSortTuple;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static util.ArrayTreeDrawer.drawArrayTree;
import static util.Canvas.DIM_W;

public class MergeSort extends SortingPanel {
    private final Stack<MergeSortTuple> steps;
    private BinaryTree currentTree;
    private Node currentNode;
    private Stack<Node> nodeStack;

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
        drawArrayTree(g, currentTree.root, currentTree, currentNode,
                isMergeStep(), 0, DIM_W);
    }


    @Override
    protected void nextStep() {
        enablePrevAndRestartButton();
        if (currentNode == null) {
            initCurrentNode();
            pushStep();
            return;
        }

        if (isDoneMerge()) {
            currentNode.left = null;
            currentNode.right = null;
            if (isLeaf(currentTree.root)) disableNextAndFinishButton();
            pushStep();
            return;
        }
        if (isLeaf(currentNode)) {
            handleLeaf();
            pushStep();
            return;
        }
        if (isMergeStep()) {
            handleMerge();
            pushStep();
            return;
        }
        goToLeftChild();
        pushStep();
    }

    @Override
    protected void prevStep() {
        if (steps.size() >= 2) {
            steps.pop();
            MergeSortTuple tuple = steps.peek();
            currentTree = tuple.tree;
            nodeStack = tuple.nodeStack;
            currentNode = tuple.currentNode;
            if (steps.size() == 1) {
                restart();
                disablePrevAndRestartButton();
            }
        } else {
            restart();
            disablePrevAndRestartButton();
        }
        enableNextAndFinishButton();
    }

    private boolean isDoneMerge() {
        return currentNode.left != null
                && currentNode.right != null
                && currentNode.left.currentIndex
                >= currentNode.left.data.size()
                && currentNode.right.currentIndex
                >= currentNode.right.data.size();
    }

    private void handleMerge() {
        int i = currentNode.left.currentIndex;
        int j = currentNode.right.currentIndex;
        int size1 = currentNode.left.data.size();
        int size2 = currentNode.right.data.size();
        if (i < size1 && j < size2) {
            mergeLeftRight();
            return;
        }

        if (i < size1) {
            mergeLeft();
            return;
        }

        if (j < size2) {
            mergeRight();
        }
    }

    private void mergeLeftRight() {
        Node leftNode = currentNode.left;
        Node rightNode = currentNode.right;
        int i = leftNode.currentIndex;
        int j = rightNode.currentIndex;
        int k = currentNode.currentIndex;
        int v1 = leftNode.data.get(i);
        int v2 = rightNode.data.get(j);
        if (v1 < v2) {
            currentNode.data.set(k, v1);
            leftNode.data.set(i, -1);
            leftNode.currentIndex++;
        } else {
            currentNode.data.set(k, v2);
            rightNode.data.set(j, -1);
            rightNode.currentIndex++;
        }
        currentNode.currentIndex++;
    }

    private void mergeLeft() {
        Node leftNode = currentNode.left;
        int i = leftNode.currentIndex;
        int k = currentNode.currentIndex;
        int v1 = leftNode.data.get(i);
        currentNode.data.set(k, v1);
        leftNode.data.set(i, -1);
        leftNode.currentIndex++;
        currentNode.currentIndex++;
    }

    private void mergeRight() {
        Node rightNode = currentNode.right;
        int j = rightNode.currentIndex;
        int k = currentNode.currentIndex;
        int v2 = rightNode.data.get(j);
        currentNode.data.set(k, v2);
        rightNode.data.set(j, -1);
        rightNode.currentIndex++;
        currentNode.currentIndex++;
    }

    private void handleLeaf() {
        if (nodeStack.isEmpty()) return;
        Node parentNode = nodeStack.peek();
        if (parentNode.left == currentNode) {
            goToRight();
        } else {
            goToParent();
            clearCurrentData();
            currentNode.currentIndex = 0;
            initIndexChildren();
        }
    }

    private void clearCurrentData() {
        Collections.fill(currentNode.data, -1);
    }

    private void initIndexChildren() {
        currentNode.left.currentIndex = 0;
        currentNode.right.currentIndex = 0;
    }

    private boolean isMergeStep() {
        return currentNode != null &&
                isLeaf(currentNode.left)
                && isLeaf(currentNode.right) && (
                currentNode.data.contains(-1)
                        || (currentNode.left.data.contains(-1) &&
                        currentNode.right.data.contains(-1))
        );
    }

    private boolean isLeaf(Node node) {
        return node != null
                && node.left == null
                && node.right == null;
    }

    private void goToParent() {
        currentNode = nodeStack.peek();
        nodeStack.pop();
        if (currentNode.left != null)
            currentNode.left.isPainted = true;
        if (currentNode.right != null)
            currentNode.right.isPainted = true;
    }

    private void goToRight() {
        Node parentNode = nodeStack.peek();
        currentNode = parentNode.right;
        currentNode.isPainted = true;
        if (currentNode.left != null)
            currentNode.left.isPainted = true;
        if (currentNode.right != null)
            currentNode.right.isPainted = true;
    }

    private void goToLeftChild() {
        if (currentNode.left == null) {
            return;
        }
        nodeStack.push(currentNode);
        currentNode = currentNode.left;
        currentNode.isPainted = true;
        if (currentNode.left != null)
            currentNode.left.isPainted = true;
        if (currentNode.right != null)
            currentNode.right.isPainted = true;
    }

    private void initCurrentNode() {
        currentNode = currentTree.root;
        currentNode.isPainted = true;
        if (currentNode.left != null)
            currentNode.left.isPainted = true;
        if (currentNode.right != null)
            currentNode.right.isPainted = true;
    }

    @Override
    protected void restart() {
        if (steps.isEmpty())
            return;
        MergeSortTuple tuple = steps.firstElement();
        currentTree = tuple.tree;
        currentNode = null;
        nodeStack = new Stack<>();
        values = tuple.tree.root.data;
        steps.clear();
        pushStep();

        prevButton.setEnabled(false);
        restartButton.setEnabled(false);
        nextButton.setEnabled(true);
        finishButton.setEnabled(true);
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
