/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox;


import java.util.*;

import org.ugosan.rox.grafo.*;
public class Randomizador {
     public Randomizador(){}


     /**Gera novo gráfico com um maximo de n vertices
      * @return Um novo grafo randomico
    **/



     public Grafo getNovoGrafo(int maximoVertices) {
        Grafo grafo = new Grafo();

        try{
        Main rox =  Main.getInstance();

        long seed = Calendar.getInstance().getTimeInMillis();
        Random rand = new Random(seed);



        int numVertices = this.randomAte(maximoVertices);
        while(numVertices<3){
            numVertices = this.randomAte(maximoVertices);
        }
        //cria vertices
        for(int i=0;i<numVertices;i++){
            grafo.addVertice(rand.nextInt(rox.getWidth()-130),rand.nextInt(rox.getHeight()-160));

        }

        //cria arestas
        for(int i=0;i<numVertices;i++){
            for(int j=0;j<rand.nextInt(numVertices);j++){
                if(i!=j)grafo.addAresta(grafo.getVertice(i),grafo.getVertice(j));

            }
        }
        }catch(Exception e){
            System.out.println("Erro: Randomizador: "+e.getMessage());
        }

     return(grafo);
     }


     private int randomAte(int num){
        String s =  Math.random()*num+"";
        Integer i = new Integer(s.substring(0,s.indexOf(".")));
     return(i.intValue());
     }

}