package com.boylett.tomson.connect4;

/**
 * Created by Tomson on 13/05/2016.
 */
public abstract class TreeAction<T> {
    TreeNode<T> node;

    public TreeAction(TreeNode<T> node) {
        this.node = node;

    }

    public abstract void onNodeTraverse();
}
