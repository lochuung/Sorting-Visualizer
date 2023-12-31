package util.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public List<Integer> data;
    public Node left;
    public Node right;
    public int currentIndex = -1;
    public boolean isPainted;

    public Node(List<Integer> data) {
        this.data = new ArrayList<>(data);
        this.left = null;
        this.right = null;
        this.isPainted = false;
    }

    public boolean isParent(Node node) {
        return left == node || right == node;
    }
}
