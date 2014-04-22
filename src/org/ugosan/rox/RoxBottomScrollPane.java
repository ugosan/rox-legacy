/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/


package org.ugosan.rox;

import javax.swing.JScrollPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
*
*    Um JScrollPane que desce automaticamente. O metodo .setAutoScrolls nao deu certo quando criei
*    o log (JTextArea) e atribui um JScrollPane padrão. Usei este no lugar.
* @author Anônimo
*/
public class RoxBottomScrollPane extends JScrollPane{
    public RoxBottomScrollPane()	{
        super();
    }

    public RoxBottomScrollPane(Component compo)	{
        super(compo);
    }

    private void scrollPaneToBottom()	{
        SwingUtilities.invokeLater(new Runnable()		{
            public void run()			{                             //repaint will call this method now!
                getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
            }
        });
    }

    public void paintComponent(Graphics g)	{
        super.paintComponent(g);
        scrollPaneToBottom();
    }

}