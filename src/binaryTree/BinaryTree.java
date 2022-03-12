package binaryTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BinaryTree extends JFrame implements InputAdder{
    public Node root = null;
    Container co;
    InputAdder inputAdder;
    int speed=1000;
    public BinaryTree() {
        super("Binary Tree Visualizer");
        inputAdder=this;
        setSize(500, 500);
        co = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                if (root == null) return;
                Queue<Node> queue = new LinkedList<>();
                queue.add(root);
                while (queue.size() > 0) {
                    Node current = queue.poll();
                    if (current.left != null) {
                        g.setColor(new Color(0, 0, 0, 50));
                        g.drawLine(current.getX() + 25, current.getY() + 50, current.left.getX() + 25, current.left.getY());
                        queue.add(current.left);
                    }
                    if (current.right != null) {
                        g.setColor(new Color(0, 0, 0, 50));
                        g.drawLine(current.getX() + 25, current.getY() + 50, current.right.getX() + 25, current.right.getY());
                        queue.add(current.right);
                    }
                }
                g.dispose();
            }
        };
        co.setLayout(null);
        Container content = getContentPane();
        content.setLayout(null);
        content.add(co);
        content.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                co.setSize(content.getSize());
                render();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                new Thread(() -> {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_SPACE:
                            String data = JOptionPane.showInputDialog(e.getComponent(), "Enter Operation and data", "Operation", JOptionPane.PLAIN_MESSAGE);
                            if (data != null && data.length() > 0) {
                                String[] arr= data.split(" ");
                                if (arr.length==2) inputAdder.addInput(arr[0], Integer.parseInt(arr[1]) );
                                else inputAdder.addInput(arr[0], 0 );
                                render();
                            }
                        case KeyEvent.VK_D:
                            if (e.isControlDown()) deSelectAll(root);
                            break;
                        case KeyEvent.VK_DOWN:
                            setSpeed( speed-500 );
                            JOptionPane op = new JOptionPane("Speed: "+speed);
                            JDialog jd = op.createDialog(null, "Speed");
                            jd.setModal(false);
                            jd.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
                            jd.setVisible(true);
                            try { Thread.sleep(1000); } catch (InterruptedException ex) {ex.printStackTrace();}
                            jd.setVisible(false);
                            break;
                        case KeyEvent.VK_UP:
                            setSpeed( speed+500 );
                            JOptionPane op2 = new JOptionPane("Speed: "+speed);
                            JDialog jd2 = op2.createDialog(null, "Speed");
                            jd2.setModal(false);
                            jd2.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
                            jd2.setVisible(true);
                            try { Thread.sleep(1000); } catch (InterruptedException ex) {ex.printStackTrace();}
                            jd2.setVisible(false);
                            break;
                        case KeyEvent.VK_A:
                            if (e.isControlDown()) selectAll(root);
                            break;
                    }
                }).start();
            }
        });
        setVisible( true );
    }

    public void delete( int data ){
        root=delete(root,data);
        repaint();
    }

    protected Node delete(Node root, int data){
        ArrayBinaryTree arr = new ArrayBinaryTree( getTreeHeight() );
        arr.initWithTree( this );

        int childIndex = arr.getLastNodeIndex();
        Node parent = arr.getParentNode(childIndex);
        Node child = arr.tree[childIndex];
        if ( childIndex%2==0 ) {
            co.remove( child );
            parent.right=null;
        }
        else{
            co.remove( child );
            parent.left=null;
        }
        Node dataNode = find( data );
        dataNode.data = child.data;
        dataNode.setText( dataNode.data+"" );
        return root;
    }

    public Node find(int data) {
        Node node = find(root, data);
        if ( node !=null ) {
            showOutput("Found the Element: "+data );
            node.isHighLighted=true;
            node.highlight();
        }
        else showOutput("Element :"+data+" Not Found" );
        return node;
    }

    protected Node find(Node root, int data) {
        if (root == null) return null;

        root.highlight();
        try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}

        if (root.data == data) return root;

        root.highlight();

        Node left = find(root.left, data);
        if (left != null) return left;

        root.highlight();
        try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}
        root.highlight();

        Node right = find(root.right, data);
        if (right != null) return right;

        root.highlight();
        try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}
        root.highlight();
        return null;
    }

    public Node getNode(int i){
        Node n = new Node(i);
        co.add(n);
        return n;
    }

    public void add(int i) {
        if (root == null) root = getNode(i);
        else {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (queue.size() > 0) {
                Node currentNode = queue.poll();
                if (currentNode.left == null) {
                    currentNode.left = getNode(i);
                    break;
                } else if (currentNode.right == null) {
                    currentNode.right = getNode(i);
                    break;
                } else {
                    queue.add(currentNode.left);
                    queue.add(currentNode.right);
                }
            }
        }
    }

    public void inorder() {
        JLabel out = getOutputChanger();
        out.setText("Inorder: ");
        inorder(root, out);
    }

    protected void inorder( Node root, JLabel out ){
        if (root == null) return;

        inorder( root.left , out );

        root.highlight();
        out.setText( out.getText() + " " + root.data );
        try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}

        inorder( root.right , out );
    }

    public void preorder() {
        JLabel out = getOutputChanger();
        out.setText("Preorder: ");
        preorder(root, out);
    }

    protected void preorder( Node root, JLabel out ){
        if (root == null) return;

        root.highlight();
        out.setText( out.getText() + " " + root.data );
        try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}

        preorder( root.left , out );
        preorder( root.right , out );
    }

    public void postorder() {
        JLabel out = getOutputChanger();
        out.setText("Postorder: ");
        postorder(root, out);
    }

    protected void postorder( Node root, JLabel out ){
        if (root == null) return;

        postorder( root.left , out );
        postorder( root.right , out );

        root.highlight();
        out.setText( out.getText() + " " + root.data );
        try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}
    }

    public int getTreeHeight(){
        int count=0;
        for ( Node temp=root;temp!=null; temp=temp.left) count++;
        return count;
    }

    public void getVisualTreeHeight() {
        JLabel out = getOutputChanger();
        out.setText( "Height of the Tree: 0" );
        int height= getVisualTreeHeight(root,out);
    }

    protected int getVisualTreeHeight(Node root, JLabel out) {
        if (root == null) return 0;

        root.highlight();
        try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}

        int leftHeight = getVisualTreeHeight(root.left, out);
        int rightHeight = getVisualTreeHeight(root.right, out);

        if (leftHeight>=rightHeight && root.left !=null){
            deSelectAll( root.right );
            int height=leftHeight+1;
            out.setText( "Height of the Tree: "+ height );
            return height;
        }
        if( root.right !=null ){
            deSelectAll( root.left );
            int height=rightHeight+1;
            out.setText( "Height of the Tree: "+ height );
            return height;
        }
        return 1;
    }

    public int getTreeSize(){ return getTreeSize( root ); }
    protected int getTreeSize(Node root){
        if (root == null) return 0;

        return 1 + getTreeSize( root.left ) + getTreeSize( root.right );
    }

    protected int getTreeSpace(){
        return (int)(Math.pow( 2, getTreeHeight() ) - 1);
    }

    protected Node[] getNodeList() {
        if ( root == null ) return null;

        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree( getTreeHeight() );
        arrayBinaryTree.initWithTree( this );

        return arrayBinaryTree.tree;
    }

    public void selectAll(Node root) {
        if (root == null) return;

        root.isHighLighted = false;
        root.highlight();

        selectAll(root.left);
        selectAll(root.right);
    }

    public void deSelectAll(Node root) {
        if (root == null) return;

        root.isHighLighted = true;
        root.highlight();

        deSelectAll(root.left);
        deSelectAll(root.right);
    }

    @Override
    public void addInput(String operation, int data) {
        switch (operation){
            case "find": find( data );break;
            case "add": add( data );break;
            case "height": getVisualTreeHeight();break;
            case "speed": setSpeed(data);break;
            case "inorder": inorder();break;
            case "preorder": preorder();break;
            case "postorder": postorder();break;
            case "delete": delete(data);break;
        }
    } // code for component

    public void render() {
        if (root == null) return;
        int nodeHeight = getTreeHeight();
        int height = co.getHeight();
        int width = co.getWidth();
        Node[] nodesList = getNodeList();
        int ind=0;
        for (int i = 0; i < nodeHeight; i++) {
            int y = (height / (1 + nodeHeight)) * (i + 1);
            int nodeWidth = (int) Math.pow(2, i);
            for (int j = 0; j < nodeWidth; j++) {
                Node currentNode = nodesList[ ind++ ];
                if ( currentNode == null ) continue;
                int x = (width / (1 + nodeWidth)) * (j + 1);
                currentNode.setLocation(x, y);
            }
        }
    } // code for component

    public void showOutput(String text){
        JOptionPane.showMessageDialog(this,text,"Output",JOptionPane.INFORMATION_MESSAGE);
    } // code for component

    public JLabel getOutputChanger(){
        JLabel text = new JLabel();
        new Thread(() -> {
            JOptionPane.showMessageDialog(this,text,"Output",JOptionPane.INFORMATION_MESSAGE);
            deSelectAll(root);
        }).start();
        return text;
    } // code for component

    public void setSpeed(int speed){
        this.speed = speed;
    } // code for component

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        for (int i=1;i<=10;i++){
            tree.add(i);
        }
        System.out.println( tree.getTreeHeight() );
    }

}
