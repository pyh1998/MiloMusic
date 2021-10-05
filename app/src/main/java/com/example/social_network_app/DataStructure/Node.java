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
    LinkedList<Integer> GameIDList;

    public Colour getColour() {
        return colour;
    }

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

    public LinkedList<Integer> getGameIDList() {
        return GameIDList;
    }

    public Node(T value, int id) {
        this.value  = value;
        this.colour = Colour.RED;
        this.GameIDList=new LinkedList<>();
        GameIDList.add(id);
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
        StringBuilder res = new StringBuilder("key: " + this.value + "; ");
        LinkedList<Integer> gameIDList = getGameIDList();
        for (int i = 0; i < gameIDList.size(); i++) {
            if (i == gameIDList.size() - 1) {
                res.append(gameIDList.get(i));
            } else {
                res.append(gameIDList.get(i)).append(", ");
            }

        }
        return res.toString();
    }
}
