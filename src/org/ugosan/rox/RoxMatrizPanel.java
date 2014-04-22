/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.swing.*;

import org.ugosan.rox.grafo.*;

import java.io.*;


public class RoxMatrizPanel extends JPanel{

    private int[][] matrizAdjacencias;
    private int[][] matrizIncidencias;

    private int qtdVertices;
    private int qtdArestas;

    public RoxMatrizPanel() {
        this.setFont(new Font("Courier",Font.PLAIN,11));
    }

    public final void paintComponent(Graphics g){
        int x=22,y=50;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        qtdVertices = Rox.getInstance().getGrafo().getQtdVertices();
        matrizAdjacencias = new int[qtdVertices][qtdVertices];
        matrizAdjacencias = Rox.getInstance().getGrafo().getMatrizAdjacencias();
        g2.drawString("Matriz de Adjacencias",x,y-10);
        for(int i=0;i<qtdVertices;i++){
            for(int j=0;j<qtdVertices;j++){
                g2.drawString(matrizAdjacencias[i][j]+"",x,y);

            x = x+10;
            }
        y = y+10;
        x=22;
        }


        x=22;
        y=y+20;
        qtdArestas = Rox.getInstance().getGrafo().getQtdArestas();
        matrizIncidencias = new int[qtdVertices][qtdArestas];
        matrizIncidencias = Rox.getInstance().getGrafo().getMatrizIncidencias();
        g2.drawString("Matriz de Incidencias",x,y-10);
        for(int i=0;i<qtdVertices;i++){
            for(int j=0;j<qtdArestas;j++){
                g2.drawString(matrizIncidencias[i][j]+"",x,y);
            x = x+10;
            }
        y = y+10;
        x=22;
        }

    }






}