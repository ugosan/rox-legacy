/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;



import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.ugosan.rox.grafo.*;

import java.io.File;
/**
 *  Componente usado apenas para atribuir o rótulo ao Vértice.
 * @author Ugo Braga Sangiorgi
 **/
public class RoxVerticeTextField extends JTextField {

    Vertice vertice; //o vertice relativo ao popup
    String rotulo;

    public RoxVerticeTextField(Vertice v){
        super();
        this.vertice = v;
        rotulo = v.getRotulo();
        super.setText(rotulo);
    }

    public void setRotulo(String rotulo){
        this.rotulo = rotulo;
        vertice.setRotulo(rotulo);

    }

    public final void paintComponent(Graphics g){
        super.paintComponent(g);
    }




}