package ru.nsu.fit.ezaitseva;

import java.util.LinkedList;
import java.util.List;

public class TreeNode<T, N extends TreeNode<T, N>>{
    T data;
    TreeNode<T, N> parent;
    LinkedList<TreeNode<T, N>> children;

    TreeNode() {
    }

    public void addChild(T child_data) {
        TreeNode<T, N> childNode = new TreeNode();
        childNode.parent = this;
        childNode.data = child_data;
        childNode.children = new LinkedList<>();
        this.children.add(childNode);
    }

}