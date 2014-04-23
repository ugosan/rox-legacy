/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox.grafo;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.awt.*;

import org.ugosan.rox.*;
public class Grafo implements
        Serializable{

    public boolean ehDigrafo;

    protected ArrayList vertices;
    protected ArrayList arestas;

    protected int[][] matrizAdjacencias;
    protected int[][] matrizIncidencias;

    /** Cria um Grafo vazio
    **/
    public Grafo(){
        vertices = new ArrayList();
        arestas = new ArrayList();
        this.ehDigrafo=false;
    }

    /** Cria um Grafo baseado em uma matriz de adjacencias, os vértices
     * ficam em posições aleatórias. Use este construtor para manipular o grafo
     * independentemente, através do clone da matriz de adjacencias antiga.
     * <p>Obs: a matriz de incidencias tambem é gerada, porém pode haver
     * uma alteração na ordem em que elas aparecem na matriz, porém, ainda são
     * arestas e ainda ligam relativamente os mesmos vertices
      *@param matrizAdjacencias a matriz em questão
     * **/
    public Grafo(int[][] matrizAdjacencias){
        vertices = new ArrayList();
        arestas = new ArrayList();
        this.ehDigrafo = false;
        try{
            Main rox = Main.getInstance();
            Random rand = new Random(System.currentTimeMillis());
            //cria vertices
            for (int i=0;i<matrizAdjacencias.length;i++) {
                this.addVertice(rand.nextInt(rox.getWidth()-130),rand.nextInt(rox.getHeight()-160));
            }

            for (int i=0;i<matrizAdjacencias.length;i++) {
                for (int j=0;j<matrizAdjacencias.length;j++) {
                    if(matrizAdjacencias[i][j]==1){
                        Vertice v1 = this.getVertice(i);
                        Vertice v2 = this.getVertice(j);
                        if(this.getArestaEntreVertices(v1,v2)==null)
                            this.addAresta(v1,v2);
                    }
                }
            }

        }catch(Exception e){
            //isso nunca deverá acontecer
            e.printStackTrace();
        }
    }

    /** Adiciona um Vertice

     * ex: instancie um objeto {@link Vertice} e configure sua posicao
     * usando Vertice.posX = valor e Vertice.posY = valor e então
     * efetue a alteração com este metodo em questão
     *@param _v Um novo vertice
      * @return o Vertice recem-criado
    **/
    public Vertice addVertice(Vertice _v){
        _v.nome = this.getNovoNome();
        _v.setRotulo(_v.nome+"");
        vertices.add(_v);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();
    return(_v);
    }




    /** Adiciona um Vertice na posicao especificada

     * efetue a alteração com este metodo em questão
     *@param _x Uma posicao X
    *@param _y Uma posicao Y
       *  @return o Vertice recem-criado
     * **/
    public synchronized Vertice addVertice(int _x,int _y){
        Vertice v = new Vertice(_x,_y);
        v.nome = this.getNovoNome();
        v.setRotulo(v.nome+"");
        vertices.add(v);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();
    return(v);
    }




    /**Apaga O Vertice com o nome passado
     * @param nomevertice o nome do vertice
    **/
    public synchronized  void delVerticeByNome(int nomevertice){
        Vertice v = this.getVertice(nomevertice-1);

        this.delArestas(v);
        vertices.remove(v.nome-1);
        this.updateNomesVertices();
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();

    }


    /**Adiciona uma aresta entre os vertices _v1 e _v2
        * @return a Aresta recem-criada
     *  @param _v1 um Vertice <br>
     *  @param _v2 um Vertice
     *

    **/
    public synchronized  Aresta addAresta(Vertice _v1, Vertice _v2){
        if(this.getArestaEntreVertices(_v1,_v2)==null){
            Aresta aresta = new Aresta(_v1,_v2);
            arestas.add(aresta);
            this.updateMatrizAdjacencias();
            this.updateMatrizIncidencias();
            return(aresta);
        }
    return(null);
    }



    /**Apaga todas as arestas que _v1 possui
     * @param _v1 um Vertice
     *
    **/
    public synchronized void delArestas(Vertice _v1){
         Aresta a;
         for(int i=0;i<this.getQtdArestas();i++){
            a=this.getAresta(i);
            if(a.contemVertice(_v1)){

                arestas.remove(i);
                i--;

            }
         }


    }


    /**Apaga a aresta entre os vertices _v1 e _v2
     * @param _v1 um Vertice <br>
     * @param _v2 um Vertice
    **/
    public synchronized  void delAresta(Vertice _v1, Vertice _v2){
        for(int i=0;i<this.getQtdArestas();i++){
            if(this.getAresta(i).contemVertices(_v1,_v2)){

                arestas.remove(i);
                this.updateMatrizAdjacencias();
                this.updateMatrizIncidencias();

                return;
            }
        }
    }



    public synchronized void delAresta(int i){
        arestas.remove(i);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();

    }



    /**@return O Vertice com o nome passado
     * @param nomevertice o nome do vertice
    **/
    public synchronized Vertice getVerticeByNome(int nomevertice){
    return((Vertice)vertices.get(nomevertice-1));
    }

    /**@return O Vertice com o nome passado, o nome deve estar no formato <br>
     * "Vertice n" onde n é o nome do vertice
     * @param nomevertice o nome do vertice
    **/
    public synchronized  Vertice getVerticeByNome(String nomevertice){
        String nome = nomevertice.substring(nomevertice.indexOf(" ")+1);
    return((Vertice)vertices.get(Integer.valueOf(nome).intValue()-1));
    }

    /**@return A quantidade de arestas
    **/
    public synchronized int getQtdArestas(){
    return(arestas.size());
    }
    /**@return A quantidade de vertices
    **/
    public synchronized int getQtdVertices(){
    return(vertices.size());
    }


     /** Checa se existe algum vertice numa area de ate 20 pixels
      *  dos valores _x e _y passados
      * @return nome (int) do vertice, se existe algum, caso
      * contrario, retorna -1
      *
      *@param _x posicao x
     *@param _y posicao y
    **/
    public synchronized int existeVertice(int _x,int _y){
        int x1;
        int x2;
        int y1;
        int y2;

        Vertice v;
        for(int i=0;i<vertices.size();i++){
            v = this.getVertice(i);
            x1 = v.getPosX();
            x2 = v.getPosX() + v.getLargura_imagem();
            y1 = v.getPosY();
            y2 = v.getPosY() + v.getAltura_imagem();

            if(((_x>x1)&&(_x<x2))&&((_y>y1)&&(_y<y2))){
                return(v.nome);
            }
        }
    return(-1);
    }

    /** Para ser usado em alterções de posicao, caso
     * se deseje mover algum vertice sem intervenção do usuário
     *
     *  <br>
     * ex: use getVerticeByNome(int i), altere a posicao do vertice
     * usando Vertice.posX = valor e Vertice.posY = valor e então
     * efetue a alteração com este metodo em questão
     *
     * @param _v O Vertice
    **/
    public synchronized void alteraVertice(Vertice _v){
        if((_v!=null)&&(vertices.contains(_v))){
            vertices.set(_v.nome-1,_v);
        }
    }

    /** Retorna a Aresta entre os vertices especificados
     *  <br>
     * @param _v1 Vertice
     * @param _v2 Vertice
     *
     *
    **/
    public synchronized Aresta getArestaEntreVertices(Vertice _v1, Vertice _v2){
        Aresta aresta;
        for(int i=0;i<arestas.size();i++){
            aresta = this.getAresta(i);
            if(aresta.contemVertices(_v1,_v2)) return(aresta);
        }
    return(null);
    }


    /** Retorna um ArrayList contendo todos os
     * Vertices adjacentes a _v
     *  <br>
     * @param _v Vertice em questao
     *
     *
    **/
    public synchronized  ArrayList getAdjacencias(Vertice _v){
        Aresta aresta;
        ArrayList adjacencias = new ArrayList();
        for(int i=0;i<arestas.size();i++){
            aresta = this.getAresta(i);
            if(aresta.v1==_v){
                adjacencias.add(aresta.v2);
            }
            if(aresta.v2==_v){
                adjacencias.add(aresta.v1);
            }
        }

    return(adjacencias);
    }





    /**@return A Aresta baseado na sua representação
     * interna<br>
     * <b> Atenção: este método foi feito para ser usado
     * dentro de laços de repeticao em conjunto com
     * {@link #getQtdArestas() getQtdArestas}, para obter a aresta entre
     * dois vertices, passe os dois vertices como parametro
     * em {@link #getArestaEntreVertices(Vertice,Vertice) getArestaentreVertices}
     * </b>
     * @param i indice do vertice (nome-1)
    **/
    public synchronized  Aresta getAresta(int i){
    return((Aresta)arestas.get(i));
    }







    /**@return O Vertice baseado na sua representação
     * interna (nome-1).<br>
     * <b> Atenção: este método foi feito para ser usado
     * dentro de laços de repeticao em conjunto com
     * {@link #getQtdVertices() getQtdVertices}, para obter o vertice
     * pelo nome que eh mostrado, use {@link #getVerticeByNome(String) getVerticeByNome}
     * </b>
     * @param i indice do vertice (nome-1)
    **/
    public synchronized  Vertice getVertice(int i){
    return((Vertice)vertices.get(i));
    }



    /** @return Matriz de inteiros bi-dimensional
     *  representando a matriz de adjacencias do grafo
    **/
    public synchronized  int[][] getMatrizAdjacencias(){
    return(matrizAdjacencias);
    }




    /** @return Matriz de inteiros bi-dimensional
     *  representando a matriz de incidencias do grafo
    **/
    public synchronized  int[][] getMatrizIncidencias(){
    return(matrizIncidencias);
    }


    /** @return Matriz(independente) de inteiros bi-dimensional
     *  representando a matriz de incidencias do grafo
    **/
    public synchronized  int[][] getCloneMatrizAdjacencias(){
        int[][] retorno = new int[vertices.size()][vertices.size()];
        for (int i=0;i<vertices.size();i++) {
            for (int j=0;j<vertices.size();j++) {
                if(matrizAdjacencias[i][j]==1)
                    retorno[i][j] = 1;
                else if(matrizAdjacencias[i][j]==0)
                    retorno[i][j] = 0;
            }
        }
    return(retorno);
    }

    /** @return Matriz(independente) de inteiros bi-dimensional
     *  representando a matriz de incidencias do grafo
    **/
    public synchronized  int[][] getCloneMatrizIncidencias(){
        int[][] retorno = new int[vertices.size()][arestas.size()];
        for (int i=0;i<vertices.size();i++) {
            for (int j=0;j<arestas.size();j++) {
                if(matrizIncidencias[i][j]==1)
                    retorno[i][j] = 1;
                else if(matrizIncidencias[i][j]==0)
                    retorno[i][j] = 0;
            }
        }
    return(retorno);
    }






    /**Remove todas as cores dos vertices e arestas
    **/
    public void resetAcesos(){
        for(int i=0;i<vertices.size();i++)
              ((Vertice)vertices.get(i)).setCor(null);

        for(int i=0;i<arestas.size();i++)
              ((Aresta)arestas.get(i)).setCor(null);



    }

    /**
     *@deprecated
     * @return RoxAcendedor inerente ao grafo
    **/
    public RoxAcendedor getAcendedor(){
    return(null);
    }

    /**@return um ArrayList com todos os Vertices do grafo
    **/
    public synchronized  ArrayList getVertices(){
    return(vertices);
    }

    /**@return um ArrayList com todas as Arestas do grafo
    **/
    public synchronized  ArrayList getArestas(){
    return(arestas);
    }


    /**ex: Para ser usado em JOptionPane ou na mostragem dos vertices em um Combobox
     * @return um ArrayList com todos os nomes de todos os <br>
     * Vertices no formato "Vertice n" onde n é o nome do vertice
     *
    **/
    public synchronized ArrayList getNomesVertices(){
        ArrayList retorno = new ArrayList();
        for(int i=0;i<vertices.size();i++)
            retorno.add("Vertice "+this.getVertice(i).nome);
    return(retorno);
    }

     /**Atribui uma nova matriz para a matriz de incidencias
    **/
    public synchronized void setMatrizIncidencias(int[][] matrizIncidencias){
        this.matrizIncidencias = matrizIncidencias;
    }

     /**Atribui uma nova matriz para a matriz de incidencias
    **/
    public synchronized void setMatrizAdjacencias(int[][] matrizAdjacencias){
        this.matrizAdjacencias = matrizAdjacencias;
    }

    //metodos privados


    private synchronized void updateMatrizAdjacencias(){
        Vertice vertice;
        Vertice verticeAux;
        ArrayList v_adjacencias;
        matrizAdjacencias = new int[vertices.size()][vertices.size()];


        for(int i=0;i<vertices.size();i++){
            vertice = this.getVertice(i);
            v_adjacencias = this.getAdjacencias(vertice);
            for(int j=0;j<v_adjacencias.size();j++){
                verticeAux = (Vertice)v_adjacencias.get(j);
                matrizAdjacencias[i][verticeAux.nome-1] = 1;
            }
        }
    }
    private synchronized void updateMatrizIncidencias(){
        Aresta aresta;
        matrizIncidencias = new int[vertices.size()][arestas.size()];

        for(int j=0;j<arestas.size();j++){
            aresta = this.getAresta(j);
            matrizIncidencias[aresta.v1.nome-1][j] = 1;
            matrizIncidencias[aresta.v2.nome-1][j] = 1;
        }
    }



    private int getNovoNome(){
        return(vertices.size()+1);
    }

    private synchronized void updateNomesVertices(){
        Vertice v;
        for(int i=0;i<this.getQtdVertices();i++){
            v = this.getVertice(i);
            v.nome=(i+1);
            vertices.set(i,v);
        }
    }






     /**
    *@deprecated
    *@see  org.ugosan.rox.grafo.Vertice#setCor(int, int,int)
    **/
    public synchronized void acender(Vertice _v, int R, int G, int B){
       if(_v!=null)  _v.setCor(new Color(R,G,B));
    }



    /**
     *@deprecated
     *@see  org.ugosan.rox.grafo.Aresta#setCor(int, int,int)
    **/
    public synchronized void acender(Aresta _a, int R, int G, int B){
        if(_a!=null) _a.setCor(new Color(R,G,B));
    }



    /**
     *@deprecated
    *@see org.ugosan.rox.grafo.Vertice#setCor(java.awt.Color)
    **/
    public synchronized void acender(Vertice _v, java.awt.Color _cor){
        if(_v!=null) _v.setCor(_cor);
    }




    /**
     *@deprecated
     *@see org.ugosan.rox.grafo.Aresta#setCor(java.awt.Color)
    **/
    public synchronized void acender(Aresta _a, java.awt.Color _cor){
        if(_a!=null) _a.setCor(_cor);
    }




}