/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox.dialogs;

/**
 *  Dialog de informações
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.ugosan.rox.Main;

import java.io.*;

public class AboutDialog extends JFrame implements ActionListener{
    JLabel label_1;
    JLabel label_2;
    JTextArea textarea_1;
    JScrollPane sp_textarea_1;
    JButton button_ok;
    JLabel label_3;
    JLabel label_4;

    public AboutDialog(){
        RoxSobreDialogLayout customLayout = new RoxSobreDialogLayout();

        getContentPane().setLayout(customLayout);

        label_1 = new JLabel(Main.versao+" Copyright (C) 2003 - Ugo Braga Sangiorgi - http://www.mycgiserver.com/~ugosan/rox");
        getContentPane().add(label_1);

        label_2 = new JLabel(Main.p.getProperty("about.text"));
        getContentPane().add(label_2);

        textarea_1 = new JTextArea("");
        textarea_1.setEditable(false);
        sp_textarea_1 = new JScrollPane(textarea_1);
        getContentPane().add(sp_textarea_1);

        this.montaGPL();
        sp_textarea_1.getVerticalScrollBar().setValue(0);

        button_ok = new JButton("Ok");
         button_ok.addActionListener(this);
        getContentPane().add(button_ok);

        label_3 = new JLabel(Main.p.getProperty("about.GPL"));
        getContentPane().add(label_3);

        label_4 = new JLabel(Main.p.getProperty("about.author"));
        getContentPane().add(label_4);

        setSize(getPreferredSize());
        setResizable(false);
        this.setIconImage(Main.getInstance().getIconImage());
        setTitle(Main.p.getProperty("about.title"));


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void montaGPL(){
        try{

            BufferedReader in = new BufferedReader(new FileReader("gpl.txt"));

    	    String inputLine = "";
            while(inputLine!=null){
                inputLine = in.readLine();
                if(inputLine!=null) textarea_1.append(inputLine+"\n");
            }


        }catch(FileNotFoundException e){
            System.setOut(Main.defaultPrintStream);
            System.out.println(Main.p.getProperty("error.cantfindgpl"));
            System.exit(1);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent e){
        this.dispose();
        System.gc();
    }

}

class RoxSobreDialogLayout implements LayoutManager {

    public RoxSobreDialogLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 595 + insets.left + insets.right;
        dim.height = 355 + insets.top + insets.bottom;

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
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+8,576,24);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+32,576,24);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+136,576,160);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+240,insets.top+312,72,24);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+112,576,24);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+56,576,24);}
    }
}