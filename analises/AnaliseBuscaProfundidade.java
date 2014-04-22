import java.util.ArrayList;
import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;

public class AnaliseBuscaProfundidade implements RoxAnalise {
	
	protected ArrayList lVisitados = new ArrayList();

	public String execAnalise(Grafo pGrafo) throws Exception {
		
		ArrayList lVertices = pGrafo.getVertices();		
		
		for (int i = 0; i < lVertices.size(); i++) {
			
			Vertice v = (Vertice) lVertices.get(i);
			
			/* Se nao encontrar o vertice chama o visitaVertice */
			if(!(this.buscaVertice((Vertice) v))){
				this.visitaVertice(pGrafo, v);
			}
			
		}
		
		String retorno = this.getVertices();
		
		return retorno;
	}
	
	private String getVertices() {
		
		StringBuffer retorno = new StringBuffer();
		
		for (int i = 0; i < this.lVisitados.size(); i++) {
			
			Vertice v = (Vertice) lVisitados.get(i);
			retorno.append(" " + v.getNome());
			
		}
		
		return retorno.toString();
	}
	
	private void visitaVertice(Grafo pGrafo, Vertice pVertice) {
		
		this.lVisitados.add(pVertice);
		
		ArrayList listaAdj = pGrafo.getAdjacencias(pVertice);
		
		for (int i = 0; i < listaAdj.size(); i++) {
			
			Vertice v = (Vertice) listaAdj.get(i);
	
			/* Se nao encontrar o vertice chama o metodo novamente */
			if(!(this.buscaVertice(v))){
				
				this.visitaVertice(pGrafo, v);
				
			}
	
		}
		
	}
	
	public boolean buscaVertice(Vertice pVertice) {
		
		for(int i = 0; i < this.lVisitados.size(); i++) {
			
			Vertice lVertice = (Vertice)lVisitados.get(i);
			
			if(lVertice.equals(pVertice)) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}


	public void buscaProfundidade(){
		
	}
	public String getNome() {

		return "* Busca em Profundidade";

	}

}

