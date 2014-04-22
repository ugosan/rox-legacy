import java.util.*;

public class AnaliseScie implements RoxAnalise {
    ArrayList partA,partB,nInseridos;

  public AnaliseScie(){
  }

  public ArrayList naoAdjacencia (Grafo g,Vertice v){
    ArrayList array = g.getAdjacencias(v);
    ArrayList nAdj = new ArrayList();
	  for(int i=0;i<g.getQtdVertices();i++){
      int a=0;
      for (int j=0;j<array.size();j++){
        if (g.getVertice(i).getNome()==((Vertice)array.get(j)).getNome())
          a++;
      }
      if(a==0)
		    nAdj.add(g.getVertice(i));
	  }
	  return nAdj;
  }

  public String execAnalise(Grafo grafo) throws Exception{
    ArrayList arrayScie = new ArrayList();
    for(int i=0;i<grafo.getQtdVertices();i++){
      ArrayList nAdjacencias = naoAdjacencia(grafo,grafo.getVertice(i));

      for (int i=0;i<nAdjacencias.size();i++){
        ArrayList adj = grafo.getAdjacencias((Vertice)nAdjacencia.get(i));
        ArrayList auxArray= new ArrayList();

        for (int k=0;k<nAdjacencias.size();k++){
          for (int j=0;j<adj.size();j++){
            int a=0;
            if (((Vertice)adj.get(j)).getNome()==((Vertice)nAdjacencia.get(k)).getNome()){
              a++;
            }
            if (a==0)
              auxArray.add(nAdjacencia.get(k));
          }
          arrayScie.add(auxArray);//array de array
        }
      }
    }
    String result;
    for (int k=0;k<arrayScie.size();k++){
      result+= ("{ ");
      ArrayList aux=(ArrayList) arrayScie.get(k);
      for (int i=0;i<aux.size();i++){
        result+=(((Vertice) aux.get(i)).getNome()+ " , ");
      }
      result += " } \n";
    }
    return(result);
  }

  public String getNome(){
    return("SCIE");
  }
}

