/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diret�rio-raiz em gpl.txt
*/
package org.ugosan.rox.analises;

import javax.swing.*;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.ugosan.rox.Main;
import org.ugosan.rox.grafo.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
*   Classe de compila��o das an�lises
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
        this.prefixo = Main.conf.getProperty("analysis.prefix");

        pw = new PrintWriter(System.out);
    }

/**
 * <p>
 * Este m�todo cria uma Thread e a inicia para compilar as an�lises
 * </p><p>
 *
 */

    public void start(){
        Thread t = new Thread(this);
        t.start();
    }

/**
 * <p>
 * Este m�todo foi criado para compilar as an�lises selecionadas
 * </p><p>
 * @param caminhos os caminhos relativos as analises.
 */
    public void start(String[] caminhos){
        this.arquivos = caminhos;
        this.start();
    }


    public void run(){

        Main.getInstance().selectTabpane(2);

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
