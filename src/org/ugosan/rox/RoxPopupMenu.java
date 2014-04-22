/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RoxPopupMenu extends JPopupMenu implements KeyListener{



    public RoxPopupMenu(){
    super();


    }


    public final void paintComponent(Graphics g){
        super.paintComponent(g);
    }


    public final Component add(Component c){
        c.addKeyListener(this);
        super.add(c);
    return(c);
    }



    public final void keyPressed(KeyEvent e){
        try{

            if(e.getSource().getClass().equals(Class.forName("ugo.rox.RoxVerticeTextField"))){

                RoxVerticeTextField t = (RoxVerticeTextField)e.getSource();
                String texto = t.getText();

                if(e.getKeyCode()==e.VK_BACK_SPACE){

                    t.setRotulo(texto.substring(0,texto.length()-1));

                }else{
                    texto += e.getKeyChar();
                    t.setRotulo(texto.trim());
                }
                Rox.getInstance().repaint();

            }
            }catch(Exception ex){}
    }

    public final void keyTyped(KeyEvent e){


    }
    public final void keyReleased(KeyEvent e){

    }

}