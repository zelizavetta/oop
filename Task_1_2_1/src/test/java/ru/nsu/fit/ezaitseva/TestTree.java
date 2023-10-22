package ru.nsu.fit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;


public class TestTree {

    @ParameterizedTest
    @MethodSource("testTreeAddingNode")
    void testAddNode(Tree tree, TreeNode<T, N> node, Tree expectedTree) {
        Tree realTree = tree.add(node);
        assert realTree.equals(expectedTree);
    }


    private static Stream<Arguments> testTreeAddingNode() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[]{1, 4, 2, 3}),
                        new Polynomial(new int[]{1, 2, 3, 4}),
                        new Polynomial(new int[]{2, 6, 5, 7})),

                Arguments.of(new Polynomial(new int[]{-1, 4, -2, 3}),
                        new Polynomial(new int[]{1, 2, 3, 4}),
                        new Polynomial(new int[]{0, 6, 1, 7})),
        );
    }

}