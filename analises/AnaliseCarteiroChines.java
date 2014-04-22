import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Aresta;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;

public class AnaliseCarteiroChines implements RoxAnalise {

	private final String DISTANCIA = "distancia";
	private LinkedList aVisitados = new LinkedList();
	private LinkedList aLimites = new LinkedList();
	private ArrayList aArestas;
	private Grafo aGrafo;
	private LinkedList aVerticesGrauImpar;
	private LinkedList aArestasDuplicadas = new LinkedList();
	private LinkedList aCaminhoDoCarteiro = new LinkedList();


	public String execAnalise(Grafo pGrafo) throws Exception {
		
		this.aGrafo = pGrafo;
		this.aGrafo.resetAcesos();
		
		this.aVerticesGrauImpar = this.getVerticesGrauImpar();
		
		AnaliseGrafoConexo lAnaliseGrafoConexo = new AnaliseGrafoConexo();
		lAnaliseGrafoConexo.iniciaAnalise(pGrafo);
		
		if(lAnaliseGrafoConexo.componentes.size() > 1) {
			
			return "ERRO: O grafo possui mais de uma componente.";
			
		}
		
		if(this.aGrafo.getQtdArestas() == 0) {
			
			return "ERRO: Grafo sem arestas!";
			
		}
		
		if(this.aVerticesGrauImpar.size() > 2) {
			
			return "FALHA: O grafo contém mais do que 2 (dois) vértices com grau ímpar!";
					
		} else {
			
			this.aArestas = this.aGrafo.getArestas();	
			
			for (int i = 0; i < this.aArestas.size(); i++) {

				// Pegando a aresta atual
				Aresta lArestaAtual = (Aresta) aArestas.get(i);
				
				// Pegando os vertices desta aresta
				String lPeso = JOptionPane.showInputDialog(this.getMensagemInput(lArestaAtual));
				lArestaAtual.putPropriedade("peso", lPeso);
				
			}
			
			if (this.aVerticesGrauImpar.size() != 0) {
				
				this.analiseDjikstra();
				this.getCaminho();
				this.carteiroChines((Vertice)this.aVerticesGrauImpar.get(0));
			
			} else {
				
				this.carteiroChines((Vertice) this.aGrafo.getVertices().get(0));
				
			}
			
			System.out.println();
			System.out.println();
			System.out.println("ROTEIRO DO CARTEIRO CHINES:");
			for(int i = 0; i < this.aCaminhoDoCarteiro.size(); i++) {
				System.out.println("Aresta: " + ((Aresta)this.aCaminhoDoCarteiro.get(i)).getVertice1().getRotulo() + " - " + ((Aresta)this.aCaminhoDoCarteiro.get(i)).getVertice2().getRotulo());
			}
			System.out.println();
			System.out.println();
			
		}		
		
		return "Análise executada com sucesso. Verifique o caminho no LOG!";
		
		
	}
	
	private void carteiroChines(Vertice pVertice){
		
		ArrayList lVerticesAdj = new ArrayList(this.aGrafo.getAdjacencias(pVertice));

	
		while(this.aGrafo.getQtdVertices() > 0) {
		
			Vertice lVerticeAtual = (Vertice)lVerticesAdj.get(0);
		
			Aresta menorAresta = this.aGrafo.getArestaEntreVertices(pVertice, lVerticeAtual);

			for(int i = 1; i < lVerticesAdj.size(); i++) {
	
				lVerticeAtual = (Vertice)lVerticesAdj.get(i);
				Aresta lArestaAtual = this.aGrafo.getArestaEntreVertices(pVertice, lVerticeAtual);			

				if (Integer.parseInt(menorAresta.getPropriedade("peso")) > Integer.parseInt(lArestaAtual.getPropriedade("peso"))) {

					menorAresta = lArestaAtual;

				}

			}

			lVerticesAdj.remove(menorAresta);

			if(menorAresta != null) {
			
				if(this.removerAresta(menorAresta)) {
	
					if(menorAresta != null && pVertice.equals(menorAresta.getVertice1())) {
			
						this.carteiroChines(menorAresta.getVertice2());
			
					} else if(menorAresta != null) {
			
						this.carteiroChines(menorAresta.getVertice1());
			
					}
				
				}
			}

		}
		
	}
	
	private void imprimeAresta(Aresta pAresta) {
		System.out.println("Aresta: " + pAresta.getVertice1().getRotulo() + "-" + pAresta.getVertice2().getRotulo() + " Peso: " + pAresta.getPropriedade("peso"));
	}
	
	private boolean removerAresta(Aresta pAresta) {
		
		Vertice lVertice1 = pAresta.getVertice1();
		Vertice lVertice2 = pAresta.getVertice2();
		String pesoAresta = pAresta.getPropriedade("peso");
		
		// Aresta pode ser removida pois está duplicada.
		if(this.aArestasDuplicadas.contains(pAresta)) {
			
			this.aArestasDuplicadas.remove(pAresta);
			
			this.aCaminhoDoCarteiro.add(pAresta);

			// Aresta removida
			return true;
			
		} else {
			
			AnaliseGrafoConexo lComponenteConexa = new AnaliseGrafoConexo();
			lComponenteConexa.iniciaAnalise(this.aGrafo);

			int qtdeComponentesConexasAntes = lComponenteConexa.componentes.size();

			this.aGrafo.delAresta(lVertice1,lVertice2);

			boolean v1Rem = false;
			boolean v2Rem = false;
			if(aGrafo.getAdjacencias(lVertice1).size() == 0) {
	
				this.aGrafo.delVerticeByNome(lVertice1.getNome());
				v1Rem = true;
	
			}

			if(aGrafo.getAdjacencias(lVertice2).size()== 0) {
					
				this.aGrafo.delVerticeByNome(lVertice2.getNome());
				v2Rem = true;
	
			}
			
			
			AnaliseGrafoConexo lComponenteConexa2 = new AnaliseGrafoConexo();
			lComponenteConexa2.iniciaAnalise(this.aGrafo);
			int qtdeComponentesConexasDepois = lComponenteConexa2.componentes.size();
			

			if(qtdeComponentesConexasAntes < qtdeComponentesConexasDepois) {
				
				if(v1Rem) {

					this.aGrafo.addVertice(lVertice1);
					
				}
				
				if(v2Rem) {
					
					this.aGrafo.addVertice(lVertice2);
					
				}
				
				this.aGrafo.addAresta(lVertice1, lVertice2);
				this.aGrafo.getArestaEntreVertices(lVertice1, lVertice2).putPropriedade("peso", pesoAresta);
				
				// Aresta nao removida
				return false;
				
			} else {
				
				this.aCaminhoDoCarteiro.add(pAresta);
				
				// Aresta removida
				return true;
			}
				
		}
				
	}
	
	private void getCaminho() {
		
		Vertice lVerticeAtual = (Vertice)this.aVerticesGrauImpar.get(1);
		this.aGrafo.getVerticeByNome(lVerticeAtual.getNome()).setCor(255, 0, 0);
		
		boolean achou = false;
		
		while(!lVerticeAtual.getPropriedade(this.DISTANCIA).equals("0")) {
			
			lVerticeAtual = this.getProximoVertice(lVerticeAtual);
			
		}
		
	}
	
	private Vertice getProximoVertice(Vertice pVertice) {
		
		Vertice lVerticeAdj = null;
		
		boolean achou = false;
		
		LinkedList lAdjacencias = new LinkedList(aGrafo.getAdjacencias(pVertice));
		
		while(!achou) {
	
			lVerticeAdj = (Vertice)lAdjacencias.getFirst();
			Aresta lAresta = this.aGrafo.getArestaEntreVertices(pVertice, lVerticeAdj);
			
			int rotuloPVertice = Integer.parseInt(pVertice.getPropriedade(this.DISTANCIA));
			int rotuloVerticeAdj = Integer.parseInt(lVerticeAdj.getPropriedade(this.DISTANCIA));
			int pesoAresta = Integer.parseInt(lAresta.getPropriedade("peso"));
			
			if((rotuloPVertice - pesoAresta) == rotuloVerticeAdj) {
				
				lAresta.putPropriedade("peso", lAresta.getPropriedade("peso"));
				this.aArestasDuplicadas.addFirst(lAresta);
				lAresta.setCor(0, 0, 255);
				achou = true;
				
			} else {
				
				lAdjacencias.removeFirst();
				
			}

		}
		
		return lVerticeAdj;
	}
	
	private LinkedList getVerticesGrauImpar() {
		
		LinkedList lRetorno = new LinkedList();
		
		for(int i = 0; i < aGrafo.getQtdVertices(); i++) {
			
			Vertice lVerticeAtual = this.aGrafo.getVertice(i);
			
			if((this.aGrafo.getAdjacencias(lVerticeAtual).size() % 2) != 0) {
				
				lRetorno.addLast(lVerticeAtual);
				
			}
			
		}
		
		return lRetorno;
		
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
		
		Vertice lVerticeInicial = (Vertice)this.aVerticesGrauImpar.get(0);		
		
		for(int i = 0; i < this.aGrafo.getQtdVertices(); i++) {
			
			((Vertice)aGrafo.getVertice(i)).putPropriedade(this.DISTANCIA,"*");
			
		}
		
		//Diferenciando o vertice inicial
		lVerticeInicial.putPropriedade(this.DISTANCIA,"0");
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
			
			if (Integer.parseInt(menorVertice.getPropriedade(this.DISTANCIA)) > Integer.parseInt(lVerticeAtual.getPropriedade(this.DISTANCIA))) {
				
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
					
					lVerticeAdj.putPropriedade(this.DISTANCIA, novoRotulo.toString());
					this.aLimites.addLast(lVerticeAdj);
					
				} else {
					
					int rotuloAtual = Integer.parseInt(lVerticeAdj.getPropriedade(this.DISTANCIA));
					
					if(rotuloAtual > novoRotulo.intValue()) {
						
						int posicao = aLimites.indexOf(lVerticeAdj);
						((Vertice)aLimites.get(posicao)).putPropriedade(this.DISTANCIA, novoRotulo.toString());
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private int calculaRotulo(Vertice v1, Vertice v2) {
		
		Aresta lAresta = aGrafo.getArestaEntreVertices(v1, v2);
		int pesoAresta = Integer.parseInt(lAresta.getPropriedade("peso"));
		
		return (pesoAresta + Integer.parseInt(v1.getPropriedade(this.DISTANCIA)));
		
	}
	

	public String getNome() {
		
		return "* Carteiro Chinês (Fleury)";
	}

}
