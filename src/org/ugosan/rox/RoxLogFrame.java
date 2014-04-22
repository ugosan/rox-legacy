
/*
    Rox - Teoria dos Grafos
   Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/

package org.ugosan.rox;

import javax.swing.*;

import org.ugosan.rox.grafo.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class RoxLogFrame extends JInternalFrame implements ActionListener{

    private JTextArea log;

    private JButton limpar;


    private RoxBottomScrollPane logScroll;
    private BorderLayout layout;




    public RoxLogFrame(){



        layout = new BorderLayout();


        this.getContentPane().setLayout(layout);

        log = new JTextArea();
        log.setEditable(false);

        limpar = new JButton("Limpar");


        limpar.addActionListener(this);


        logScroll = new RoxBottomScrollPane(log);
        //logScroll.setAutoscrolls(true);

        logScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);



        getContentPane().add(logScroll, layout.CENTER);


        getContentPane().add(limpar, layout.PAGE_END);


    }

    public void add(String s){
        log.append(s);

    }

    public void addResultadoAnalise(String nomeAnalise, String s){
        log.append("\n");
        log.append("[Analise: "+nomeAnalise+"]\n");
        log.append(s);
        log.append("\n");
        log.append("[Analise: "+nomeAnalise+"]\n");
        log.append("\n");

    }



    public void actionPerformed(ActionEvent e){
        log.setText("");
    }
}