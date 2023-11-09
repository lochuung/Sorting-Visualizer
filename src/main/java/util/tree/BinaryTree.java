package util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTree {
    public Node root;

    public BinaryTree(List<Integer> rootList) {
        this.root = new Node(rootList);
        makeMergeSortTree(root);
    }

    public BinaryTree(BinaryTree tree) {
        this.root = new Node(tree.root.data);
        copyTree(this.root, tree.root);
    }

    public Node findParentNode(Node node) {
        return findParentNode(root, node);
    }

    public Node findParentNode(Node root, Node node) {
        if (root == null) {
            return null;
        }
        if (root.left == node || root.right == node) {
            return root;
        }
        Node leftNode = findParentNode(root.left, node);
        Node rightNode = findParentNode(root.right, node);
        if (leftNode != null) {
            return leftNode;
        }
        return rightNode;
    }

    public int getMaxHeightLevel() {
        int size = root.data.size();
        if ((size & 1) > 0) {
            size++;
        }
        return (int) (Math.log(size) / Math.log(2)) + 1;
    }

    public Stack<Node> copyNodeStack(Stack<Node> nodeStack) {
        Stack<Node> newStack = new Stack<>();
        for (Node node : nodeStack) {
            Node foundNode =
                    findNode(node.data);
            newStack.push(
                    foundNode == null ?
                            new Node(node.data) :
                            foundNode
            );
        }
        return newStack;
    }

    public Node findNode(List<Integer> data) {
        return findNode(root, data);
    }

    private Node findNode(Node root, List<Integer> data) {
        if (root == null) {
            return null;
        }
        if (root.data.equals(data)) {
            return root;
        }
        Node leftNode = findNode(root.left, data);
        Node rightNode = findNode(root.right, data);
        if (leftNode != null) {
            return leftNode;
        }
        return rightNode;
    }

    private void copyTree(Node root,
                          Node treeRoot) {
        if (treeRoot == null) {
            return;
        }
        root.isPainted = treeRoot.isPainted;
        root.currentIndex = treeRoot.currentIndex;
        if (treeRoot.left != null) {
            root.left = new Node(treeRoot.left.data);
            copyTree(root.left, treeRoot.left);
        }
        if (treeRoot.right != null) {
            root.right = new Node(treeRoot.right.data);
            copyTree(root.right, treeRoot.right);
        }
    }

    private void makeMergeSortTree(Node root) {
        if (root.data.size() == 1) {
            return;
        }
        int mid = root.data.size() / 2;
        List<Integer> left = new ArrayList<>() {{
            addAll(root.data.subList(0, mid));
        }};
        List<Integer> right = new ArrayList<>() {{
            addAll(root.data.subList(mid, root.data.size()));
        }};
        root.left = new Node(left);
        root.right = new Node(right);
        makeMergeSortTree(root.left);
        makeMergeSortTree(root.right);
    }

    public int getCurrentHeightLevel(Node currentNode) {
        return getCurrentHeightLevel(root, currentNode, 1);
    }

    private int getCurrentHeightLevel(Node root, Node currentNode, int heightLevel) {
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

    public int getCurrentWidth(Node currentNode) {
        int heightLevel = getCurrentHeightLevel(currentNode);
        return getCurrentWidth(root, currentNode, 1) - (1 << (heightLevel - 1)) + 1;
    }

    private int getCurrentWidth(Node root, Node currentNode, int widthLevel) {
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
