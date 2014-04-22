/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;

import javax.swing.*;

import org.ugosan.rox.grafo.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
/**
*   Classe de seleção de análises para compilar
* @author Ugo Braga Sangiorgi
*/
public class RoxAnaliseCompilerChooser extends JFrame implements ActionListener{
    JList list_1;
    JScrollPane sp_list_1;
    JLabel label_1;
    JButton button_1;
    JButton button_2;
    JList list_2;
    JScrollPane sp_list_2;
    JButton button_3;
    JButton button_4;
    JLabel label_2;
    JButton button_6;
    DefaultListModel listModel_list_1;
    DefaultListModel listModel_list_2;
    public RoxAnaliseCompilerChooser() {
        RoxAnaliseCompilerChooserLayout customLayout = new RoxAnaliseCompilerChooserLayout();

        getContentPane().setLayout(customLayout);

        listModel_list_1 = new DefaultListModel();


        list_1 = new JList(listModel_list_1);
        sp_list_1 = new JScrollPane(list_1);
        getContentPane().add(sp_list_1);

        label_1 = new JLabel("Análises encontradas");
        getContentPane().add(label_1);

        button_1 = new JButton(">");
        getContentPane().add(button_1);

        button_2 = new JButton("<");
        getContentPane().add(button_2);

        listModel_list_2 = new DefaultListModel();

        list_2 = new JList(listModel_list_2);
        sp_list_2 = new JScrollPane(list_2);
        getContentPane().add(sp_list_2);

        button_3 = new JButton("Ok");
        getContentPane().add(button_3);

        button_4 = new JButton("Cancelar");
        getContentPane().add(button_4);

        label_2 = new JLabel("Compilar");
        getContentPane().add(label_2);

        button_6 = new JButton("Todas");
        getContentPane().add(button_6);

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(("img"+File.separator+"icone.gif")));
        setSize(getPreferredSize());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        this.setTitle("Compilar Análises");

        button_1.addActionListener(this);
        button_2.addActionListener(this);
        button_3.addActionListener(this);
        button_4.addActionListener(this);
        button_6.addActionListener(this);

        ArrayList a = new ArrayList(RoxAnaliseLoader.getInstance().getNomesArquivos());

        for(int i=0;i<a.size();i++){
            listModel_list_1.addElement(a.get(i));
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button_6){
            this.dispose();
            RoxAnaliseCompiler compilador = new RoxAnaliseCompiler(System.getProperty("user.dir"), Rox.conf.getProperty("analysis.prefix"));
            compilador.start();
        }else if(e.getSource()==button_4){
            this.dispose();
        }else if(e.getSource()==button_1){
            Object[] a = list_1.getSelectedValues();
            for(int i=0;i<a.length;i++)
                listModel_list_2.addElement(a[i]);
        }else if(e.getSource()==button_2){
            Object[] a = list_2.getSelectedValues();
            for(int i=0;i<a.length;i++)
                listModel_list_2.remove(listModel_list_2.indexOf(a[i]));
        }else if(e.getSource()==button_3){

            RoxAnaliseCompiler compilador = new RoxAnaliseCompiler(System.getProperty("user.dir"), Rox.conf.getProperty("analysis.prefix"));
            String[] analises = new String[listModel_list_2.getSize()];
            listModel_list_2.copyInto(analises);

            this.dispose();
            compilador.start(analises);

        }



    }

}

class RoxAnaliseCompilerChooserLayout implements LayoutManager {

    public RoxAnaliseCompilerChooserLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 707 + insets.left + insets.right;
        dim.height = 388 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+40,288,272);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+16,288,24);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+310,insets.top+144,44,32);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+310,insets.top+184,44,32);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+384,insets.top+40,296,272);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+336,104,24);}
        c = parent.getComponent(6);
        if (c.isVisible()) {c.setBounds(insets.left+344,insets.top+336,104,24);}
        c = parent.getComponent(7);
        if (c.isVisible()) {c.setBounds(insets.left+384,insets.top+16,296,24);}
        c = parent.getComponent(8);
        if (c.isVisible()) {c.setBounds(insets.left+304,insets.top+40,72,56);}
    }
}