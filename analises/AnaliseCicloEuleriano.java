import java.util.*;
import java.awt.Color;

public class AnaliseCicloEuleriano implements RoxAnalise {


    public AnaliseCicloEuleriano(){}



    public String execAnalise(Grafo grafo){
        ArrayList adjacencias;
        Vertice v;

        for(int i=0;i<grafo.getQtdVertices();i++){
            v = grafo.getVertice(i);
            adjacencias = grafo.getAdjacencias(v);

            if((adjacencias.size()%2) != 0){
                return("Nao se pode afirmar...");
            }
        }

    return("O Grafo COM CERTEZA tem um ciclo euleriano");
    }

    public String getNome(){
        return("Ciclo Euleriano");
    }

}