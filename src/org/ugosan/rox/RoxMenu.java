/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.ugosan.rox.dialogs.ImageCache;
import org.ugosan.rox.dialogs.ImageChooser;
import org.ugosan.rox.grafo.*;

import java.io.File;

public class RoxMenu extends JMenu implements MouseListener{

    Vertice vertice; //o vertice relativo ao popup

    public RoxMenu(String titulo){

        super(titulo);

    }

    public final void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    public void setVertice(Vertice v){
        this.vertice = v;
    }

    //porque nao mouseclicked? eu hein...
    public final void mouseReleased(MouseEvent e){
        try{
            JMenuItem item = (JMenuItem)e.getSource();
            if(item.getName().equalsIgnoreCase("Atualizar Imagens...")){
                ImageCache.getInstance().atualizarImagens();


            }else if(item.getName().equalsIgnoreCase("Adicionar Imagens...")){
                ImageChooser chooser = ImageChooser.getInstance();
                //chooser.setVisible(true);

            }else{
                if(item.getName().substring(item.getName().lastIndexOf(File.separator)+1).equals("vertice.gif")){
                    vertice.setNome_interno(true);
                }else vertice.setNome_interno(false);
                vertice.setImagem(item.getName());
                Main.getInstance().repaint();






            }
        }catch(Exception ex){
            System.out.println("Erro: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public final void mouseClicked(MouseEvent e){}
    public final void mousePressed(MouseEvent e){}
    public final void mouseDragged(MouseEvent e){}
    public final void mouseEntered(MouseEvent e){}
    public final void mouseExited(MouseEvent e){}


    public final JMenuItem add(JMenuItem item){
        item.addMouseListener(this);
        super.add(item);
    return(item);
    }
}