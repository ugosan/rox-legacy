import java.util.*;

public class AnaliseCiclo implements RoxAnalise {

  public AnaliseCiclo() {}


  public String caminho(Vertice v, Vertice vAnt, Grafo g){
     ArrayList matAdj = g.getAdjacencias(v);

     for (int i=0; i < matAdj.size(); i++){
        if (((Vertice)mAdj.get(i)).getNome() != vAnt.getNome()){

           if (((Vertice)mAdj.get(i)).getNome() != vInicial.getNome()){
              Vertice vProx = (Vertice)mAdj.get(i);
              caminho(vProx,v,g);
           }else{
              //se vertice atual for igual a vInicial concluiu o ciclo
           }

        }
     }

  }

}
//gerar cada caminhos possivel e armazenar em um arraylist quando ele
//chegar de volta ao noh inicial

//eliminar os caminhos que percorrem a mesma aresta mais de uma vez
