package util.tree;

import java.util.List;
import java.util.Stack;

public class BinaryTree {
    public Node<List<Integer>> root;

    public BinaryTree(List<Integer> rootList) {
        this.root = new Node<>(rootList);
        makeMergeSortTree(root);
    }

    public BinaryTree(BinaryTree tree) {
        this.root = new Node<>(tree.root.data);
        copyTree(this.root, tree.root);
    }

    public Stack<Node<List<Integer>>> copyNodeStack(Stack<Node<List<Integer>>> nodeStack) {
        Stack<Node<List<Integer>>> newStack = new Stack<>();
        for (Node<List<Integer>> node : nodeStack) {
            newStack.push(findNode(node.data));
        }
        return newStack;
    }

    public Node<List<Integer>> findNode(List<Integer> data) {
        return findNode(root, data);
    }

    private Node<List<Integer>> findNode(Node<List<Integer>> root, List<Integer> data) {
        if (root == null) {
            return null;
        }
        if (root.data.equals(data)) {
            return root;
        }
        Node<List<Integer>> leftNode = findNode(root.left, data);
        Node<List<Integer>> rightNode = findNode(root.right, data);
        if (leftNode != null) {
            return leftNode;
        }
        return rightNode;
    }

    private void copyTree(Node<List<Integer>> root, Node<List<Integer>> treeRoot) {
        if (treeRoot == null) {
            return;
        }
        if (treeRoot.left != null) {
            root.left = new Node<>(treeRoot.left.data);
            copyTree(root.left, treeRoot.left);
        }
        if (treeRoot.right != null) {
            root.right = new Node<>(treeRoot.right.data);
            copyTree(root.right, treeRoot.right);
        }
    }

    private void makeMergeSortTree(Node<List<Integer>> root) {
        if (root.data.size() == 1) {
            return;
        }
        int mid = root.data.size() / 2;
        root.left = new Node<>(root.data.subList(0, mid));
        root.right = new Node<>(root.data.subList(mid, root.data.size()));
        makeMergeSortTree(root.left);
        makeMergeSortTree(root.right);
    }

    public int getCurrentHeightLevel(Node<List<Integer>> currentNode) {
        return getCurrentHeightLevel(root, currentNode, 1);
    }

    private int getCurrentHeightLevel(Node<List<Integer>> root, Node<List<Integer>> currentNode, int heightLevel) {
        if (root == null) {
            return 0;
        }
        if (root == currentNode) {
            return heightLevel;
        }
        int leftHeightLevel = getCurrentHeightLevel(root.left, currentNode, heightLevel + 1);
        int rightHeightLevel = getCurrentHeightLevel(root.right, currentNode, heightLevel + 1);
        return Math.max(leftHeightLevel, rightHeightLevel);
    }

    public int getCurrentWidth(Node<List<Integer>> currentNode) {
        int heightLevel = getCurrentHeightLevel(currentNode);
        return getCurrentWidth(root, currentNode, 1) - (1 << (heightLevel - 1)) + 1;
    }

    private int getCurrentWidth(Node<List<Integer>> root, Node<List<Integer>> currentNode, int widthLevel) {
        if (root == null) {
            return 0;
        }
        if (root == currentNode) {
            return widthLevel;
        }
        int leftWidthLevel = getCurrentWidth(root.left, currentNode, widthLevel * 2);
        int rightWidthLevel = getCurrentWidth(root.right, currentNode, widthLevel * 2 + 1);
        return Math.max(leftWidthLevel, rightWidthLevel);
    }
}
