/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;

import java.awt.*;
import javax.swing.*;

class RoxSplash extends JWindow
{

    JTextField jt = new JTextField();
    RoxSplashPanel painel = new RoxSplashPanel();

    public RoxSplash(Frame f, String initString)
    {
        super(f);

        jt.setFont(new Font("Verdana",Font.PLAIN,9));
        jt.setText(initString);

        getContentPane().add(painel,BorderLayout.CENTER);
        getContentPane().add(jt,BorderLayout.SOUTH);


        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500,310);

        setLocation(screenSize.width/2 - 250, screenSize.height/2 - 150);

        Runnable waitRunner = new Runnable(){
          public void run(){
            try{
              Thread.sleep(10);
            }
            catch(Exception e){}
          }
        };
        setVisible(true);
        Thread splashThread = new Thread(waitRunner, "RoxSplash");
        splashThread.start();
    }
    public void setProgresso(int progresso){
      painel.setProgresso(progresso);

    }
    public void setTexto(String texto){
      jt.setText(texto);
    }

}