/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;


import javax.swing.*;

import org.ugosan.rox.grafo.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * Carregador das análises e montadora do menu Análises. O nucleo (classe Rox)
 * acessa um objeto (singleton) dessa classe para obter o menu.
 * Acessa RoxClassLoader para compilar as análises e as guarda num TreeMap.
 * @author Ugo Braga Sangiorgi
 */
public class RoxAnaliseLoader implements ActionListener{

    private static RoxAnaliseLoader instance;

    private JMenu menuAnalises;
    private JMenuItem atualizarAnalises;
    private JMenuItem compilarAnalises;

    private String prefixoAnalise = Rox.conf.getProperty("analysis.prefix");
    private JMenuItem analise;

    protected TreeMap analises;

    public RoxAnaliseLoader(){
        menuAnalises = new JMenu(Rox.p.getProperty("menu.2"));
        analises = new TreeMap();
    }

    public RoxAnaliseLoader(JMenu menuAnalises){
        this.menuAnalises = menuAnalises;
    }

    public static RoxAnaliseLoader getInstance(){
        if (instance==null){
            try{
                instance = new RoxAnaliseLoader();

            }catch(Exception e){
                e.printStackTrace(System.out);
            }
        }

    return(instance);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==atualizarAnalises){
        try{
            this.loadAnalises(this.menuAnalises);
        }catch(Exception ex){
            ex.printStackTrace(System.out);
            }
        }else{
            RoxAnaliseCompilerChooser chooser = new RoxAnaliseCompilerChooser();
            chooser.pack();
            chooser.show();


        }
    }

    public JMenu getMenuAnalises(){
        this.reload();
    return(this.menuAnalises);
    }

    public void reload(){
        try{
            analises = new TreeMap();
            this.loadAnalises(this.menuAnalises);

        }catch(Exception ex){
            ex.printStackTrace(System.out);
        }
    }

    public void loadAnalises(JMenu menuAnalises) throws Exception{
        this.menuAnalises = menuAnalises;


        if(atualizarAnalises ==null){
            this.atualizarAnalises = new JMenuItem(Rox.p.getProperty("menu.2.1"));
            atualizarAnalises.addActionListener(this);
        }

        if(compilarAnalises ==null){
            this.compilarAnalises = new JMenuItem(Rox.p.getProperty("analysis.compile"));
            compilarAnalises.addActionListener(this);
        }

        menuAnalises.removeAll();
        menuAnalises.add(atualizarAnalises);
        menuAnalises.add(compilarAnalises);
        menuAnalises.addSeparator();

        RoxClassLoader classloader = new RoxClassLoader(ClassLoader.getSystemClassLoader());

        String[] arquivos = new File(System.getProperty("user.dir")).list();


        int x=0;
        for (int j = 0; j < arquivos.length; j++){
            if (arquivos[j].endsWith(".class"))
                x++;
        }

        final String[] classes = new String[x];
        x=0;
        for (int j = 0; j < arquivos.length; j++){
            if(arquivos[j].startsWith(this.prefixoAnalise) && arquivos[j].endsWith(".class")){
                classes[x] = arquivos[j];
                x++;
            }
        }
         System.out.print(Rox.p.getProperty("analysis.loading")+"("+classes.length+")...[");

         for(int i=0;i<classes.length;i++){

                classloader.preparaClasse(classes[i]);
                String nomeclasse = classes[i].substring(0,classes[i].indexOf("."));
                Class classe = classloader.findClass(nomeclasse);

                final RoxAnalise novaAnalise = (RoxAnalise)classe.newInstance();

                analises.put(nomeclasse,novaAnalise);

                analise = new JMenuItem(novaAnalise.getNome());


                analise.addActionListener(new ActionListener(){
                    public final void actionPerformed(ActionEvent e){
                        try{
                            Rox.getInstance().getRoxAnaliseExecutor().execAnalise(Rox.getInstance().getGrafo(),novaAnalise);
                        }catch(Exception ex){
                            System.out.println("sux!]");
                            System.out.println("Rox não pode ser instanciado / cannot instantiate Rox");
                            ex.printStackTrace(System.out);
                            System.exit(1);
                        }
                    }
			     }
		        );

                menuAnalises.add(analise);

        }
        System.out.println("ok!]");

    }


    public Collection getNomesArquivos(){
        final String[] arquivos = new File(System.getProperty("user.dir")).list();
        Collection c = new ArrayList();
        int x=0;
        for (int j = 0; j < arquivos.length; j++){
            if (arquivos[j].startsWith(prefixoAnalise)&&(arquivos[j].endsWith("java")))
                c.add(arquivos[j]);
        }

        return(c);

    }


    public RoxAnalise getAnalise(String nome){

        return (RoxAnalise)analises.get(nome);
        /*
        RoxAnalise novaAnalise = null;
        try{
            RoxClassLoader classloader = new RoxClassLoader(ClassLoader.getSystemClassLoader());
            classloader.preparaClasse(nome+".class");
            Class classe = classloader.findClass(nome+".class");
            novaAnalise = (RoxAnalise)classe.newInstance();
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
        return(novaAnalise);
        */
    }

}