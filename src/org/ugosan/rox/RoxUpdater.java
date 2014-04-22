/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
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
            optionPane.showMessageDialog(null,"Uma versão mais nova ("+versaoAtual+") está disponível em www.ugorox.kit.net \n É altamente recomendado que você atualize sua ferramenta","Troque seu Rox velho por um novo!",1);

        }
        }catch(Exception e){}

    }


}