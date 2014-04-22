/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;

import javax.swing.*;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.ugosan.rox.grafo.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
*   Classe de compilação das análises
* @author Ugo Braga Sangiorgi
*/
public class RoxAnaliseCompiler implements Runnable{
    private PrintWriter pw;
    private String dir;
    private String prefixo;
    private String[] arquivos;
   // private com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
    javax.tools.JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

    public RoxAnaliseCompiler(String dir, String prefixo){
        this.dir = dir;
        this.prefixo = prefixo;
        pw = new PrintWriter(System.out);
    }

    public RoxAnaliseCompiler(){

        this.dir = System.getProperty("user.dir");
        this.prefixo = Rox.conf.getProperty("analysis.prefix");

        pw = new PrintWriter(System.out);
    }

/**
 * <p>
 * Este método cria uma Thread e a inicia para compilar as análises
 * </p><p>
 *
 */

    public void start(){
        Thread t = new Thread(this);
        t.start();
    }

/**
 * <p>
 * Este método foi criado para compilar as análises selecionadas
 * </p><p>
 * @param caminhos os caminhos relativos as analises.
 */
    public void start(String[] caminhos){
        this.arquivos = caminhos;
        this.start();
    }


    public void run(){

        Rox.getInstance().selectTabpane(2);

        if(arquivos == null)
            arquivos = new File(dir).list();

        this.compile(arquivos);
        arquivos = null;
    }

	public int compile(String[] arquivos2) {
		// TODO Auto-generated method stub
		
		return -1;
	}
}
