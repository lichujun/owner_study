package com.lee.owner.structure.impl;

import com.lee.owner.structure.Queue;

/**
 * 栈
 * @Date 2019-08-23 16:38
 * @Author joseph.li
 */
public class MyStack<T> implements Queue<T> {

    private Node<T> head;

    private Node<T> current;

    @Override
    public void push(T t) {
        Node<T> node = new Node<>(t);
        if (head == null) {
            head = node;
            current = head;
        } else {
            node.pre = current;
            current = node;
        }
    }

    @Override
    public T pop() {
        if (current == null) {
            return null;
        } else {
            T t = current.data;
            if (current == head) {
                current = null;
                head = null;
            } else {
                current = current.pre;
            }
            return t;
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> pre;

        Node(T data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyStack<>();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println();
    }
}
