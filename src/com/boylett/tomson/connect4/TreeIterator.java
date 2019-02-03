package com.boylett.tomson.connect4;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Tomson on 13/05/2016.
 */
public class TreeIterator<T> implements Iterator<TreeNode<T>> {
    private TreeNode<T> currentNode;
    private Stack<Integer> lastChild = new Stack<>();

    public TreeIterator(TreeNode<T> rootNode) {
        currentNode = rootNode;
        lastChild.push(-1);
    }

    @Override
    public boolean hasNext() {
        if ((lastChild.peek() + 1)  < currentNode.getChildren().size()) {
            return true;
        }
        else if (currentNode.getParent() == null){
            return false;
        }

        TreeNode<T> testNode = currentNode;
        Stack<Integer> testLastChild = (Stack<Integer>) lastChild.clone();
        int last = (testLastChild.pop() + 1);
        while (last >= testNode.getChildren().size()) {
            if (testLastChild.isEmpty()) {
                return false;
            }
            last = (testLastChild.pop() + 1);
            testNode = testNode.getParent();
        }
        return true;
    }

    @Override
    public TreeNode<T> next() {
        int last = (lastChild.pop() + 1);
        while (last >= currentNode.getChildren().size()) {
            last = (lastChild.pop() + 1);
            currentNode = currentNode.getParent();
        }
        TreeNode<T> nextNode = currentNode.getChildren().get(last);
        currentNode = nextNode;
        lastChild.push(last);
        lastChild.push(-1);
        return currentNode;
    }
}
