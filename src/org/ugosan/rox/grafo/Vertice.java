/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox.grafo;
import java.io.*;
import java.lang.*;

import javax.imageio.*;

import org.ugosan.rox.*;

import java.awt.image.*;
import java.awt.*;
import java.util.*;

public class Vertice implements
        Serializable{

    int  posX;
    int  posY;
    int centroX;
    int centroY;

    int  nome;
    String rotulo;
    String caminho_imagem;
    int altura_imagem;
    int largura_imagem;

    private Properties propriedades = new Properties();

    private boolean nome_interno = true;
    private boolean selecionado = false;

    private Color cor;

    private int grau;
    private int ordem;


    /** Cria um vértice na posição especificada
     * Obs: passe o vértice criado como parametro em <code> grafo.addVertice(Vertice) </code>
     * @param _x a coordenada X
     * @param _y a coordenada Y
    **/
    public Vertice(int _x, int _y){
        this.posX = _x;
        this.posY = _y;
        this.setImagem(System.getProperty("user.dir")+File.separator+"vertices"+File.separator+"vertice.gif");
    }

    /** Cria um vértice na posição especificada com a imagem especificada no caminho
     * Obs: passe o vértice criado como parametro em <code> grafo.addVertice(Vertice) </code>
     * @param _x a coordenada X
     *        _y a coordenada Y
     *          caminho: o caminho absoluto da imagem
    **/
    public Vertice(int _x, int _y, String caminho){
        this.posX = _x;
        this.posY = _y;
        this.setImagem(caminho);
    }


    /** Retorna o nome do Vertice (relativo a sua ordem de inserção na lista de vértices).
     * <p> IMPORTANTE:  <br>
     * Caso você deseje o rótulo do vértice (que pode ser diferente do nome armazenado internamente) use getRotulo();
     * </p>
    **/
    public int getNome(){
        return(nome);
    }


    /** Atribui um rotulo qualquer ao vértice (diferente do nome, que é relativo a sua ordem de inserção na lista de vértices)
    * @param rotulo uma nova string como rótulo
    **/
    public void setRotulo(String rotulo){
        this.rotulo = rotulo;
    }


    /** Retorna o rótulo do Vertice (diferente do nome, relativo a sua ordem de inserção na lista de vértices).
     * <p> IMPORTANTE:  <br>
     * Caso você deseje o nome do vértice (que pode ser diferente do rótulo) use getNome();
     * O nome é relativo ao valor de entrada na lista de vértices, um vértice que possui nome 5, tem a entrada
     * igual a nome - 1, ou seja, 4. O rótulo que é atribuído no momento da criação é igual ao nome.
     * </p>
    **/
    public String getRotulo(){
    return(rotulo);
    }

    /** Retorna a posicao X relativa ao JPanel RoxPanel
    **/
    public int getPosX(){
    return(posX);
    }
    /** Retorna a posicao Y relativa ao JPanel RoxPanel
    **/
    public int getPosY(){
    return(posY);
    }

    /** Realoca a posicao X,Y relativa ao JPanel RoxPanel
     @param x a coordenada X
     @param y a coordenada Y

    **/
    public void setPosXY(int x, int y){
        this.setPosX(x);
        this.setPosY(y);
    }


    /** Realoca a posicao X relativa ao JPanel RoxPanel
     @param x a coordenada x
    **/
    public void setPosX(int x){

            this.posX = x;
            this.centroX = this.posX + largura_imagem/2;

    }


    /** Realoca a posicao Y relativa ao JPanel RoxPanel
     @param y a coordenada Y
    **/
    public void setPosY(int y){

            this.posY = y;
            this.centroY = this.posY + altura_imagem/2;

    }

    /** Retorna a imagem relativa ao Vértice
    **/
    public BufferedImage getImagem(){
        BufferedImage imagem = null;
        imagem = RoxImageCache.getInstance().getImagem(caminho_imagem);
        this.altura_imagem = imagem.getHeight();
        this.largura_imagem = imagem.getWidth();

    return(imagem);
    }

    /** Lê a imagem especificada no caminho e atribui ao Vértice
     * @param caminho o caminho da imagem
    **/
    public void setImagem(String caminho){
        BufferedImage imagem;

            imagem = RoxImageCache.getInstance().getImagem(caminho);
            this.altura_imagem = imagem.getHeight();
            this.largura_imagem = imagem.getWidth();

            this.centroX = this.posX + largura_imagem/2;
            this.centroY = this.posY + altura_imagem/2;

            this.caminho_imagem = caminho;

    }



    /** verifica se o nome é interno ao vértice (o nome aparece dentro do vértice)
    **/
    public boolean isNome_interno()
    {
        return nome_interno;
    }

    /** Altera a forma como o noem aparece, dentro do vértice ou em um label externo
     * @param valor true=interno ao vértice false=externo ao vértice
    **/
    public void setNome_interno(boolean valor)
    {
        nome_interno = valor;
    }




    public int getCentroX()
    {
        return centroX;
    }


    public void setCentroX(int value)
    {
        centroX = value;
    }

    public int getCentroY()
    {
        return centroY;
    }

    public void setCentroY(int value)
    {
        centroY = value;
    }


    public int getAltura_imagem()
    {
        return altura_imagem;
    }

    public void setAltura_imagem(int value)
    {
        altura_imagem = value;
    }

    public int getLargura_imagem()
    {
        return largura_imagem;
    }

    public void setLargura_imagem(int value)
    {
        largura_imagem = value;
    }

    public boolean isSelecionado()
    {
        return selecionado;
    }

    public void setSelecionado(boolean value)
    {
        selecionado = value;
    }


    /**
    * @return a Cor deste Vértice
    **/
    public Color getCor()
    {
        return(this.cor);
    }

    /**Atribui uma cor ao Vértice
     *  @param c uma Cor
    **/
    public void setCor(Color c)
    {
        this.cor = c;
    }

    /**Atribui uma cor ao Vértice
      *@param R quantidade de vermelho (entre 0 e 255)
    *@param G quantidade de verde (entre 0 e 255)
    *@param B quantidade de azul (entre 0 e 255)
    **/
    public void setCor(int R, int G, int B){
        this.cor = new Color(R,G,B);
    }

    public java.lang.String getCaminho_imagem()
    {
        return caminho_imagem;
    }

    /*
    public int getGrau()
    {
        //return Rox.getInstance().getGrafo().getAdjacencias(this).size();
        return grau;
    }

    public void setGrau(int grau){
        this.grau = grau;
    }

    public int getOrdem()
    {
        return ordem;
    }

    public void setOrdem(int value)
    {
        ordem = value;
    }
     */


    /**Retorna as propriedades do Vertice
    **/
    public Properties getPropriedades(){

        return this.propriedades;
    }

    /** Retorna uma propriedade atraves de uma chave.
     * <p>
     *  As propriedades são inseridas como: <br>
     *      <code>
     *      Vertice.putPropriedade(String chave, String valor); <br>
     *      ex:
     *      Vertice v; <br>
     *      v.putPropriedade("grau","2"); <br>
     *      </code>
     *
     * </p>
     *  <p>
     *  As propriedades são buscadas como: <br>
     *      <code>
     *      Vertice.getPropriedade(String chave); <br>
     *      ex:
     *      Vertice v; <br>
     *      String p = v.getPropriedade("grau"); <br>
     *      int grau = Integer.getInteger(p).intValue();
     *      </code>
     *
     * </p>
     * @param chave: a chave da propriedade
    **/
    public String getPropriedade(String chave){

        return propriedades.getProperty(chave);
    }


    /** Insere uma propriedade atraves de uma chave.
     * <p>
     *  As propriedades são inseridas como: <br>
     *      <code>
     *      Vertice.putPropriedade(String chave, String valor); <br>
     *      ex:<br>
     *      Vertice v; <br>
     *      v.putPropriedade("grau","2"); <br>
     *      </code>
     *
     * </p>
     *  <p>
     *  As propriedades são buscadas como: <br>
     *      <code>
     *      Vertice.getPropriedade(String chave); <br>
     *      ex:<br>
     *      Vertice v; <br>
     *      String p = v.getPropriedade("grau"); <br>
     *      int grau = Integer.getInteger(p).intValue();
     *      </code>
     *
     * </p>
     * @param chave: a chave da propriedade
     * @param valor: a propriedade em si
    **/
    public void putPropriedade(String chave, String valor){
        propriedades.setProperty(chave,valor);

    }

}