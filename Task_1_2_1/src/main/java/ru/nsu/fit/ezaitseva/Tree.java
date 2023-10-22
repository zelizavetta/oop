package ru.nsu.fit.ezaitseva;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class Tree<T, N extends TreeNode<T, N>> {

    TreeNode<T, N> root;

    Tree(T root_data) {
        root = new TreeNode<T, N>();
        root.data = root_data;
        root.parent = null;
        root.children = new LinkedList<TreeNode<T, N>>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Tree tree = (Tree) obj;
        LinkedList<TreeNode<T, N>> breadthNodes1 = this.BreadthNodes(this.root);
        LinkedList<TreeNode<T, N>> breadthNodes2 = tree.BreadthNodes(tree.root);

        int treeLength1 = breadthNodes1.size();
        int treeLength2 = breadthNodes2.size();
        if (treeLength1 != treeLength2) {
            return false;
        }

        for (int i = 0; i < treeLength1; i++) {
            if (breadthNodes1.get(i).data != breadthNodes2.get(i).data) {
                return false;
            }
        }
        return true;
    }

    public Tree addNode(T nodeData) {
        TreeNode<T, N> node = new TreeNode<T, N>();
        node.data = nodeData;
        node.parent = this.root;
        node.children = new LinkedList<>();
        this.root.children.add(node);
        return this;
    }

    public void removeNode(T nodeData) {
        TreeNode<T, N> node = this.BFS(nodeData);
        System.out.println(node.data);
        if (node.children != null) {
            for (TreeNode<T, N> elem : node.children) {
                elem.parent = node.parent;
            }
        }
        node.parent.children.remove(node);
        node.parent = null;
    }

    public Tree addSubTree(Tree subTree) {
        subTree.root.parent = this.root;
        this.root.children.add(subTree.root);
        return this;
    }

    public void removeTree(Tree subTree) {
        subTree.root.parent.children.remove(subTree.root);
        subTree.root.parent = null;
    }

    public void printDeepNodes(TreeNode<T, N> node) {
        if (node != null) {
            System.out.println(node.data);
            for (TreeNode<T, N> child : node.children) {
                printDeepNodes(child);
            }
        }
    }

    public TreeNode<T, N> BFS(T nodeData) {
        Queue<TreeNode<T, N>> queue = new LinkedList<>();
        queue.offer(this.root);
        while (! (queue.isEmpty())) {
            var tmpNode = queue.poll();
            if (tmpNode.data == nodeData) {
                return tmpNode;
            }
            for (TreeNode<T, N> nodeEle : tmpNode.children) {
                queue.offer(nodeEle);
            }
        }
        return null;
    }

    public LinkedList<TreeNode<T, N>> BreadthNodes(TreeNode<T, N> node) {
        Queue<TreeNode<T, N>> queue = new LinkedList<>();
        LinkedList<TreeNode<T, N>> res = new LinkedList<>();

        queue.offer(node);
        while (! (queue.isEmpty())) {
            var tmpNode = queue.poll();
            res.add(tmpNode);
//            System.out.println(tmpNode.data);
            for (TreeNode<T, N> nodeEle : tmpNode.children) {
                queue.offer(nodeEle);
            }
        }
        return res;
    }

    public void printDeepTree() {
        printDeepNodes(this.root);
    }

    public void  printBreadthTree() {
        LinkedList<TreeNode<T, N>> values = BreadthNodes(this.root);
        for (TreeNode<T, N> elem : values) {
            System.out.println(elem.data);
        }

    }

    public static void main(String[] args) {
        Tree tree = new Tree("R1");
        Tree a1 = tree.addNode("A1");
        Tree subtree = new Tree("R1");
        Tree b1 = subtree.addNode("A1");
        Tree b2 = subtree.addNode("B2");
        Tree b3 = subtree.addNode("B3");
        Tree new_tree = tree.addSubTree(subtree);
        Tree a2 = tree.addNode("A2");
//        tree.removeTree(subtree);
        tree.printBreadthTree();
//        System.out.println(tree.equals(subtree));
    }
}
