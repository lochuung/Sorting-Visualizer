package util.tuple;

import util.tree.BinaryTree;
import util.tree.Node;
import java.util.Stack;

public class MergeSortTuple {
    public Node currentNode;
    public BinaryTree tree;
    public Stack<Node> nodeStack;

    public MergeSortTuple(Node currentNode,
                          BinaryTree tree,
                          Stack<Node> nodeStack) {
        this.currentNode = currentNode;
        this.tree = tree;
        this.nodeStack = nodeStack;
    }
}
