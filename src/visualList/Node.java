package visualList;

import javax.swing.*;
import java.awt.*;

public class Node<Type> extends JLabel {
    Type data;
    Node<Type> next;
    boolean isSelected=false;
    public Node( Type data ){
        setData( data );
        next = null;
        setSize( 400 , 40 );
        setHorizontalAlignment( CENTER );
        setVerticalAlignment( CENTER );
        setCursor( new Cursor( Cursor.HAND_CURSOR ) );
    }
    public void setData( Type data ){
        this.data = data;
        setText( data.toString() );
    }
    public void setLocation(int x, int y) {
        super.setLocation( x - getWidth()/2 , y - getHeight()/2 );
    }

    public void select(){
        isSelected = true;
        repaint();
    }
    public void deSelect(){
        isSelected = false;
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (isSelected) g.setColor( new Color( 255, 0 ,0,100 ) );
        else g.setColor( new Color(0,0,255,100) );
        g.fillRoundRect(0,0,getWidth()-4,getHeight()-4,10,10);
    }

    @Override
    public String toString() {
        return  data+"" ;
    }
}
