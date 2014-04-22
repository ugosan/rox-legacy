public class AnaliseRegular implements RoxAnalise {

  public AnaliseRegular() {
  }


  public String execAnalise(Grafo grafo) throws Exception{
    int grau=(grafo.getAdjacencias(grafo.getVertice(0))).size();
    
	for(int i=0;i<grafo.getQtdVertices();i++){
      if ((grafo.getAdjacencias(grafo.getVertice(i))).size()!=grau) {
        return("Este grafo n�o � regular");
      }
    }
    return ("Grafo regular "+grau);
  }


  public String getNome() {
    return("Grafo Regular");
  }

}