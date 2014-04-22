import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Aresta;
import ugo.rox.grafo.Vertice;
import ugo.rox.grafo.Grafo;

public class AnaliseDjikstra implements RoxAnalise {

	private LinkedList aVisitados = new LinkedList();
	private LinkedList aLimites = new LinkedList();
	private ArrayList aArestas;
	private Grafo aGrafo;

	public String execAnalise(Grafo pGrafo) throws Exception {

		aGrafo = pGrafo;

		aArestas = aGrafo.getArestas();

		for (int i = 0; i < aArestas.size(); i++) {

			// Pegando a aresta atual
			Aresta lArestaAtual = (Aresta) aArestas.get(i);

			// Pegando os vertices desta aresta
			String lPeso = JOptionPane.showInputDialog(this.getMensagemInput(lArestaAtual));
			lArestaAtual.putPropriedade("peso", lPeso);

		}

		for (int i = 0; i < aArestas.size(); i++) {
			System.out.println(
				"Aresta "
					+ i
					+ ":"
					+ ((Aresta) aArestas.get(i)).getPropriedade("peso"));
		}
		
		this.analiseDjikstra();

		return "Análise concluída. Verificar o grafo no Rox.";

	}

	private String getMensagemInput(Aresta pAresta) {

		if (Integer.parseInt(pAresta.getVertice1().getRotulo())
			> Integer.parseInt(pAresta.getVertice2().getRotulo())) {
				
			return "Qual o peso da aresta entre os vertices: "
				+ pAresta.getVertice2().getRotulo()
				+ " e "
				+ pAresta.getVertice1().getRotulo();
				
		} else {
			
			return "Qual o peso da aresta entre os vertices: "
				+ pAresta.getVertice1().getRotulo()
				+ " e "
				+ pAresta.getVertice2().getRotulo();
				
		}

	}

	public void analiseDjikstra() {
		
		String vi = JOptionPane.showInputDialog("Informe qual o vertice INICIAL:");
		
		Vertice lVerticeInicial = aGrafo.getVerticeByNome(vi);
		
		
		for(int i = 0; i < aGrafo.getQtdVertices(); i++) {
			
			((Vertice)aGrafo.getVertice(i)).setRotulo("*");
			
		}
		
		//Diferenciando o vertice inicial
		lVerticeInicial.setRotulo("0");
		lVerticeInicial.setCor(255, 0, 0);
		
		// Adicionando na lista de visitados
		this.aVisitados.addLast(lVerticeInicial);
		
		while (this.aVisitados.size() < this.aGrafo.getQtdVertices()) {
			
			this.atualizaLimites();
			this.aVisitados.addLast(this.buscaMenorCaminho());
			
		}

	}
	
	private Vertice buscaMenorCaminho() {
		
		Vertice menorVertice = (Vertice)this.aLimites.getFirst();
		
		for(int i = 1; i < this.aLimites.size(); i++) {
			
			Vertice lVerticeAtual = (Vertice)this.aLimites.get(i);
			
			if (Integer.parseInt(menorVertice.getRotulo()) > Integer.parseInt(lVerticeAtual.getRotulo())) {
				
				menorVertice = lVerticeAtual;
				
			}
			
		}
		
		this.aLimites.remove(menorVertice);
		
		return menorVertice;
		
	}
	
	private void atualizaLimites() {
		
		Vertice lVerticeAtual = (Vertice)this.aVisitados.getLast();
		ArrayList lAdjacencias = new ArrayList(aGrafo.getAdjacencias(lVerticeAtual));
		
		for(int i = 0; i < lAdjacencias.size(); i++) {
			
			Vertice lVerticeAdj = (Vertice)lAdjacencias.get(i);
			
			Integer novoRotulo = new Integer(this.calculaRotulo(lVerticeAtual, lVerticeAdj));
			
			if(!aVisitados.contains(lVerticeAdj)) {
				
				if(!this.aLimites.contains(lVerticeAdj)) {
					
					lVerticeAdj.setRotulo(novoRotulo.toString());
					this.aLimites.addLast(lVerticeAdj);
					
				} else {
					
					int rotuloAtual = Integer.parseInt(lVerticeAdj.getRotulo());
					
					if(rotuloAtual > novoRotulo.intValue()) {
						
						int posicao = aLimites.indexOf(lVerticeAdj);
						((Vertice)aLimites.get(posicao)).setRotulo(novoRotulo.toString());
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private int calculaRotulo(Vertice v1, Vertice v2) {
		
		Aresta lAresta = aGrafo.getArestaEntreVertices(v1, v2);
		int pesoAresta = Integer.parseInt(lAresta.getPropriedade("peso"));
		
		return (pesoAresta + Integer.parseInt(v1.getRotulo()));
		
	}
	
	public String getNome() {

		return "* Djikstra";

	}
}

