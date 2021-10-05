package com.example.social_network_app.DataStructure;


public class RBTree<T extends Comparable<T>> {

    Node<T> root;


    public RBTree() {
        root = null;
    }

    /**
     * As the norm RBTree, except when the x's value equals to root's value
     * it will add x's IDlist into the root node IDlist.
     * @param root root Node
     * @param x inserted Node
     */
    private void insertRecurse(Node<T> root, Node<T> x) {
        int cmp = root.value.compareTo(x.value);
        if (cmp > 0) {
            if (root.left.value == null) {
                root.left = x;
                x.parent = root;
            } else {
                insertRecurse(root.left, x);
            }
        } else if (cmp < 0) {
            if (root.right.value == null) {
                root.right = x;
                x.parent = root;
            } else {
                insertRecurse(root.right, x);
            }
        }else {
            x.parent = root;
            root.GameIDList.add(x.GameIDList.get(0));
        }

    }

    /**
     * The method to make sure RBTree is balanced.
     * @param x the inserted Node.
     */

    public void insert(Node<T> x) {
        if (root == null) {
            root = x;
        } else {
            insertRecurse(root, x);
        }
        while (x.value != root.value && x.parent.colour == Colour.RED) {
            boolean left  = x.parent == x.parent.parent.left;
            Node<T> uncle = left ? x.parent.parent.right : x.parent.parent.left;
            if (uncle.colour == Colour.RED) {
                x.parent.colour=Colour.BLACK;
                uncle.colour=Colour.BLACK;
                x.parent.parent.colour=x.parent.parent==root? Colour.BLACK:Colour.RED;
                x = x.parent.parent;
            } else {
                if (x.value == (left ? x.parent.right.value : x.parent.left.value)) {
                    x = x.parent;
                    if (left) {
                        if (x.value == root.value)
                            root = x.right;
                        rotateLeft(x);
                    } else {
                        if (x.value==root.value)
                            root=x.left;
                        rotateRight(x);
                    }
                }
                x.parent.colour = Colour.BLACK;
                x.parent.parent.colour = Colour.RED;
                if (left) {
                    x=x.parent.parent;
                    if(x.value==root.value)
                        root=x.left;
                    rotateRight(x);
                } else {
                    x=x.parent.parent;
                    if(x.value==root.value)
                        root=x.right;
                    rotateLeft(x);
                }
            }
        }
        root.colour = Colour.BLACK;
    }

    /**
     * Left Rotate
     * @param x
     */
    public void rotateLeft(Node<T> x) {
        if (x.parent != null) {

            if (x.parent.left.value == x.value) {
                x.parent.left = x.right;
            } else {
                x.parent.right = x.right;
            }
        }
        x.right.parent = x.parent;

        x.parent = x.right;

        x.right = x.parent.left;
        x.right.parent = x;

        x.parent.left = x;
    }

    /**
     * Right Rotate
     * @param x
     */
    public void rotateRight(Node<T> x) {
        if (x.parent!=null){
            if (x.parent.left.value==x.value)
                x.parent.left=x.left;
            else x.parent.right=x.left;
        }
        x.left.parent=x.parent;
        x.parent=x.left;
        x.left=x.parent.right;
        x.left.parent=x;
        x.parent.right=x;
    }

    /**
     * method to get the root Node
     * @return root Node
     */
    public Node<T> getRoot() {
        return root;
    }

    public Node<T> find(Node<T> x, T v) {
        if (x.value == null)
            return null;

        int cmp = v.compareTo(x.value);
        if (cmp < 0)
            return find(x.left, v);
        else if (cmp > 0)
            return find(x.right, v);
        else
            return x;
    }


    public Node<T> search(T key) {
        return find(root, key);
    }

    /**
     * Return the result of a pre-order traversal of the tree
     *
     * @param tree Tree we want to pre-order traverse
     * @return pre-order traversed tree
     */
    private String preOrder(Node<T> tree) {
        if (tree != null && tree.value != null) {
            String leftStr = preOrder(tree.left);
            String rightStr = preOrder(tree.right);
            return tree
                    + (leftStr.isEmpty() ? leftStr : "\n" + leftStr)
                    + (rightStr.isEmpty() ? rightStr : "\n" + rightStr);
        }
        return "";
    }

    public String preOrder() {
        return preOrder(root);
    }
}
