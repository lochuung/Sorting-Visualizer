package util.tree;

public class Node<T> {
    public T data;
    public Node<T> left;
    public Node<T> right;
    public boolean isPainted;

    public Node(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.isPainted = false;
    }

    public Node(Node<T> node) {
        this.data = node.data;
        this.left = node.left;
        this.right = node.right;
        this.isPainted = node.isPainted;
    }

    public boolean isParent(Node<T> node) {
        return left == node || right == node;
    }
}
