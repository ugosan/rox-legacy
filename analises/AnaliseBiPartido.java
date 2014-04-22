import java.util.*;

public class AnaliseBiPartido implements RoxAnalise {
    ArrayList partA,partB,nInseridos;

  public AnaliseBiPartido(){
  }

  public boolean pertenceA(int num){//true=pertence
	  for(int i=0;i<partA.size();i++)	{
	    int NUM=((Integer)partA.get(i)).intValue();
	    if(NUM==num)
		    return true;
	  }
	  return false;
  }


  public boolean pertenceB(int num){
	  for(int i=0;i<partB.size();i++)	{
	   int NUM=((Integer)partB.get(i)).intValue();
	   if(NUM==num)
		    return true;
	  }
	  return false;
  }

  public boolean pertence_nInseridos(int num){
	  for(int i=0;i<nInseridos.size();i++)	{
	   int NUM=((Integer)nInseridos.get(i)).intValue()+1;
	   if(NUM==num)
		    return true;
	  }
	  return false;
  }

  public void insere(ArrayList adj,int V){
  int contA=0,contB=0;
	  for(int i=0;i<adj.size();i++){
      int N=((Vertice)adj.get(i)).getNome();
	    if(pertenceA(N))
		    contA++;
	    else
		    if(pertenceB(N))
		      contB++;
	  }
	  if(((contA==0)&&(partA.size()==0))||((contA==0)&&(contB!=0)))
		  partA.add(new Integer(V));
	  else
      if((contA==0)&&(contB==0)&&!(pertence_nInseridos(V)))
		    nInseridos.add(new Integer(V-1));//insere indice
      else
		    if(contB==0)
			    partB.add(new Integer(V));
  }

  public String execAnalise(Grafo grafo){
    partA=new ArrayList();
	  partB=new ArrayList();
	  nInseridos=new ArrayList();
    for(int i=0;i<grafo.getQtdVertices();i++){
      ArrayList adjacencias = grafo.getAdjacencias(grafo.getVertice(i));
      insere(adjacencias,(grafo.getVertice(i)).getNome());
    }
    for(int i=0;i<nInseridos.size();i++){
      ArrayList adjacencias = grafo.getAdjacencias(grafo.getVertice(((Integer)nInseridos.get(i)).intValue()));
      insere(adjacencias,(grafo.getVertice(((Integer)nInseridos.get(i)).intValue())).getNome());
    } 

	  String result;
	  if((partA.size()+partB.size())==grafo.getQtdVertices()){
		  result="Grafo Bipartido";
		  result+="\n\n";
		  result+="Particao 1: ";
		  for(int i=0;i<partA.size();i++)result+="-"+(((Integer)partA.get(i)).intValue())+"-";
		  result+="\n";
		  result+="Particao 2: ";
		  for(int i=0;i<partB.size();i++)result+="-"+(((Integer)partB.get(i)).intValue())+"-";
	  }
	  else
		  result="Grafo Não Bipartido";
	  return(result);
  }

  public String getNome(){
    return("Grafo Bipartido");
  }
}

