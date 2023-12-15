package nsu.fit.ezaitseva;

import java.util.LinkedList;
import java.util.List;

public class TreeNode<T, N extends TreeNode<T, N>>{
    T data;
    TreeNode<T, N> parent;
    LinkedList<TreeNode<T, N>> children;



    TreeNode() {
    }


}