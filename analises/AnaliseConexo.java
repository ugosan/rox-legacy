import ugo.rox.*;
import ugo.rox.grafo.*;
import java.util.*;

public class AnaliseConexo implements RoxAnalise {
  ArrayList componentes;
  ArrayList cadeias;
  public AnaliseConexo() {
  }

  public boolean verificaArray(ArrayList A,ArrayList B){
    if(A.size()!=B.size())  //true=ja existe cadeia igual
      return false;
    else
      for(int i=0;i<A.size();i++)
        if((((Integer)A.get(0)).intValue())==(((Integer)B.get(i)).intValue()))
          return true;
    return false;
  }

  public boolean verificaCadeia(ArrayList CADEIA,int num){
    for(int i=0;i<CADEIA.size();i++)
      if((((Integer)CADEIA.get(i)).intValue())==num)
        return false;
    return true;
  }

  public ArrayList getCadeia(Grafo grafo,int v){
  ArrayList cadeia=new ArrayList();
    cadeia.add(new Integer(v));
    for(int i=0;i<cadeia.size();i++){
      ArrayList adjacencias=grafo.getAdjacencias(grafo.getVertice(((Integer)cadeia.get(i)).intValue()-1));
      for(int j=0;j<adjacencias.size();j++){
        Integer num=new Integer(((Vertice)adjacencias.get(j)).getNome());
        if(verificaCadeia(cadeia,num.intValue()))
          cadeia.add(num);
      }
    }
    return cadeia;
  }

  public String execAnalise(Grafo grafo) throws Exception{
    cadeias=new ArrayList();

    for(int i=0;i<grafo.getQtdVertices();i++)
     cadeias.add(getCadeia(grafo,(grafo.getVertice(i)).getNome()));


    if(((ArrayList)cadeias.get(0)).size()==grafo.getQtdVertices())
      return "Grafo Conexo";
    else{
      int cont=0;
      String comp_conexas="Grafo Nao Conexo\n\n";
      componentes=new ArrayList();
      componentes.add((ArrayList)cadeias.get(0));
      for(int i=0;i<cadeias.size();i++){
        ArrayList CAD=(ArrayList)cadeias.get(i);
        for(int j=0;j<componentes.size();j++)
          if(verificaArray(CAD,((ArrayList)componentes.get(j)))==false)
            cont++;
        if(cont==componentes.size())
          componentes.add(CAD);
        cont=0;
      }
      for(int i=0;i<componentes.size();i++){
        ArrayList array=(ArrayList)componentes.get(i);
        comp_conexas+="Componente "+(i+1)+":  ";
        for(int j=0;j<array.size();j++)
          comp_conexas+="- "+((Integer)array.get(j)).intValue()+" -";
        comp_conexas+="\n";
      }
      return comp_conexas;
    }
  }

  public String getNome() {
    return("Grafo Conexo");
  }

  public ArrayList getComponentes(){
    return componentes;
  }

  public ArrayList getCadeias(){
    return cadeias;
  }

}