package nsu.fit.ezaitseva;

import java.util.*;

public class Tree<T, N extends TreeNode<T, N>> {

    private TreeNode<T, N> root;
    private int modifyCount = 0;

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
        List<String> fromBfs1 = new ArrayList<>();
        Iterator iteratorbfs1 = this.bfsiterator();
        while (iteratorbfs1.hasNext()) {
            fromBfs1.add(iteratorbfs1.next().toString());
        }

        List<String> fromBfs2 = new ArrayList<>();
        Iterator iteratorbfs2 = tree.bfsiterator();
        while (iteratorbfs2.hasNext()) {
            fromBfs2.add(iteratorbfs2.next().toString());
        }

        int treeLength1 = fromBfs1.size();
        int treeLength2 = fromBfs2.size();
        if (treeLength1 != treeLength2) {
            return false;
        }

        for (int i = 0; i < treeLength1; i++) {
            if (fromBfs1.get(i) != fromBfs2.get(i)) {
                return false;
            }
        }
        return true;
    }


    public TreeNode<T, N> addNode(T nodeData) {
        TreeNode<T, N> node = new TreeNode<T, N>();
        node.data = nodeData;
        this.modifyCount++;
        node.parent = this.root;
        node.children = new LinkedList<>();
        this.root.children.add(node);
        this.modifyCount++;
        return node;
    }

    public void removeNode(TreeNode<T, N> node) {
        if (node.children != null) {
            for (TreeNode<T, N> elem : node.children) {
                elem.parent = node.parent;
            }
        }
        node.parent.children.remove(node);
        node.parent = null;
        this.modifyCount++;
    }

    public Tree addSubTree(Tree subTree) {
        subTree.root.parent = this.root;
        this.root.children.add(subTree.root);
        this.modifyCount++;
        return this;
    }

    public void removeTree(Tree subTree) {
        subTree.root.parent.children.remove(subTree.root);
        subTree.root.parent = null;
        this.modifyCount++;
    }



    public Iterator<T> bfsiterator() {
        return new BfsIterator<T>(this);
    }

    public class BfsIterator<T> implements Iterator<T>{
        private Queue<TreeNode> queue;
        private int curModifyCount;

        BfsIterator(Tree tree) {
            this.queue = new ArrayDeque<TreeNode>();
            if (tree.root.data != null) {
                this.queue.add(tree.root);
                this.curModifyCount = modifyCount;
            }

        }

        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if (curModifyCount != modifyCount) {
                throw new ConcurrentModificationException();
            }
            return !this.queue.isEmpty();
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            TreeNode cur = this.queue.poll();
            for (int i = 0; i < cur.children.size(); i++) {
                if (cur.children.get(i) != null) {
                    this.queue.add((TreeNode) cur.children.get(i));
                }
            }
            return (T) cur.data;
        }

        @Override
        public void remove() throws ConcurrentModificationException {
            throw new ConcurrentModificationException();
        }
    }

    public Iterator<T> dfsiterator() {
        return new Dfsiterator<T>(this);
    }
    class Dfsiterator<T> implements Iterator<T> {
        private Stack<TreeNode> stack;
        private int curModifyCount;

        Dfsiterator(Tree tree) {
            this.stack = new Stack<TreeNode>();
            if (tree.root.data != null) {
                this.stack.push(tree.root);
                this.curModifyCount = modifyCount;
            }
        }

        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if (curModifyCount != modifyCount) {
                throw new ConcurrentModificationException();
            }
            return !this.stack.isEmpty();
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            TreeNode cur = this.stack.pop();
            for (int i = 0; i < cur.children.size(); i++) {
                if (cur.children.get(i) != null) {
                    this.stack.push((TreeNode) cur.children.get(i));
                }
            }
            return (T) cur.data;
        }

        @Override
        public void remove() throws ConcurrentModificationException {
            throw new ConcurrentModificationException();
        }
    }

}
