import java.util.ArrayList;
import java.util.LinkedList;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;

public class AnaliseDiametro extends AnaliseBuscaLargura implements RoxAnalise {
	
	private int diametro;
	protected Grafo grafo;

	public String execAnalise(Grafo pGrafo) throws Exception {
		
		this.diametro = 0;
		
		this.grafo = pGrafo;
		
		ArrayList listaVertices = this.grafo.getVertices();
		
		for(int i = 0; i < listaVertices.size(); i++) {
			
			this.visitaVertice((Vertice)listaVertices.get(i));
			int maiorVisitado = this.buscaMaiorVisitado();
			
			if(maiorVisitado > this.diametro) {
				
				this.diametro = maiorVisitado;
				
			}
			
			this.lVisitados = new ArrayList();
			this.zeraGraus();
			
		}

		return "* Diâmetro = " + this.diametro;
		
	}
	
	private void zeraGraus() {
		
		for(int i = 0; i < this.grafo.getVertices().size(); i++) {
			
			((Vertice)this.grafo.getVertices().get(i)).setGrau(0);
			
		}
		
	}
	
	
	private int buscaMaiorVisitado() {
		
		int maior = 0;
		
		for(int i = 0; i < this.lVisitados.size(); i++) {
			
			Vertice v = (Vertice) this.lVisitados.get(i);
			
			if(v.getGrau() > maior) {
				maior = v.getGrau();
			}
			
		}
		
		return maior;
	}

	public String getNome() {

		return "* Diametro";
		
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

