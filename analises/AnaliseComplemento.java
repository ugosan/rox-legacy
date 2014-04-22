import java.util.*;

public class AnaliseComplemento
    implements RoxAnalise {

  public AnaliseComplemento() {}

  public String execAnalise(Grafo grafo) {
    int Tamanho=grafo.getQtdVertices();
    int[][] matrizAdjacencia = grafo.getMatrizAdjacencias();
    for(int i=0;i<Tamanho;i++)
      for(int j=0;j<Tamanho;j++)
        if(i!=j){
          if(matrizAdjacencia[i][j]==0)
            grafo.addAresta(grafo.getVertice(i),grafo.getVertice(j));
          else
            grafo.delAresta(grafo.getVertice(i),grafo.getVertice(j));
        }


    return ("O Complemento foi feito!");
  }

  public String getNome() {
    return ("Complemento");
  }

}
