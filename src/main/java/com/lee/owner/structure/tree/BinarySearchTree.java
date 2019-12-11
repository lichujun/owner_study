package com.lee.owner.structure.tree;

/**
 * 二分搜索树
 * @author joseph.li
 * @date 2019-12-05 20:15
 */
public class BinarySearchTree<K extends Comparable<K>, V> implements Tree<K, V> {

    private int size;

    private Node<K, V> root;

    private Node<K, V> insert(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }
        int compareValue = key.compareTo(node.key);
        if (compareValue == 0) {
            node.value = value;
        } else if (compareValue > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.left = insert(node.left, key, value);
        }
        return node;
    }

    @Override
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    @Override
    public int size() {
        return size;
    }


}
