/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diret�rio-raiz em gpl.txt
*/
package org.ugosan.rox;


import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class RoxUpdater implements Runnable{
    String versaoUsuario;
    JOptionPane optionPane;

    private Thread thread;

    public RoxUpdater(String versao, JOptionPane optionPane){
        this.versaoUsuario = versao;
        thread=null;
        this.optionPane = optionPane;
    }



    public void start(){
        if(thread==null){
            thread= new Thread(this);
        }
        thread.start();

    }


    public void run(){
        try{

        String host ="http://www.mycgiserver.com/~ugosan/rox/versao.txt";



        URL url = new URL(host);
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

	    String inputLine = in.readLine();
	    String versaoAtual;
        in.close();



        versaoAtual = inputLine.substring(inputLine.indexOf("<")+1,inputLine.indexOf(">"));

        if(!versaoAtual.equals(versaoUsuario)){
            optionPane.showMessageDialog(null,"Uma vers�o mais nova ("+versaoAtual+") est� dispon�vel em www.ugorox.kit.net \n � altamente recomendado que voc� atualize sua ferramenta","Troque seu Rox velho por um novo!",1);

        }
        }catch(Exception e){}

    }


}