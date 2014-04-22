/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox.grafo;

import java.util.*;

import org.ugosan.rox.*;
public class Digrafo extends Grafo implements java.io.Serializable{

    public boolean ehDigrafo;

    /** Cria um Digrafo vazio
    **/
    public Digrafo(){
        super();
        super.ehDigrafo=true;
    }

    /** Cria um Digrafo baseado em uma matriz de adjacencias, os vértices
     * ficam em posições aleatórias. Use este construtor para manipular o grafo
     * independentemente, através do clone da matriz de adjacencias antiga.
     * <p>Obs: a matriz de incidencias tambem é gerada, porém pode haver
     * uma alteração na ordem em que elas aparecem na matriz, porém, ainda são
     * arestas e ainda ligam relativamente os mesmos vertices
      *@param matrizAdjacencias a matriz em questão
     * **/
    public Digrafo(int[][] matrizAdjacencias){
        super(matrizAdjacencias);
        super.ehDigrafo = true;
    }


    public Vertice addVertice(Vertice _v){
        Vertice v = super.addVertice(_v);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();
        return(v);
    }

    public synchronized Vertice addVertice(int _x,int _y){
        Vertice v = super.addVertice(_x,_y);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();
        return(v);
    }

    public synchronized  void delVerticeByNome(int nomevertice){
        super.delVerticeByNome(nomevertice);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();
    }

    public synchronized  Aresta addAresta(Vertice _v1, Vertice _v2){
        Aresta a = super.addAresta(_v1,_v2);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();
        return(a);
    }

    public synchronized  void delAresta(Vertice _v1, Vertice _v2){
        super.delAresta(_v1,_v2);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();

    }

    public synchronized void delAresta(int i){
        super.delAresta(i);
        this.updateMatrizAdjacencias();
        this.updateMatrizIncidencias();

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
        for(int i=0;i<super.arestas.size();i++){
            aresta = super.getAresta(i);
            if(aresta.v1==_v){
                adjacencias.add(aresta.v2);
            }

        }

    return(adjacencias);
    }




    private void updateMatrizAdjacencias(){
        Vertice vertice;
        Vertice verticeAux;
        ArrayList v_adjacencias;
        super.matrizAdjacencias = new int[super.vertices.size()][super.vertices.size()];

        for(int i=0;i<super.vertices.size();i++){
            vertice = super.getVertice(i);
            v_adjacencias = this.getAdjacencias(vertice);
            for(int j=0;j<v_adjacencias.size();j++){
                verticeAux = (Vertice)v_adjacencias.get(j);
                super.matrizAdjacencias[i][verticeAux.nome-1] = 1;
            }
        }

    }
    private void updateMatrizIncidencias(){
        Aresta aresta;
        super.matrizIncidencias = new int[super.vertices.size()][super.arestas.size()];

        for(int j=0;j<super.arestas.size();j++){
            aresta = super.getAresta(j);
            super.matrizIncidencias[aresta.v1.nome-1][j] = 1;
            super.matrizIncidencias[aresta.v2.nome-1][j] = -1;
        }

    }




}