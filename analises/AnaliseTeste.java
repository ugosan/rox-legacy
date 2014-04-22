import java.util.*;
import java.awt.Color;

public class AnaliseTeste implements RoxAnalise {

    public AnaliseTeste(){}

    public String execAnalise(Grafo grafo)throws Exception{
        ArrayList auxVertices = new ArrayList();
        ArrayList auxArestas = new ArrayList();

        for(int i=0;i<grafo.getQtdVertices();i++){
            if(grafo.getAdjacencias(grafo.getVertice(i)).size()%2!=0){
                grafo.acender(grafo.getVertice(i),Color.ORANGE);
            }else{
                Vertice v = grafo.getVertice(i);
                grafo.acender(v,Color.MAGENTA);
                auxVertices.add(v);
            }
        }

        for(int i=0;i<auxVertices.size();i++){
            for(int j=0;j<auxVertices.size();j++){
                Aresta a = grafo.getArestaEntreVertices((Vertice)auxVertices.get(i),(Vertice)auxVertices.get(j));

                if(a!=null){
                    auxArestas.add(a);
                }

            }

        }

        String grafoCompleto = new AnaliseGrafoCompleto().execAnalise(grafo);

        if(grafoCompleto.equals("O Grafo NAO eh completo!")){
            for(int i=0;i<grafo.getQtdArestas();i++){
                grafo.acender(grafo.getAresta(i),Color.BLUE);
            }

            for(int i=0;i<auxArestas.size();i++){
                grafo.acender((Aresta)auxArestas.get(i),170,10,10);
            }

        }else{
            for(int i=0;i<grafo.getQtdVertices();i++){
                grafo.acender(grafo.getVertice(i),Color.ORANGE);
            }
            for(int i=0;i<grafo.getQtdArestas();i++){
                grafo.acender(grafo.getAresta(i),Color.ORANGE);
            }


        }




    return(null);
    }

    public String getNome(){
        return("Analise de Teste de acendedor");
    }

}