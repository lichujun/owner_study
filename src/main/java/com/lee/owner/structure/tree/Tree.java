package com.lee.owner.structure.tree;

/**
 * @author joseph.li
 * @date 2019-12-05 20:14
 */
public interface Tree<K extends Comparable<K>, V> {

    void insert(K key, V value);

    int size();

    class Node<K, V> {
        K key;

        V value;

        Node<K, V> left;

        Node<K, V> right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
