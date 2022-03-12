package binaryTree;

public class ArrayBinaryTree {
    Node[] tree=null;
    public ArrayBinaryTree( int height ){
        tree = new Node[ (int)(Math.pow( 2, height ) - 1) ];
    }
    public void initWithTree( BinaryTree binaryTree ){
        Node root = binaryTree.root;
        tree[0]=root;
        for ( int i=0;i<tree.length;i++ ){
            Node parent = tree[i];
            if ( parent == null ) continue;
            if ( getLeftChild( i ) >= tree.length ) break;
            tree[ getLeftChild( i ) ] = parent.left;
            tree[ getRightChild( i ) ] = parent.right;
        }
    }
    public int getLeftChild( int parentIndex ){
        return 2*parentIndex + 1;
    }
    public int getRightChild( int parentIndex ){
        return 2*parentIndex + 2;
    }
    public int getParent( int childIndex ){
        return childIndex/2;
    }
    public Node getParentNode(int childIndex){
        return tree[ getParent(childIndex) ];
    }
    public Node getLastNode(){
        for (int i = tree.length-1 ; i>=0 ; i--){
            if (tree[i]!=null) return tree[i];
        }
        return null;
    }
    public int getLastNodeIndex(){
        for (int i = tree.length-1 ; i>=0 ; i--){
            if (tree[i]!=null) return i;
        }
        return -1;
    }

}
