import java.util.*;

public class AnaliseGrafo implements RoxAnalise {
    ArrayList partA,partB,nInseridos;

  public AnaliseGrafo(){
  }

  public ArrayList naoAdjacencia (Grafo g, ArrayList array){
    ArrayList nAdj = new ArrayList();
	  for(int i=0;i<g.getQtdVertices();i++){
      int a=0;
      for (int j=0;j<array.size();j++){
        Vertice v= array.get(j);
        if (g.getVertice(i).getNome()== v.getNome())
          a++;
      }
      if(a==0)
		    nAdj.add(g.getVertice(i));
	  }
	  return nAdj;
  }

  public String execAnalise(Grafo grafo){
    ArrayList arrayScie = new ArrayList();
   try{
    for(int i=0;i<grafo.getQtdVertices();i++){
      ArrayList nAdjacencias = naoAdjacencia(grafo,grafo.getAdjacencias(grafo.getVertice(i)));

      for (int i=0;i<nAdjacencias.size();i++){
        ArrayList adj = grafo.getAdjacencias((Vertice)nAdjacencias.get(i));
        ArrayList auxArray= new ArrayList();

        for (int k=0;k<nAdjacencias.size();k++){
          for (int j=0;j<adj.size();j++){
            int a=0;
            if (((Vertice)adj.get(j)).getNome()==((Vertice)nAdjacencias.get(k)).getNome()){
              a++;
            }
            if (a==0)
              auxArray.add(nAdjacencias.get(k));
          }
          arrayScie.add(auxArray);//array de array
        }
      }
    }
   }catch (Exception ex) {
      ex.printStackTrace();
   }
    String result;
    for (int k=0;k<arrayScie.size();k++){
      result+= ("{ ");
      ArrayList aux=(ArrayList) arrayScie.get(k);
      for (int i=0;i<aux.size();i++){
        result+=(((Vertice)aux.get(i)).getNome()+ " , ");
      }
      result += " } \n";
    }
    return(result);
  }

  public String getNome(){
    return("SCIE");
  }
}

