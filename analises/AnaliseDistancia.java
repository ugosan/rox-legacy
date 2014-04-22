import java.util.ArrayList;
import java.util.LinkedList;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;

/*
 * Nesta classe utilizamos o atributo Grau para simbolizar a distancia entre o 
 * vertice passado e o vertice em questao.
 */
public class AnaliseDistancia
	extends AnaliseBuscaLargura
	implements RoxAnalise {

	protected Grafo grafo;

	public String execAnalise(Grafo pGrafo) throws Exception {

		this.grafo = pGrafo;
		
		for(int j = 0; j < this.grafo.getVertices().size(); j++){
			
			Vertice v = grafo.getVertice(j);
			v.setGrau(0);
			this.visitaVertice(v);

			System.out.println();	
			System.out.println("VÉRTICE: " + v.getNome());
			for (int i = 0; i < this.lVisitados.size(); i++) {
				Vertice lVertice = (Vertice) this.lVisitados.get(i);
			
				System.out.println("V: " + lVertice.getNome());
				System.out.println("D: " + lVertice.getGrau());
				
			}
			this.lVisitados = new ArrayList();
		}

		return "Distancia calculada, veja o resultado no Log";

	}

	public String getNome() {

		return "* Distancia";

	}

	protected void visitaVertice(Vertice pVertice) {

		LinkedList lFila = new LinkedList();

		this.lVisitados.add(pVertice);

		lFila.addLast(pVertice);

		while (!lFila.isEmpty()) {

			Vertice lVertice = (Vertice) lFila.removeFirst();

			ArrayList listaAdj = grafo.getAdjacencias(lVertice);

			for (int i = 0; i < listaAdj.size(); i++) {

				Vertice v = (Vertice) listaAdj.get(i);

				if (!(this.buscaVertice(v))) {

					v.setGrau(lVertice.getGrau() + 1);
					this.lVisitados.add(v);
					lFila.addLast(v);

				}

			}
		}

	}
}

