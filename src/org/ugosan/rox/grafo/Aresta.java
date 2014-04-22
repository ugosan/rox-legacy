/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox.grafo;
import java.io.*;
import java.lang.*;
import java.awt.*;
import java.util.*;

public class Aresta implements
        Serializable{

    Vertice  v1;
    Vertice  v2;
    Color cor;
    private Properties propriedades = new Properties();

    public Aresta(Vertice _v1, Vertice _v2){
        this.v1 = _v1;
        this.v2 = _v2;
    }

    /** Analisa se a aresta está entre dois vertices.
     * (Nao importa a ordem que eles sao passados)
     * @param _v1 um Vertice
     * @param _v2 um Vertice
    **/
    public boolean contemVertices(Vertice _v1,Vertice _v2){
        if((v1==_v1)&&(v2==_v2)) return(true);
        if((v1==_v2)&&(v2==_v1)) return(true);
    return(false);
    }
    /** Analisa se a aresta contem o Vertice em questão
     * @param _v um Vertice
     *
    **/
    public boolean contemVertice(Vertice _v){
        if(v1==_v) return(true);
        if(v2==_v) return(true);

    return(false);
    }

    /**
    * @return o Vertice 1 da aresta
    **/
    public Vertice getVertice1(){
    return v1;
    }

    /**
    * @return o Vertice 2 da aresta
    **/
    public Vertice getVertice2(){
    return v2;
    }

    /**Atribui um outro vértice à aresta
     *  @param v1 um Vertice
    **/
    public void setVertice1(Vertice v1){
        this.v1 = v1;
    }

    /**Atribui um outro vértice à aresta
     *  @param v2 um Vertice
    **/
    public void setVertice2(Vertice v2){
        this.v2 = v2;
    }

    /**
    * @return a Cor desta Aresta
    **/
    public Color getCor()
    {
        return(this.cor);
    }

    /**Atribui uma cor à aresta
     *  @param c uma Cor
    **/
    public void setCor(Color c)
    {
        this.cor = c;
    }

    /**Atribui uma cor à aresta
     *@param R quantidade de vermelho (entre 0 e 255)
    *@param G quantidade de verde (entre 0 e 255)
    *@param B quantidade de azul (entre 0 e 255)
    **/
    public void setCor(int R, int G, int B){
        this.cor = new Color(R,G,B);
    }


    /**Retorna as propriedades da Aresta
    **/
    public Properties getPropriedades(){
        return this.propriedades;
    }

    /** Retorna uma propriedade atraves de uma chave.
     * <p>
     *  As propriedades são inseridas como: <br>
     *      <code>
     *      Aresta.putPropriedade(String chave, String valor); <br>
     *      ex:
     *      Aresta a; <br>
     *      a.putPropriedade("peso","2"); <br>
     *      </code>
     *
     * </p>
     *  <p>
     *  As propriedades são buscadas como: <br>
     *      <code>
     *      Aresta.getPropriedade(String chave); <br>
     *      ex:
     *      Aresta a; <br>
     *      String p = a.getPropriedade("peso"); <br>
     *      int peso = Integer.getInteger(p).intValue();
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
     *      Aresta.putPropriedade(String chave, String valor); <br>
     *      ex:
     *      Aresta a; <br>
     *      a.putPropriedade("peso","2"); <br>
     *      </code>
     *
     * </p>
     *  <p>
     *  As propriedades são buscadas como: <br>
     *      <code>
     *      Aresta.getPropriedade(String chave); <br>
     *      ex:
     *      Aresta a; <br>
     *      String p = a.getPropriedade("peso"); <br>
     *      int peso = Integer.getInteger(p).intValue();
     *      </code>
     *
     * </p>
     * @param chave: a chave da propriedade
    **/
    public void putPropriedade(String chave, String valor){
        propriedades.setProperty(chave,valor);

    }

}