package com.boylett.tomson.connect4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tomson on 13/05/2016.
 */
public class TreeNode<T> {
    private T value;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();

    public TreeNode(T value, TreeNode<T> parent) {
        this.value = value;
        this.parent = parent;
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public Iterator<TreeNode<T>> getIterator() {
        return new TreeIterator<T>(this);
    }
}
