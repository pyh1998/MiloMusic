package com.example.social_network_app.DataStructure;

import androidx.annotation.NonNull;

import java.util.LinkedList;

/**
 * @author Yuhui Pang
 *
 * The Node class used in RBTree, it can store key and value
 * @param <T>
 */
public class Node<T> {

    Colour colour;			// Node colour
    T value; 				// Node value
    Node<T> parent; 		// Parent node
    Node<T> left, right; 	// Children nodes
    LinkedList<Object> objects;

    public T getValue() {
        return value;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public LinkedList<Object> getObjects() {
        return objects;
    }

    public Node(T value, Object object) {
        this.value  = value;
        this.colour = Colour.RED;
        this.objects =new LinkedList<>();
        objects.add(object);
        this.parent = null;

        // Initialise children leaf nodes
        this.left = new Node<>();
        this.right = new Node<>();
        this.left.parent = this;
        this.right.parent = this;
    }

    public Node() {
        this.value = null;
        this.colour = Colour.BLACK;
    }

    @NonNull
    @Override
    public String toString() {
        return "value:" + this.value;
    }
}
