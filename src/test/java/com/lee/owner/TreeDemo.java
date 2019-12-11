package com.lee.owner;

import com.lee.owner.structure.tree.BinarySearchTree;
import com.lee.owner.structure.tree.Tree;
import org.junit.Test;

/**
 * @author joseph.li
 * @date 2019-12-03 19:27
 */
public class TreeDemo {

    @Test
    public void test() {
        Tree<Integer, String> tree = new BinarySearchTree<>();
        tree.insert(45, "45");
        tree.insert(12, "45");
        tree.insert(45, "45");
        tree.insert(467, "45");
        tree.insert(4444, "45");
        tree.insert(1122, "45");
        System.out.println(tree);
    }

}
