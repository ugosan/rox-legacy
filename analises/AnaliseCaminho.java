import java.util.*;

public class AnaliseCaminho implements RoxAnalise {

  public AnaliseCaminho() {}

  public boolean pertence(ArrayList array,int num){
    for(int i=0;i<array.size();i++){
      int N=((Vertice)array.get(i)).getNome();
      if(N==num)
        return true;
    }
    return false
  }

  public ArrayList existeVertice(int n1,int n2,Grafo grafo){
    ArrayList result=new ArrayList();
    for(int i=0;i<grafo.getQtdVertices();i++){
      ArrayList a=grafo.getAdjacencias(grafo.getVertice(i));
      if((pertence(a,n1))&&(pertence(a,n2)))
        result.add(new Integer((grafo.getVertice(i)).getNome()));
    }
    return result;
  }

  public boolean existeVertice(int n1,int n2,Grafo grafo){
    ArrayList result=new ArrayList();
    for(int i=0;i<grafo.getQtdVertices();i++){
      ArrayList a=grafo.getAdjacencias(grafo.getVertice(i));
      if((pertence(a,n1))&&(pertence(a,n2)))
        return true;
    }
    return false;
  }

  public String execAnalise(Grafo grafo){
    Rox rox = Rox.getInstance();
    String V1,V2;
    V1 = (String)rox.getInput("Escolha um vertice, ou cancele para terminar",grafo.getNomesVertices().toArray());
    V2 = (String)rox.getInput("Escolha um vertice, ou cancele para terminar",grafo.getNomesVertices().toArray());
    int v1=Integer.valueOf(V1).intValue();
    int v2=Integer.valueOf(V2).intValue();
    ArrayList caminhos=new ArrayList();
    ArrayList array=new ArrayList();
    if(pertence(grafo.getAdjacencias(grafo.getVertice(v1-1),v2))){
      array.add(new Integer(v1));
      array.add(new Integer(v2));
    }
    caminhos.add(array);
    int num=v1;
    while(cont<11){
      if(existeVertice(num,v2,grafo))

        ArrayList a=existeVertice(num,v2,grafo);
        cont++;
    }
    caminhos.add(array);
  }

  public String getNome() {
    return("Caminho");
  }

}