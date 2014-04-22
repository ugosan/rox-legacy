import java.util.*;

public class AnaliseSubgrafo implements RoxAnalise {

  ArrayList vColoridos;
  ArrayList aColoridas;

  public AnaliseSubgrafo() {
  }

  public boolean clique(){
    for(int i=0;i<aColoridas.size();i++){
      ArrayList Cadeia=(ArrayList)aColoridas.get(i);
      if(Cadeia.size()!=(vColoridos.size()-1))
        return false;
    }
    return true;
  }

  public boolean induzido(Grafo grafo){
  int cont;
    for(int i=0;i<vColoridos.size();i++){
      int nome=((Integer)vColoridos.get(i)).intValue();
      ArrayList adja=grafo.getAdjacencias(grafo.getVerticeByNome(nome));
      cont=0;
      for(int j=0;j<adja.size();j++)
        if((grafo.getAcendedor()).estaAceso((Vertice)adja.get(j)))
          cont++;
      if(cont!=((ArrayList)aColoridas.get(i)).size())
        return false;
    }
    return true;
  }

  public boolean independente(){
    for(int i=0;i<aColoridas.size();i++){
      if((((ArrayList)aColoridas.get(i)).size())!=0)
        return false;
    }
    return true;
  }

  public String subgrafo(Grafo grafo){
  String result;
    if(clique())
      result="O subgrafo é um Clique!";
    else
      if(induzido(grafo)){
        if(independente())
          result="O subgrafo é Independente de Vértices!";
        else
          result="O subgrafo é um Induzido!";
      }
      else
        result="Subgrafo Qualquer!";
    return result;
  }

  public boolean pertenceColoridos(int n){
    for(int i=0;i<vColoridos.size();i++){
      int v=((Integer)vColoridos.get(i)).intValue();
      if(v==n)
        return true;
    }
    return false;
  }


  public String execAnalise(Grafo grafo){
    vColoridos=new ArrayList();//vertices coloridos
    aColoridas=new ArrayList();//arestas coloridas
    for(int i=0;i<grafo.getQtdVertices();i++)
      if((grafo.getAcendedor()).estaAceso(grafo.getVertice(i)))
        vColoridos.add(new Integer((grafo.getVertice(i)).getNome()));

    if(vColoridos.size()!=0){
      for(int i=0;i<vColoridos.size();i++){
          Vertice colorido=grafo.getVerticeByNome(((Integer)vColoridos.get(i)).intValue());
          ArrayList adjacencias=grafo.getAdjacencias(colorido);
          ArrayList a=new ArrayList();
          for(int j=0;j<adjacencias.size();j++){
            Vertice V=(Vertice)adjacencias.get(j);
            if(pertenceColoridos(V.getNome()))
              if((grafo.getAcendedor()).estaAceso(grafo.getArestaEntreVertices(colorido,V)))
                a.add(new Integer(V.getNome()));
          }
          aColoridas.add(a);
        }
        return subgrafo(grafo);
  }
  else
    return "Escolha um subgrafo!";
  }


  public String getNome() {
    return("Subgrafo");
  }

}