import java.util.Collection;
import java.util.LinkedList;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;



public class AnaliseOrdemTopologica implements RoxAnalise {

	private Grafo newGrafo;
	private LinkedList listaVerticesComGrau = new LinkedList();
	private int ordem = 0;

	public String execAnalise(Grafo pGrafo) throws Exception {

		newGrafo = pGrafo;

		if (!newGrafo.ehDigrafo) {
			return "Esta análise só é possível para Dígrafos";
		}
		
		this.setGraus();
		this.diminuiGrau(this.retornaGrauZero());
		for (int i = 0; i < this.listaVerticesComGrau.size(); i++){
			System.out.println("Nome: " + ((VerticeComOrdem)this.listaVerticesComGrau.get(i)).getNome());
			System.out.println("Ordem: " + ((VerticeComOrdem)this.listaVerticesComGrau.get(i)).ordem);
		}

		return "OK";

	}
	
	private String imprimeOrdem(){
		return null;
	}
	
	private void diminuiGrau(Collection pCol) {
		
		LinkedList lLista = new LinkedList(pCol);
		
		for(int i = 0; i < lLista.size(); i++) {
			VerticeComOrdem vI = (VerticeComOrdem)lLista.get(i);
			for(int j = 0; j < this.listaVerticesComGrau.size(); j++) {
				VerticeComOrdem vJ = (VerticeComOrdem)this.listaVerticesComGrau.get(j);
				
				if(vI.equals(vJ)) {
					vJ.grau--;
					this.listaVerticesComGrau.add(j, vJ);
				}
			}
		}		
	}
	
	private Collection retornaGrauZero() {
		
		LinkedList listaVerticesAdjGZ = new LinkedList();		
		ordem++;
		
		for(int i = 0; i < listaVerticesComGrau.size(); i++) {
			
			VerticeComOrdem lVertice = (VerticeComOrdem)this.listaVerticesComGrau.get(i); 
			
			if(lVertice.grau == 0) {
				
				lVertice.grau = -1; // Desativar o vertice
				lVertice.ordem = ordem;
				listaVerticesComGrau.add(i, lVertice);
				listaVerticesAdjGZ.addAll(newGrafo.getAdjacencias(lVertice.v));
				
			}
		}
		
		return listaVerticesAdjGZ;
	}
	
	

	private void setGraus() {

		for (int i = 0; i < newGrafo.getQtdVertices(); i++) {

			Vertice lVertice = newGrafo.getVertice(i);
			int grau = this.atualizaGrauVertice(i);
			VerticeComOrdem novoVertice =
				new VerticeComOrdem(lVertice.getPosX(), lVertice.getPosY());
			novoVertice.grau = grau;
			novoVertice.ordem = 0;
			
			this.listaVerticesComGrau.addLast(novoVertice);

		}

	}

	private int atualizaGrauVertice(int posicao) {

		int grau = 0;

		int[][] matrizAdjacencias = newGrafo.getMatrizAdjacencias();

		for (int i = 0; i < newGrafo.getQtdVertices(); i++) {
			grau += matrizAdjacencias[i][posicao];
		}

		return grau;
	}

	public String getNome() {
		return "* Ordem Topológica";
	}

}