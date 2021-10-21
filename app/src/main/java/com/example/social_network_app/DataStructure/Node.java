package com.example.social_network_app.DataStructure;

import java.util.LinkedList;

/**
 * The Node class used in RBTreewhich contains GameIDlist in every Node.
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
        this.left = new Node<T>();
        this.right = new Node<T>();
        this.left.parent = this;
        this.right.parent = this;
    }

    public Node() {
        this.value = null;
        this.colour = Colour.BLACK;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("value:" + this.value );
//        LinkedList<Object> gameIDList = getObjects();
//        for (int i = 0; i < gameIDList.size(); i++) {
//            if (i == gameIDList.size() - 1) {
//                res.append(gameIDList.get(i).toString());
//            } else {
//                res.append(gameIDList.get(i).toString()).append(", ");
//            }
//
//        }
        return res.toString();
    }
}
