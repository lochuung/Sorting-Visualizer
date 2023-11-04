package util.tuple;

import util.tree.BinaryTree;
import util.tree.Node;

import java.util.List;
import java.util.Stack;

public class MergeSortTuple {
    public Node<List<Integer>> currentNode;
    public BinaryTree tree;
    public Stack<Node<List<Integer>>> nodeStack;

    public MergeSortTuple(Node<List<Integer>> currentNode,
                          BinaryTree tree,
                          Stack<Node<List<Integer>>> nodeStack) {
        this.currentNode = currentNode;
        this.tree = tree;
        this.nodeStack = nodeStack;
    }
}
