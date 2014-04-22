/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;


import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.awt.image.*;
import javax.swing.*;
/**
*  Pelo fato de se poder adicionar qualquer imagem aos vértices, fez-se
* necessário o uso de um cache para elas, economizando acessos ao disco
* e consequente re-leitura da imagem, cada vez que ela fosse desenhada.
* Aqui, o menu das imagens também é montado, sub-menu
* do RoxPopupMenu em RoxFrame.
* @author Ugo Braga Sangiorgi
**/
public class RoxImageCache{

    private static RoxImageCache instance;
    private TreeMap cache;
    private RoxMenu menuImagens;
    private JMenuItem atualizarImagens;
    private JMenuItem adicionarImagens;

    private JFileChooser filechooser;

    private RoxImageCache(){
        cache = new TreeMap();

        /*
        this.atualizarImagens = new JMenuItem("Atualizar Imagens...");
        atualizarImagens.setName("Atualizar Imagens...");
        this.adicionarImagens = new JMenuItem("Adicionar Imagens...");
        adicionarImagens.setName("Adicionar Imagens...");
          */

        filechooser = new JFileChooser();
    }


    public static RoxImageCache getInstance(){
        try{
        if (instance==null){
            instance = new RoxImageCache();
        }
        }catch(Exception e){
            System.out.println("Impossivel instanciar RoxImageCache: "+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    return(instance);
    }


    public String addImagem(String caminho){
        try{
            cache.put(caminho,ImageIO.read(new File(caminho)));
            this.updateMenuImagens();
        }catch(Exception e){
            System.out.println("Não foi possivel armazenar a imagem "+caminho+": "+e.getMessage());
            JOptionPane.showMessageDialog(null,"Não foi possivel armazenar a imagem "+caminho+": "+e.getMessage());
            caminho = System.getProperty("user.dir")+File.separator+"vertices"+File.separator+"vertice.gif";
        }
    return(caminho);
    }

    public void addAll(String[] caminhos, boolean limpar){
        if (limpar) cache.clear();
        try{
            for(int i=0;i<caminhos.length;i++)
                cache.put(caminhos[i],ImageIO.read(new File(caminhos[i])));
            this.updateMenuImagens();
        }catch(Exception e){
            System.out.println("Não foi possivel armazenar a imagem: "+e.getMessage());
            e.printStackTrace();
        }
    }


    public BufferedImage getImagem(String caminho){
        if(!this.cache.containsKey(caminho)){
            this.addImagem(caminho);
            this.updateMenuImagens();
        }
        return((BufferedImage)cache.get(caminho));
    }

    public ArrayList getAll(){
        return(new ArrayList(cache.values()));
    }

    public Object[] getAllKeys(){
        return( (Object[])cache.keySet().toArray());
    }
    public boolean contains(String caminho){
        return(cache.containsKey(caminho));
    }

    public boolean contains(BufferedImage imagem){
        return(cache.containsValue(imagem));
    }

    public RoxMenu getMenuImagens(){
        return(menuImagens);
    }

    public void setMenuImagens(JMenu menu){
        this.menuImagens = (RoxMenu)menu;
    }

    public void updateMenuImagens(){
        this.atualizarImagens = new JMenuItem("Atualizar Imagens...");
        atualizarImagens.setName("Atualizar Imagens...");
        this.adicionarImagens = new JMenuItem("Adicionar Imagens...");
        adicionarImagens.setName("Adicionar Imagens...");

        menuImagens = new RoxMenu("Mudar Imagem");
        Object[] imagens = this.getAllKeys();
        for(int i=0;i<imagens.length;i++){
            JMenuItem item = new JMenuItem((String)imagens[i]);
            item.setName((String)imagens[i]);

            menuImagens.add(item);

        }
        menuImagens.addSeparator();
        menuImagens.add(atualizarImagens);
        menuImagens.add(adicionarImagens);

    }


    public void atualizarImagens(){
        String[] imagens = new File(System.getProperty("user.dir")+File.separator+"vertices"+File.separator).list();
        for(int i=0;i<imagens.length;i++){
            imagens[i] = System.getProperty("user.dir")+File.separator+"vertices"+File.separator +imagens[i];
        }
        instance.addAll(imagens,true);
        instance.updateMenuImagens();

    }


    public int showFileChooser(){
        int retorno = JFileChooser.CANCEL_OPTION;
        try{
            retorno = this.filechooser.showOpenDialog(Rox.getInstance());
        }catch(Exception e){}
    return(retorno);
    }

    public JFileChooser getFileChooser(){
        return(this.filechooser);
    }
}