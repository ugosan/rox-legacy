import java.util.ArrayList;
import java.util.LinkedList;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;

public class AnaliseBuscaLargura implements RoxAnalise {
	
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
	
	protected void visitaVertice(Grafo pGrafo, Vertice pVertice) {
		
		LinkedList lFila = new LinkedList();
		
		this.lVisitados.add(pVertice);
		
		lFila.addLast(pVertice);
		
		while (!lFila.isEmpty()) {
			
			Vertice lVertice = (Vertice) lFila.removeFirst();
			
			ArrayList listaAdj = pGrafo.getAdjacencias(lVertice);
			
			for (int i = 0; i < listaAdj.size(); i++) {
				
				Vertice v = (Vertice) listaAdj.get(i);
		
				/* Se nao encontrar o vertice chama o metodo novamente */
				if(!(this.buscaVertice((Vertice) v))){
					
					this.lVisitados.add(v);
					lFila.addLast(v);
					
				}
		
			}
		}
		
	}
	
	protected boolean buscaVertice(Vertice pVertice) {
		
		for(int i = 0; i < this.lVisitados.size(); i++) {
			
			Vertice lVertice = (Vertice)lVisitados.get(i);
			
			if(lVertice.equals(pVertice)) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}


	public void buscaLargura(){
		
	}
	public String getNome() {

		return "* Busca em Largura";

	}

}
