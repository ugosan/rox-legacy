import ugo.rox.*;
import ugo.rox.grafo.*;
import java.util.*;


public class AnaliseEstatisticas implements RoxAnalise {


    public AnaliseEstatisticas(){}



    public String execAnalise(Grafo grafo) throws Exception{
        grafo.resetAcesos();
        ArrayList adjacencias = new ArrayList();
        int[] grauVertices = new int[99];
        int soma = 0;
        boolean ok = false;
        String dados;
        dados = "- " + grafo.getQtdVertices() + " vertice(s);" ;

        int grau;
        for(int ini = 0; ini < 99; ini++){
             grauVertices[ini] = 0;
        }
        for(int i=0;i<grafo.getQtdVertices();i++){
            adjacencias = grafo.getAdjacencias(grafo.getVertice(i));
            grau = adjacencias.size();
            grauVertices[grau]++;
        }

        for (int m = 0; m < 99; m++){
            if ( grauVertices[m] > 0 ){
                dados += "\n- " + grauVertices[m] + " vertices de grau " + (m);
            }
        }

        for (int s = 0; s < 99; s++){
            soma += s*grauVertices[s];
        }

        dados += "\n- " + soma/2 + " aresta(s)";
        dados += "\n- Soma dos Graus: " + soma;
        return(dados);
    }

    public String getNome(){
        return("Estatísticas...");
    }
}