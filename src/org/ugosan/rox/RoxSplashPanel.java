/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class RoxSplashPanel extends JPanel{
    String[] quadros = {"img"+File.separator+"q10.gif",
                        "img"+File.separator+"q20.gif",
                        "img"+File.separator+"q30.gif",
                        "img"+File.separator+"q40.gif",
                        "img"+File.separator+"q50.gif",
                        "img"+File.separator+"q60.gif",
                        "img"+File.separator+"q70.gif",
                        "img"+File.separator+"q80.gif",
                        "img"+File.separator+"q90.gif"
                        };
    BufferedImage[] imagens;

    BufferedImage quadroBase;
    int INDICE = 0;


    public RoxSplashPanel(){

        this.setSize(500,300);
        imagens = new BufferedImage[quadros.length];
        try{
            quadroBase = ImageIO.read(new File("img"+File.separator+"Rox_Splash.gif"));
            for(int i=0;i<quadros.length;i++)
                imagens[i] = ImageIO.read(new File(quadros[i]));


        }catch(Exception e){}
         this.setFont(new Font("Courier",Font.BOLD,11));

    }

    public final void paintComponent(Graphics g1){
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D)g1;

        g.setColor(Color.DARK_GRAY);
        g.drawImage(quadroBase,0,0,null);
        g.drawString(Rox.versao.substring(Rox.versao.indexOf(" ")),195,74);

        try{
            if(INDICE>=10)  g.drawImage(imagens[0],0,0,this);
            if(INDICE>=20)  g.drawImage(imagens[1],0,0,this);
            if(INDICE>=30)  g.drawImage(imagens[2],0,0,this);
            if(INDICE>=40)  g.drawImage(imagens[3],0,0,this);
            if(INDICE>=50)  g.drawImage(imagens[4],0,0,this);
            if(INDICE>=60)  g.drawImage(imagens[5],0,0,this);
            if(INDICE>=70)  g.drawImage(imagens[6],0,0,this);
            if(INDICE>=80)  g.drawImage(imagens[7],0,0,this);
            if(INDICE>=90)  g.drawImage(imagens[8],0,0,this);
            if(INDICE==100)  g.drawImage(imagens[9],0,0,this);

        }catch(Exception e){}
    }

    public final void setProgresso(int p){
        INDICE = p;
        this.repaint();

    }

}