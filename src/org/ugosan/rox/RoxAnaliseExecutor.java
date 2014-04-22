/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;

import javax.swing.*;

import org.ugosan.rox.grafo.*;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Executor das análises feitas, gerencia o tempo que a análise leva pra
 * executar. Cria-se uma Thread dentro de execAnalise(Grafo, RoxAnalise) e
 * dentro dela é onde a análise roda.
 * @author Ugo Braga Sangiorgi
 **/
public class RoxAnaliseExecutor implements Runnable{

    private RoxAnalise ultimaAnalise;
    private long tempoUltimaAnalise;

    private PrintWriter printwriter;
    private JMenuItem atualizarAnalises;
    private ImageIcon iconeResultado;
    private Thread execucao;

    private Grafo grafo;
    public RoxAnaliseExecutor(){
        iconeResultado = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img"+File.separator+"icone.gif"));
   }


    public void execAnalise(Grafo grafo, RoxAnalise analise) throws Exception{
        this.grafo = grafo;
        this.ultimaAnalise = analise;


        System.gc();

        execucao = new Thread(this);
        execucao.start();

    }
    /*
    public void stop(){
        execucao.interrupt();
        try{
        execucao.sleep(System.currentTimeMillis());
        }catch(Exception e){
            System.out.println("eee");
        }
        ultimaAnalise = null;
        execucao = null;
        System.gc();

        Rox.getInstance().setWaitState(false);
    }
      */
    public void run(){
        Rox rox = Rox.getInstance();

        try{
            rox.setWaitState(true);
            long tempoInicial = System.currentTimeMillis();

            String resultado = ultimaAnalise.execAnalise(grafo);

            tempoUltimaAnalise =  System.currentTimeMillis() - tempoInicial;
            rox.repaint();

            String tempo = this.tempo(tempoUltimaAnalise);
            rox.msg(tempo);


            rox.setWaitState(false);
            if(resultado != null)
                JOptionPane.showMessageDialog(rox,resultado+"\n\n"+rox.getProperties().getProperty("analysis.length")+tempo,ultimaAnalise.getNome(),1,iconeResultado);
            else
                resultado = rox.getProperties().getProperty("analysis.noresult");

            rox.loga(ultimaAnalise.getNome()+" ("+ultimaAnalise.getClass()+")",resultado + "\n"+rox.getProperties().getProperty("analysis.length")+" " + tempo);

        }
        catch(Exception ex){

            System.out.println("\n["+rox.getProperties().getProperty("error.analysis")+"]");

            ex.printStackTrace(System.out);
            System.out.println("\n["+rox.getProperties().getProperty("error.analysis")+"]");


            JOptionPane.showMessageDialog(rox,rox.getProperties().getProperty("error.name")+": "+ex.toString(),rox.getProperties().getProperty("error.analysis.title"),JOptionPane.ERROR_MESSAGE);
            rox.setWaitState(false);
        }

    }


    private String tempo(long tempo){
        String retorno ="";
        long milis = 0;
        long segundos = 0;
        long minutos = 0;
        long horas = 0;

        milis = tempo % 1000;
        segundos = (tempo / 1000)%60;
        minutos = ((tempo / 1000)/60)%60;
        horas = (((tempo / 1000)/60)/60)%60;

        retorno = horas+":"+minutos+":"+segundos+":"+milis;

    return(retorno);
    }

    public void execUltimaAnalise(){
        if(ultimaAnalise != null){

            try{

                RoxAnaliseCompiler compilador = new RoxAnaliseCompiler();
                String nomeAnalise = ultimaAnalise.getClass().toString().substring(ultimaAnalise.getClass().toString().indexOf(" ")+1);

                String[] arquivos = new String[]{nomeAnalise+".java"};
                int status = compilador.compile(arquivos);

                RoxAnaliseLoader loader = RoxAnaliseLoader.getInstance();
                loader.reload();

                if(status == 0){
                    RoxAnalise analise = loader.getAnalise(nomeAnalise);
                    ultimaAnalise = analise;
                    this.execAnalise(Rox.getInstance().getGrafo(),ultimaAnalise);
                }else Rox.getInstance().selectTabpane(2);

            }catch(Exception e){
                System.out.println(Rox.p.getProperty("analysis.cantexecute"));
                e.printStackTrace(System.out);
            }
        }
    }


}