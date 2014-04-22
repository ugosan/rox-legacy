/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;

import java.util.*;
import java.awt.Color;

import org.ugosan.rox.grafo.*;
/**@deprecated
*    O RoxAcendedor foi depreciado, é muito mais eficaz usar os metodos
*    dos objetos que se quer colorir. como Vertice.setCor() e Aresta.setcor().
**/
public class RoxAcendedor implements java.io.Serializable{



    private ArrayList verticesAcesos;  //0..* Vertice
    private ArrayList arestasAcesas;   //0..* Aresta
    private ArrayList coresVertices;   //0..* Color
    private ArrayList coresArestas;    //0..* Color


    /**
    @deprecated
    */
    public RoxAcendedor(){
        verticesAcesos = new ArrayList();
        arestasAcesas = new ArrayList();
        coresVertices = new ArrayList();
        coresArestas = new ArrayList();
    }

    /**
    @deprecated
    */
    public void acenderVertice(Vertice _v, int R, int G, int B){
        _v.setCor(new Color(R,G,B,130));

        if(verticesAcesos.contains(_v)){
            verticesAcesos.set(verticesAcesos.indexOf(_v),_v);
            coresVertices.set(verticesAcesos.indexOf(_v),new Color(R,G,B,130));
        }else{
            verticesAcesos.add(_v);
            coresVertices.add(new Color(R,G,B,130));
        }



    }

    /**
    @deprecated
    */
    public void acenderAresta(Aresta _a, int R, int G, int B){
        _a.setCor(new Color(R,G,B,100));

        if(arestasAcesas.contains(_a)){
            arestasAcesas.set(arestasAcesas.indexOf(_a),_a);
            coresArestas.set(arestasAcesas.indexOf(_a),new Color(R,G,B,130));
        }else{
            arestasAcesas.add(_a);
            coresArestas.add(new Color(R,G,B,130));
        }

    }

        /**
    @deprecated
    */
    public void acenderVertice(Vertice _v, Color _cor){
         _v.setCor(new Color(_cor.getRed(),_cor.getGreen(),_cor.getBlue(),130));

         if(verticesAcesos.contains(_v)){
            verticesAcesos.set(verticesAcesos.indexOf(_v),_v);
            coresVertices.set(verticesAcesos.indexOf(_v),new Color(_cor.getRed(),_cor.getGreen(),_cor.getBlue(),130));
        }else{
            verticesAcesos.add(_v);
            coresVertices.add(new Color(_cor.getRed(),_cor.getGreen(),_cor.getBlue(),130));
        }


    }

        /**
    @deprecated
    */
    public void acenderAresta(Aresta _a,Color _cor){
        _a.setCor(new Color(_cor.getRed(),_cor.getGreen(),_cor.getBlue(),130));
        if(arestasAcesas.contains(_a)){
            arestasAcesas.set(arestasAcesas.indexOf(_a),_a);
            coresArestas.set(arestasAcesas.indexOf(_a),new Color(_cor.getRed(),_cor.getGreen(),_cor.getBlue(),130));
        }else{
            arestasAcesas.add(_a);
            coresArestas.add(new Color(_cor.getRed(),_cor.getGreen(),_cor.getBlue(),130));
        }



    }


    /**
    @deprecated
    */
    public void delArestaAcesa(Aresta _a){
        _a.setCor(null);
        if(arestasAcesas.contains(_a)){
            coresArestas.remove(arestasAcesas.indexOf(_a));
            arestasAcesas.remove(arestasAcesas.indexOf(_a));

        }
    }
    /**
    @deprecated
    */
    public void delVerticeAceso(Vertice _v){
        _v.setCor(null);
        if(verticesAcesos.contains(_v)){
            coresVertices.remove(verticesAcesos.indexOf(_v));
            verticesAcesos.remove(verticesAcesos.indexOf(_v));

        }
    }

    /**
    @deprecated
    */
    public boolean estaAceso(Vertice _v){
        if(_v.getCor() == null) return false;
        return(verticesAcesos.contains(_v));
    }

    /**
    @deprecated
    */
    public boolean estaAceso(Aresta _a){
        if(_a.getCor() == null) return false;
        return(arestasAcesas.contains(_a));
    }



    /**
    @deprecated
    */
    public Aresta getAresta(int i){
    return((Aresta)arestasAcesas.get(i));
    }

    /**
    @deprecated
    */
    public Vertice getVertice(int i){
    return((Vertice)verticesAcesos.get(i));
    }

    /**
    @deprecated
    */
    public Color getCorVertice(Vertice _v){
    if(verticesAcesos.contains(_v)){
        return((Color)coresVertices.get(verticesAcesos.indexOf(_v)));
    }
    return(null);
    }

    /**
    @deprecated
    */
    public Color getCorAresta(Aresta _a){
    if(arestasAcesas.contains(_a)){
        return((Color)coresArestas.get(arestasAcesas.indexOf(_a)));
    }
    return(null);

    }

    /**
    @deprecated
    */
    public int getQtdVerticesAcesos(){
    return(verticesAcesos.size());
    }
    /**
    @deprecated
    */
    public int getQtdArestasAcesas(){
    return(arestasAcesas.size());
    }

}