package binaryTree;

import VisualList.VisualStack;
import javax.swing.*;
import java.util.Stack;

public class BinarySearchTree extends BinaryTree{
    public void add(int data) {
        root = add( root , data );
    }
    private Node add( Node root , int data ) {
        if (root == null) return getNode(data);

        if (data < root.data) root.left = add( root.left , data );
        else root.right = add( root.right , data );

        return root;
    }

    public int getTreeHeight() {
        return getTreeHeight( root );
    }
    private int getTreeHeight( Node root ) {
        if (root == null) return 0;

        int leftHeight = getTreeHeight( root.left );
        int rightHeight = getTreeHeight( root.right );

        return 1 + Math.max( leftHeight, rightHeight );
    }

    public Node find(int data) {
        Node dataNode = find( root , data );
        showOutput( (dataNode == null)? "Element Not Found "+data : "Found Element "+data );
        if (dataNode!=null) dataNode.highlight();
        return dataNode;
    }
    protected Node find(Node root, int data) {
        if (root == null) return null;

        root.highlight();
        try {Thread.sleep( speed );} catch (InterruptedException ignored) {}

        if (root.data == data) return root;

        root.highlight();
        try {Thread.sleep( speed );} catch (InterruptedException ignored) {}

        if ( data < root.data ) return find( root.left , data );
        return find( root.right , data );
    }

    public void delete(int data) {
        root = delete( root,data );
        deSelectAll( root );
        repaint();
    }
    protected Node delete(Node root, int data) {
        if (root == null) return null;

        if (data < root.data) root.left = delete( root.left ,data );
        else if( data> root.data ) root.right = delete( root.right ,data );
        else{
            root.highlight();
            try {Thread.sleep( speed );} catch (InterruptedException ignored) {}

            if (root.left == null) {
                co.remove( root );
                return root.right;
            }
            if (root.right == null) {
                co.remove( root );
                return root.left;
            }
            root.data = min(root.right);
            root.setText( root.data+"" );

            try {Thread.sleep( speed );} catch (InterruptedException ignored) {}

            root.right = delete(root.right,root.data);
        }

        return root;
    }

    protected int min( Node root ){
        int minimum = root.data;
        for ( Node tmp = root;tmp!=null;tmp=tmp.left ){ minimum=tmp.data; }
        return minimum;
    }

    public void inorder(){
        if (root == null) return;
        JLabel out = getOutputChanger();
        VisualStack<Node> stack = new VisualStack<>();
        out.setText( "Inorder :" );
        Node curr = root;
        showOutput("start Inorder traversal");
        while( stack.length()>0 || curr!=null ){
            while (curr != null) {
                stack.visualPush( curr );
                curr = curr.left;
                try {Thread.sleep(speed);} catch (InterruptedException e) { e.printStackTrace(); }
            }
            curr = stack.visualPop();
            try {Thread.sleep(speed);} catch (InterruptedException e) { e.printStackTrace(); }
            out.setText( out.getText() + " " + curr.data );
            curr.highlight();
            curr = curr.right;
        }
        stack.dispose();
    }

    public void preorder() {
        if (root == null) return;
        Stack<Node> stack = new Stack<>();
        stack.push( root );
        Node curr=root;
        while( stack.size()>0 || curr!=null ){
            while( curr!=null ){
                System.out.println( curr );

            }
        }
    }

    public void postorder(){
        if (root == null) return;


    }

    public static void main(String[] args) {
        BinarySearchTree bts = new BinarySearchTree();
        bts.add( 5 );
        bts.add( 1 );
        bts.add( 3 );
        bts.add( 7 );
        bts.add( 6 );
        bts.add( 8 );
    }
}
