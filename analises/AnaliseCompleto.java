import java.util.*;

public class AnaliseCompleto implements RoxAnalise {

  public AnaliseCompleto() {
  }


  public String execAnalise(Grafo grafo) throws Exception{
    ArrayList adjacencias= new ArrayList();
    
	int grau;
    
	for(int i=0;i<grafo.getQtdVertices();i++)
		adjacencias=grafo.getAdjacencias(grafo.getVertice(i));
    
	return ("Grafo completo K"+grafo.getQtdVertices());
  }


  public String getNome() {
    return("Grafo Kn");
  }

}