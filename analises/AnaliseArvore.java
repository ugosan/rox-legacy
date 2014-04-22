import java.util.*;

public class AnaliseArvore implements RoxAnalise {

  public AnaliseArvore(){
  }

  public String execAnalise(Grafo grafo){
    AnaliseConexo AC=new AnaliseConexo();
    String result="";
    try{
      String conexidade=AC.execAnalise(grafo);
      AnaliseCortePonte ACP=new AnaliseCortePonte();
      String aux=ACP.execAnalise(grafo);

      if((ACP.getPontes()).size()!=grafo.getQtdArestas())
        result= "O Grafo não é nem árvore nem floresta!";
      else{
        if (conexidade.equals("Grafo Conexo"))
          result= "O Grafo é uma árvore!";
        else {
          result= "O Grafo é uma floresta!\n";
          ArrayList componentes = AC.getComponentes();
          for(int i=0;i<componentes.size();i++){
            ArrayList array = (ArrayList) componentes.get(i);
            result+= "Componente " + (i + 1) + ":  ";
            for (int j = 0; j < array.size(); j++)
              result += "- " + ( (Integer) array.get(j)).intValue() +
                  " -";
            result += "\n";
          }
        }
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return result;
  }

  public String getNome(){
    return("Grafo Arvore/Floresta");
  }
}

