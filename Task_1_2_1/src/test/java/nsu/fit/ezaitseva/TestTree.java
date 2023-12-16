package nsu.fit.ezaitseva;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;


public class TestTree {
    static Tree tree1 = new Tree("R1");
    static Tree tree2 = new Tree("R1");
    static Tree tree3 = new Tree("R1");
    static Tree treeModify1 = new Tree("R1");
    static Tree treeModify2 = new Tree("R11");

    @BeforeAll
    static void setTree1() {
        TreeNode a1 = tree1.addNode("A1");
        Tree subtree = new Tree("R1");
        TreeNode b1 = subtree.addNode("A1");
        TreeNode b2 = subtree.addNode("B2");
        TreeNode b3 = subtree.addNode("B3");
        Tree new_tree = tree1.addSubTree(subtree);
        TreeNode a2 = tree1.addNode("A2");
    }
    @BeforeAll
    static void setTree2() {
        TreeNode a1 = tree2.addNode("A1");
        Tree subtree = new Tree("R1");
        TreeNode b1 = subtree.addNode("A1");
        TreeNode b2 = subtree.addNode("B2");
        TreeNode b3 = subtree.addNode("B3");
        Tree new_tree = tree2.addSubTree(subtree);
        TreeNode a2 = tree2.addNode("A2");
        tree2.removeNode(a1);
    }
    @BeforeAll
    static void setTree3() {
        TreeNode a1 = tree3.addNode("A1");
        Tree subtree = new Tree("R1");
        TreeNode b1 = subtree.addNode("A1");
        TreeNode b2 = subtree.addNode("B2");
        TreeNode b3 = subtree.addNode("B3");
        Tree new_tree = tree3.addSubTree(subtree);
        TreeNode a2 = tree3.addNode("A2");
    }
    @BeforeAll
    static void setTreeModify1() {
        TreeNode a1 = treeModify1.addNode("A1");
        Tree subtree = new Tree("R1");
        TreeNode b1 = subtree.addNode("A1");
        TreeNode b2 = subtree.addNode("B2");
        TreeNode b3 = subtree.addNode("B3");
        Tree new_tree = treeModify1.addSubTree(subtree);
        TreeNode a2 = treeModify1.addNode("A2");

    }

    @BeforeAll
    static void setTreeModify2() {
        TreeNode a1 = treeModify2.addNode("A1");
        Tree subtree = new Tree("R1");
        TreeNode b1 = subtree.addNode("A1");
        TreeNode b2 = subtree.addNode("B2");
        TreeNode b3 = subtree.addNode("B3");
        Tree new_tree = treeModify2.addSubTree(subtree);
        TreeNode a2 = treeModify2.addNode("A2");
        treeModify2.removeTree(subtree);

    }

    @ParameterizedTest
    @MethodSource("BFS")
    void testBFS(Tree tree,  ArrayList expectedBFS) {
        List<String> fromBfs = new ArrayList<>();
        Iterator iteratorbfs = tree.bfsiterator();
        while (iteratorbfs.hasNext()) {
            fromBfs.add(iteratorbfs.next().toString());
        }
        assert expectedBFS.equals(fromBfs);
    }

    @ParameterizedTest
    @MethodSource("DFS")
    void testDFS(Tree tree, ArrayList expectedDFS) {
        List<String> fromDfs = new ArrayList<>();
        Iterator iteratordfs = tree.dfsiterator();
        while (iteratordfs.hasNext()) {
            fromDfs.add(iteratordfs.next().toString());
        }
        assert expectedDFS.equals(fromDfs);
    }

    @ParameterizedTest
    @MethodSource("treeEquals")
    void equals(Tree tree1, Tree tree2, boolean result) {
        Assertions.assertEquals(tree1.equals(tree2), result);
    }

    @ParameterizedTest
    @MethodSource("errorModify")
    void testErrorModify(Tree tree, Exception excExpected) {
        try {
            Iterator iterator = tree.bfsiterator();
            TreeNode x = tree.addNode("x");
        }
        catch (Exception exception) {
            Assertions.assertEquals(exception.getClass(), excExpected.getClass());
        }
    }


    private static Stream<Arguments> BFS() {
        return Stream.of(
                Arguments.of(tree1, new ArrayList<String>(Arrays.asList("R1", "A1", "R1", "A2", "A1", "B2", "B3"))),
                Arguments.of(tree2, new ArrayList<String>(Arrays.asList("R1", "R1", "A2", "A1", "B2", "B3")))
        );
    }

    private static Stream<Arguments> DFS() {
        return Stream.of(
                Arguments.of(tree1, new ArrayList<String>(Arrays.asList("R1", "A2", "R1", "B3", "B2", "A1", "A1"))),
                Arguments.of(tree2, new ArrayList<String>(Arrays.asList("R1", "A2", "R1", "B3", "B2", "A1")))
        );
    }

    private static Stream<Arguments> treeEquals() {
        return  Stream.of(
          Arguments.of(tree1, tree2, false),
                Arguments.of(tree1, tree3, true)
        );
    }

    private static Stream<Arguments> errorModify() {
        return Stream.of(
                Arguments.of(treeModify1, new ConcurrentModificationException()),
                Arguments.of(treeModify2, new ConcurrentModificationException())
        );
    }

}