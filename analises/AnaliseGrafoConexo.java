import java.util.ArrayList;
import java.util.LinkedList;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;

public class AnaliseGrafoConexo extends AnaliseBuscaLargura implements RoxAnalise {
	
	public ArrayList componentes = new ArrayList();
	public LinkedList copiaListaVertices;

	public String execAnalise(Grafo pGrafo) throws Exception {
		
		this.iniciaAnalise(pGrafo);
		
		return new Integer(this.componentes.size()).toString();
		
	}
	
	public void iniciaAnalise(Grafo pGrafo) {
		
		copiaListaVertices = new LinkedList(pGrafo.getVertices());
		
		while(!this.copiaListaVertices.isEmpty()) {
		
			Vertice v = (Vertice) copiaListaVertices.getFirst();
	
			this.visitaVertice(pGrafo, v);
	
			this.removeVerticesListaVisitadoListaCopia();
	
		}
		
	}
	
	private void removeVerticesListaVisitadoListaCopia() {
		
		ArrayList lVerticesComponente = new ArrayList();
		
		while (!this.lVisitados.isEmpty()) {
			
			lVerticesComponente.add(lVisitados.get(0));
			
			for (int i = 0; i < this.copiaListaVertices.size(); i++){
				
				Vertice lVertice = (Vertice)copiaListaVertices.get(i);

				if(lVertice.equals(lVisitados.get(0))) {

					this.copiaListaVertices.remove(i);
					i = this.copiaListaVertices.size();	
				
				}
			}
			
			lVisitados.remove(0);						
			
		}
		
		this.componentes.add(lVerticesComponente);
		
	}

	public String getNome() {

		return "* Componentes Conexas";
		
	}

}
