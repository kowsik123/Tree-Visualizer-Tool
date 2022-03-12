package VisualList;

import binaryTree.BinaryTree;
import logging.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Stack;

public class VisualStack<Type> extends JFrame {
    Container co;
    Node<Type> top;
    int speed=1000;
    Logger log = new Logger();
    public VisualStack(){
        super( "Stack Visualizer Tool" );
        setSize( 400,550 );
        setLocation( 400,100 );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        co = getContentPane();
        co.setLayout( null );
        co.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
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
                                if (arr.length==2) {
                                    addInput(arr[0],arr[1]);
                                }
                                else addInput(arr[0], null);
                                render();
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            speed = speed-500 ;
                            JOptionPane op = new JOptionPane("Speed: "+speed);
                            JDialog jd = op.createDialog(null, "Speed");
                            jd.setModal(false);
                            jd.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
                            jd.setVisible(true);
                            try { Thread.sleep(1000); } catch (InterruptedException ex) {ex.printStackTrace();}
                            jd.setVisible(false);
                            break;
                        case KeyEvent.VK_UP:
                            speed = speed+500;
                            JOptionPane op2 = new JOptionPane("Speed: "+speed);
                            JDialog jd2 = op2.createDialog(null, "Speed");
                            jd2.setModal(false);
                            jd2.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
                            jd2.setVisible(true);
                            try { Thread.sleep(1000); } catch (InterruptedException ex) {ex.printStackTrace();}
                            jd2.setVisible(false);
                            break;
                    }
                }).start();
            }
        });
        setVisible( true );
    }

    public void render(){
        int nodeHeight = length();
        int height = co.getHeight();
        int width = co.getWidth();
        Node<Type> tmp = top;
        for (int i=0;i<nodeHeight;i++){
            int y = ( height / (1 + nodeHeight)) * (i + 1);
            tmp.setSize( (int) (width*0.5), 40 );
            tmp.setLocation( width/2 , y );
            tmp=tmp.next;
        }
    }

    public void showOutput(String text){
        JOptionPane.showMessageDialog(this,text,"Output",JOptionPane.INFORMATION_MESSAGE);
    } // code for component

    public void push(Type data){
        log.addLog( new Log( "push",data.toString()) );
        Node<Type> tmp = top;
        top = getNode( data );
        top.next = tmp;
    }

    public void visualPush( Type data ){
        push( data );
        top.select();
        render();
//        showOutput("Pushed Element: " + top );
        top.deSelect();
    }

    public Type pop(){
        if (isEmpty()) return null;
        Node<Type> tmp = top;
        top = top.next;
        co.remove(tmp);
        log.addLog( new Log( "pop",null,tmp.data.toString()) );
        return tmp.data;
    }

    public Type visualPop(){
        Node<Type> tmp = peekNode();
        if (tmp==null) {
            JOptionPane.showMessageDialog(this,"Stack UnderFLow","Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        tmp.select();
        showOutput("Pop Element: " + tmp.data.toString() );
        Type ele = pop();
        render();
        repaint();
        return ele;
    }

    public int length(){
        int count=0;
        for (Node<Type> tmp = top;tmp!=null;tmp=tmp.next){ count++; }
        return count;
    }

    public void visualLength(){
        showOutput( "Total Elements in Stack: "+length() );
    }

    public Type peek() {
        log.addLog( new Log( "peek",top+"") );
        return top.data;
    }

    protected Node<Type> peekNode(){
        return top;
    }

    public void visualPeek(){
        if (isEmpty()) return;
        top.select();
        showOutput( "Peek Element: "+ peek() );
        top.deSelect();
    }

    public boolean isEmpty(){
        return top==null;
    }

    public Node<Type> getNode( Type data ){
        Node<Type> newNode = new Node<Type>(data);
        co.add( newNode );
        return newNode;
    }

    public void addInput(String operation, String data) {
        switch (operation){
            case "push": visualPush( (Type) (Integer) Integer.parseInt(data) );break;
            case "pop": visualPop();break;
            case "peek": visualPeek();break;
            case "length": visualLength();break;
            case "log": showOutput( log.toString() );break;
        }
    }

    public static void main(String[] args) {
        int[] arr = { 1,2,3,4,5,6 };
        Stack<Integer> stack= new Stack<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i:arr){
            stack.push( i );
            list.add(0 , i );
        }
        for ( int i=0;i<arr.length;i++ ) System.out.print( stack.pop() +" " );
        System.out.println("\nlist "+list);
//        System.out.println("stack "+stack);
    }
}
