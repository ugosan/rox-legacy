import java.util.*;

public class AnaliseOrdemEstatistica implements RoxAnalise {

  public AnaliseOrdemEstatistica() {
  }


  public String execAnalise(Grafo grafo) throws Exception{
    int ordem=grafo.getQtdVertices();
    int vet[]=new int[ordem];

    for(int i=0;i<grafo.getQtdVertices();i++)
      vet[i]=(grafo.getAdjacencias(grafo.getVertice(i))).size();

    for(int i=0;i<ordem;i++)
      for(int j=i+1;j<ordem;j++)
        if(vet[i]>vet[j]){
          int aux=vet[i];
          vet[i]=vet[j];
          vet[j]=aux;
        }
    String estatistica="";
    int cont=0;
    int num=vet[0];
      for(int i=0;i<ordem;i++){
        if(vet[i]==num)
          cont++;
        else{
            num=vet[i];
            estatistica+=cont+" vertices - grau: "+vet[i-1]+"\n";
            cont=1;
        }
        if((i==ordem-1))
          estatistica+=cont+" vertices - grau: "+vet[i]+"\n";
        }
    return ("Ordem: "+grafo.getQtdVertices()+"\n\n"+estatistica);
  }


  public String getNome() {
    return("Ordem e Estatistica");
  }

}