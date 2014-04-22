import java.util.*;

public class AnaliseCortePonte
    implements RoxAnalise {
  ArrayList pontes,corte;

  public AnaliseCortePonte() {}

  public boolean pertencePontes(int V1,int V2){
    for(int i=0;i<pontes.size();i++){
      ArrayList aux=(ArrayList)pontes.get(i);
      int v1=((Integer)aux.get(0)).intValue();
      int v2=((Integer)aux.get(1)).intValue();
      if((V1==v2)&&(V2==v1))
        return true;
    }
    return false;
  }

  public String getPontes(Grafo grafo) {
      AnaliseConexo AC = new AnaliseConexo();
      pontes = new ArrayList();
      ArrayList adjacencias;
      Vertice v;Aresta a;
      int Tamanho=grafo.getQtdVertices();//tamanho do grafo original

      try {
        String conexidade = AC.execAnalise(grafo);
        if (conexidade.equals("Grafo Conexo"))
          for (int i = 0; i < Tamanho; i++) {
            adjacencias=grafo.getAdjacencias(grafo.getVertice(i));
            for(int j=0;j<adjacencias.size();j++){
              grafo.delAresta(grafo.getVertice(i), (Vertice) adjacencias.get(j));
              if (! ( (AC.execAnalise(grafo)).equals("Grafo Conexo"))){
                if(pertencePontes((grafo.getVertice(i)).getNome(),((Vertice)adjacencias.get(j)).getNome())==false){
                  ArrayList A = new ArrayList();
                  A.add(new Integer( (grafo.getVertice(i)).getNome()));
                  A.add(new Integer( ( (Vertice) adjacencias.get(j)).getNome()));
                  pontes.add(A);
                }
              }
              a = grafo.addAresta(grafo.getVertice(i),(Vertice)adjacencias.get(j));
            }
          }
        else {
          int nComponentes = (AC.getComponentes()).size(); //nº de componentes
          for (int i = 0; i < grafo.getQtdVertices(); i++) {
            adjacencias=grafo.getAdjacencias(grafo.getVertice(i));
            for(int j=0;j<adjacencias.size();j++){
              ( (Vertice) adjacencias.get(j)).getNome();
              grafo.delAresta(grafo.getVertice(i), (Vertice) adjacencias.get(j));
              if (! ( (AC.execAnalise(grafo)).equals("Grafo Conexo")))
                if ( ( (AC.getComponentes()).size()) > nComponentes){
                  if(pertencePontes((grafo.getVertice(i)).getNome(),((Vertice)adjacencias.get(j)).getNome())==false){
                    ArrayList A = new ArrayList();
                    A.add(new Integer( (grafo.getVertice(i)).getNome()));
                    A.add(new Integer( ( (Vertice) adjacencias.get(j)).getNome()));
                    pontes.add(A);
                  }
                }
                a = grafo.addAresta(grafo.getVertice(i),(Vertice)adjacencias.get(j));
            }
          }
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      String result = "Pontes entre vertices:\n";
      for (int i = 0; i < pontes.size(); i++) {
        ArrayList aux=(ArrayList)pontes.get(i);
        result += "-" + ( (Integer) aux.get(0)).intValue() + " e ";
        result += ( (Integer) aux.get(1)).intValue() + "-\n";
      }

      return result;
    }


  public String getVerticesCorte(Grafo grafo) {
    AnaliseConexo AC = new AnaliseConexo();
    corte = new ArrayList();
    ArrayList adjacencias;
    Vertice v;Aresta a;
    int X,Y,Tamanho=grafo.getQtdVertices();//tamanho do grafo original
    int[] vet;

    try {
      String conexidade = AC.execAnalise(grafo);
      if (conexidade.equals("Grafo Conexo"))
        for (int i = 0; i < Tamanho; i++) {
          X=(grafo.getVertice(0)).getPosX();Y=(grafo.getVertice(0)).getPosY();
          adjacencias=grafo.getAdjacencias(grafo.getVertice(0));
          vet=new int[adjacencias.size()];
          for(int j=0;j<adjacencias.size();j++)
            vet[j]=((Vertice)adjacencias.get(j)).getNome();
          grafo.delVerticeByNome(1);
          if (!((AC.execAnalise(grafo)).equals("Grafo Conexo")))
            corte.add(new Integer(i+1));

          v=grafo.addVertice(X,Y);

          for(int j=0;j<adjacencias.size();j++)
            a = grafo.addAresta(grafo.getVertice(Tamanho-1),grafo.getVertice(vet[j] - 2));
        }
      else {
        int nComponentes = (AC.getComponentes()).size(); //nº de componentes
        for (int i = 0; i < Tamanho; i++) {
          X=(grafo.getVertice(0)).getPosX();Y=(grafo.getVertice(0)).getPosY();
          adjacencias=grafo.getAdjacencias(grafo.getVertice(0));
          vet=new int[adjacencias.size()];
          for(int j=0;j<adjacencias.size();j++)
            vet[j]=((Vertice)adjacencias.get(j)).getNome();
          grafo.delVerticeByNome(1);
          if (! ( (AC.execAnalise(grafo)).equals("Grafo Conexo")))
            if ( ( (AC.getComponentes()).size()) > nComponentes)
              corte.add(new Integer(i+1));

          v=grafo.addVertice(X,Y);

          for(int j=0;j<adjacencias.size();j++)
            a = grafo.addAresta(grafo.getVertice(Tamanho-1),grafo.getVertice(vet[j] - 2));
        }
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    String result = "Vertices de Corte:\n";
    for (int i = 0; i < corte.size(); i++) {
      result += "-" + ( (Integer) corte.get(i)).intValue() + "-";
    }
    return result;
  }


  public String execAnalise(Grafo grafo) {
    return getVerticesCorte(grafo)+"\n"+getPontes(grafo);
  }

  public String getNome() {
    return ("Vertice de Corte/Ponte");
  }

  public ArrayList getCorte(){
    return corte;
  }

  public ArrayList getPontes(){
    return pontes;
  }
}
