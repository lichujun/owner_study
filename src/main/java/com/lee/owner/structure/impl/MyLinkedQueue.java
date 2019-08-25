package com.lee.owner.structure.impl;

import com.lee.owner.structure.Queue;

/**
 * 链表队列消息
 * @Date 2019-08-23 18:05
 * @Author joseph.li
 */
public class MyLinkedQueue<T> implements Queue<T> {

    private Node<T> head;

    private Node<T> current;

    @Override
    public void push(T t) {
        Node<T> node = new Node<>(t);
        if (head == null) {
            head = node;
            current = node;
        } else {
            current.next = node;
            current = node;
        }
    }

    @Override
    public T pop() {
        if (head == null) {
            return null;
        } else {
            Node<T> node = head;
            if (current == head) {
                current = null;
                head = null;
            } else {
                head = head.next;
            }
            return node.data;
        }
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        MyLinkedQueue<Integer> myLinkedQueue = new MyLinkedQueue<>();
        myLinkedQueue.push(1);
        myLinkedQueue.push(2);
        myLinkedQueue.push(3);
        System.out.println(myLinkedQueue.pop());
        System.out.println(myLinkedQueue.pop());
        System.out.println(myLinkedQueue.pop());
        System.out.println(myLinkedQueue.pop());
    }

}
