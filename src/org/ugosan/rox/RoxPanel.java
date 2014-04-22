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

import org.ugosan.rox.grafo.*;

import java.util.*;

public class RoxPanel extends JPanel{
    Properties p = Rox.p;
    BufferedImage imagem;
    Rox rox;
    RoxFrame roxframe;

    private boolean waitState;
    private RenderingHints hints = new RenderingHints(null);

    int[][] matrizAdjacencias;
    int[][] matrizIncidencias;

    Aresta a;
    long milis;
    long tdq;
    long tdq_tmp;

    int i;

    public void setRenderingHints(RenderingHints h){
       hints = h;
       repaint();
    }

    public void ativarOtimizacao(boolean ativar){
        RenderingHints rh = new RenderingHints(null);
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        rh.put(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        if(ativar){
            rh.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_SPEED);
            rh.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
            rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        }else{
            rh.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
            rh.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        }
        this.setRenderingHints(rh);
    }


    public RoxPanel(Rox _rox, RoxFrame _roxframe) throws Exception{
        this.rox = _rox;
        this.roxframe = _roxframe;

        this.setFont(new Font("Courier",Font.PLAIN,11));
        setDoubleBuffered(false);



    }

    public void setWaitState(boolean wait){
        this.waitState = wait;
        this.repaint();
    }

    public final void paintComponent(Graphics g){
        rox = Rox.getInstance();
        milis = System.currentTimeMillis();

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHints(hints);




        if(roxframe.mostrarMatrizes){
            if((roxframe.ativarOtimizacao)){
                if(tdq<60) this.printMatrizes(g2);
                if(!roxframe.movendoVertice) this.printMatrizes(g2);
            }else this.printMatrizes(g2);


        }
        //arestas
        if(roxframe.criandoAresta){
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(roxframe.vtemp1.getCentroX(),roxframe.vtemp1.getCentroY(),roxframe.mousex,roxframe.mousey);
        }else if(roxframe.colorindoAresta){
            g2.setColor(Rox.cor_selecionada);
            g2.drawLine(roxframe.vtemp1.getCentroX(),roxframe.vtemp1.getCentroY(),roxframe.mousex,roxframe.mousey);
        }else if(roxframe.removendoAresta){

            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(180,0,0,150));
            g2.drawLine(roxframe.vtemp1.getCentroX(),roxframe.vtemp1.getCentroY(),roxframe.mousex,roxframe.mousey);
        }else if(roxframe.selecionandoVertices){
            g2.setColor(Color.GRAY);
            g2.drawRect(0,0,roxframe.mousex,roxframe.mousey);
        }

        if((roxframe.movendoVertice)&&(roxframe.ativarOtimizacao)){

            if(tdq<90){
            for(int i=0;i<rox.grafo.getQtdArestas();i++){
                a = rox.grafo.getAresta(i);
            if(a.getCor()!=null){
                g2.setStroke(new BasicStroke(2));
                g2.setColor(a.getCor());

            }else{
                g2.setStroke(new BasicStroke());
                g2.setColor(Color.BLACK);
            }

            g2.drawLine(a.getVertice1().getCentroX(),a.getVertice1().getCentroY(),a.getVertice2().getCentroX(),a.getVertice2().getCentroY());


            }
            }
        }else{

            for(int i=0;i<rox.getGrafo().getQtdArestas();i++){
                a = rox.getGrafo().getAresta(i);

            if(a.getCor()!=null){
                g2.setStroke(new BasicStroke(2));
                g2.setColor(a.getCor());
            }else{
                g2.setStroke(new BasicStroke());
                g2.setColor(Color.BLACK);
            }
            g2.drawLine(a.getVertice1().getCentroX(),a.getVertice1().getCentroY(),a.getVertice2().getCentroX(),a.getVertice2().getCentroY());


            if(rox.getGrafo().ehDigrafo)
                this.drawArrow(g2,a.getVertice1().getCentroX(),a.getVertice1().getCentroY(),a.getVertice2().getCentroX(),a.getVertice2().getCentroY());

        }
        }

        //vertices
        Vertice v;
        for(int i=0;i<rox.grafo.getQtdVertices();i++){
            v = rox.grafo.getVertice(i);
            //nota: é necessario desenhar a imagem relativa a suas posicoes x e y, e nao
            //ao seu centro, pois o que o usuario vê é a imagem relativa ao centro, ele precisa
            //interagir com o centro da imagem, caso contrario, o centro perde o sentido...
            g2.drawImage(v.getImagem(),v.getPosX(),v.getPosY(),this);

            this.desenhaRotuloVertice(g2,v);

            if(v.getCor()!=null){
                g2.setColor(new Color(v.getCor().getRed(),v.getCor().getGreen(),v.getCor().getBlue(),130));
                g2.fillOval(v.getPosX(),v.getPosY(),v.getImagem().getWidth(),v.getImagem().getHeight());
            }


        }



       tdq_tmp = System.currentTimeMillis()-milis;
       if(tdq_tmp>0){
        tdq = tdq_tmp;
       }

       if(Rox.getInstance().roxframe.mostrarTDQ){
        g2.setColor(new Color(240,240,240,160));

        g2.fillRect(Rox.getInstance().getWidth()-217,Rox.getInstance().getHeight()-173,125,20);
        g2.setColor(Color.BLACK);
        g2.drawString(tdq+" "+p.getProperty("roxpanel.miliseconds"),Rox.getInstance().getWidth()-210,Rox.getInstance().getHeight()-160);


       }



       if(waitState){
            g2.setColor(new Color(240,240,240,130));
            g2.fillRect(0,0,this.getWidth(),this.getHeight());

            g2.setColor(new Color(240,240,240,30));
            g2.fillRect(1,1,this.getWidth()-30,25);
            g2.setColor(Color.BLACK);
            g2.drawString("Aguarde, Executando análise...",5,10);
        }
    }



    private void desenhaRotuloVertice(Graphics2D g2, Vertice v){
            String nome = v.getRotulo();

            if(nome.length()>2) v.setNome_interno(false);
            else v.setNome_interno(true);

            if(!v.getCaminho_imagem().endsWith(File.separator+"vertice.gif")) v.setNome_interno(false);

            if(v.isNome_interno()){
                g2.setColor(Color.WHITE);
                if(v.getRotulo().length()<2)
                    g2.drawString(nome,v.getPosX()+5,v.getPosY()+12);
                else g2.drawString(nome,v.getPosX()+2,v.getPosY()+12);

            }else{
                g2.setColor(new Color(1,1,1,170));
                g2.fillRect(v.getPosX()-16,v.getPosY()-15,nome.length()*9,11);

                g2.setColor(Color.WHITE);
                g2.drawString(nome,v.getPosX()-15,v.getPosY()-6);

           }
    }

    private final void printMatrizes(Graphics2D g2){

        if(rox.grafo.getQtdVertices()>0){
            g2.setColor(Color.DARK_GRAY);
            int x=10,y=20;
            int qtdVertices = rox.grafo.getQtdVertices();
            g2.drawString(p.getProperty("roxpanel.adjacence"),10,10);
            matrizAdjacencias = new int[qtdVertices][qtdVertices];
            matrizAdjacencias = rox.grafo.getMatrizAdjacencias();

            for(int i=0;i<qtdVertices;i++){
                for(int j=0;j<qtdVertices;j++){
                    g2.drawString(matrizAdjacencias[i][j]+"",x,y);
                    x = x+10;
                }
                y = y+10;
                x=10;
            }


        y=y+20;

        int qtdArestas = rox.grafo.getQtdArestas();
        matrizIncidencias = new int[qtdVertices][qtdArestas];
        matrizIncidencias = rox.grafo.getMatrizIncidencias();
        g2.drawString(p.getProperty("roxpanel.incidence"),x,y-10);
        for(int i=0;i<qtdVertices;i++){
            for(int j=0;j<qtdArestas;j++){
                g2.drawString(matrizIncidencias[i][j]+"",x,y);
                //if(rox.grafo.ehDigrafo)
                //    x = x+20;
                //else
                x = x+10;

            }


        y = y+10;
        x=10;
        }

        }

    }



    //função para desenhar uma seta, original por Tim Yates
    private final void drawArrow( Graphics2D g, int x, int y, int xx, int yy )
 {
   float arrowWidth = 7.0f ;
   float theta = 0.423f ;
   int[] xPoints = new int[ 3 ] ;
   int[] yPoints = new int[ 3 ] ;
   float[] vecLine = new float[ 2 ] ;
   float[] vecLeft = new float[ 2 ] ;
   float fLength;
   float th;
   float ta;
   float baseX, baseY ;

   int pontomedioX = (xx+x) / 2;
   int pontomedioY = (yy+y) / 2;

   xPoints[ 0 ] = pontomedioX ;
   yPoints[ 0 ] = pontomedioY ;


   // build the line vector
   vecLine[ 0 ] = (float)xPoints[ 0 ] - x ;
   vecLine[ 1 ] = (float)yPoints[ 0 ] - y ;

   // build the arrow base vector - normal to the line
   vecLeft[ 0 ] = -vecLine[ 1 ] ;
   vecLeft[ 1 ] = vecLine[ 0 ] ;

   // setup length parameters
   fLength = (float)Math.sqrt( vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1] ) ;
   th = arrowWidth / ( 2.0f * fLength ) ;
   ta = arrowWidth / ( 2.0f * ( (float)Math.tan( theta ) / 2.0f ) * fLength ) ;

   // find the base of the arrow
   baseX = ( (float)xPoints[ 0 ] - ta * vecLine[0]);
   baseY = ( (float)yPoints[ 0 ] - ta * vecLine[1]);

   // build the points on the sides of the arrow
   xPoints[ 1 ] = (int)( baseX + th * vecLeft[0] );
   yPoints[ 1 ] = (int)( baseY + th * vecLeft[1] );
   xPoints[ 2 ] = (int)( baseX - th * vecLeft[0] );
   yPoints[ 2 ] = (int)( baseY - th * vecLeft[1] );


   g.fillPolygon( xPoints, yPoints, 3 ) ;
 }






}