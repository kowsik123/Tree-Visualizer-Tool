package binaryTree;

import javax.swing.JLabel;
import java.awt.*;

public class Node extends JLabel {
    int data;
    Node right;
    Node left;
    boolean isHighLighted=false;
    public Node(String data ){
        this.data= Integer.parseInt(data);
        left=null;
        right=null;

        setCursor( new Cursor( Cursor.HAND_CURSOR ) );
        setText(data+"");
        setSize( 50,50 );
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    public Node(int data ){
        this.data= data;
        left=null;
        right=null;

        setCursor( new Cursor( Cursor.HAND_CURSOR ) );
        setText(data+"");
        setSize( 50,50 );
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    public void highlight(){
        isHighLighted=!isHighLighted;
        repaint();
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x-getWidth()/2, y-getHeight()/2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isHighLighted) g.setColor( new Color(0,0,255,120) );
        else g.setColor( new Color(255,0,0,100) );
        g.fillOval( 0,0, getWidth(),getHeight() );
    }

    @Override
    public String toString() {
        return data+"";
    }

}
