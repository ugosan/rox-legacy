import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;

public class AnaliseColorizacao implements RoxAnalise {

	private ArrayList coresUtilizadas;
	private ArrayList coresPossiveis;

	public AnaliseColorizacao() {

		this.coresPossiveis = new ArrayList();
		this.preencheCoresPossiveis();
		this.coresUtilizadas = new ArrayList();
		this.utilizaCor();

	}

	public void utilizaCor() {

		Random randomColor = new Random();
		int posicaoCor = randomColor.nextInt(this.coresPossiveis.size());

		Color cor = (Color) this.coresPossiveis.get(posicaoCor);
		this.coresUtilizadas.add(cor);
		this.coresPossiveis.remove(posicaoCor);

	}

	/*
	 * Este metodo vai gerar as cores possiveis para colorir o grafo. 
	 */
	private void preencheCoresPossiveis() {

		for (int r = 0; r < 256; r += 122) {
			for (int g = 0; g < 256; g += 122) {
				for (int b = 0; b < 256; b += 122) {

					this.coresPossiveis.add(new Color(r, g, b));

				}
			}
		}

	}

	public String execAnalise(Grafo pGrafo) throws Exception {

		ArrayList lVertices = pGrafo.getVertices();

		for (int i = 0; i < lVertices.size(); i++) {

			ArrayList lVerticesAdj =
				pGrafo.getAdjacencias(pGrafo.getVertice(i));
			Color corPintura = this.escolheCor(lVerticesAdj);
			System.out.println("Cor utilizada:" + corPintura);
			pGrafo.getVertice(i).setCor(corPintura);

		}

		StringBuffer retorno = new StringBuffer();
		retorno.append("Colorização realizada. Confira o grafo.\n");
		retorno.append(
			"Quantidade de Cores Utilizadas: " + this.coresUtilizadas.size());

		return retorno.toString();
	}

	public Color escolheCor(ArrayList pListaVerticesAdj) {

		boolean achou = false;

		for (int i = 0; i < this.coresUtilizadas.size(); i++) {

			Color corI = (Color) this.coresUtilizadas.get(i);

			for (int j = 0; j < pListaVerticesAdj.size(); j++) {

				Color corJ = ((Vertice) pListaVerticesAdj.get(j)).getCor();
				System.out.println("Cor do vertice adj " + j + " - " + corJ);

				System.out.println("Cores: " + corI + " - " + corJ);

				if (corI.equals(corJ)) {

					System.out.println("******************Sao iguais");

					achou = true;

					j = pListaVerticesAdj.size();
				} else {

					achou = false;

				}
			}

			if (achou == false) {
				return (Color) this.coresUtilizadas.get(i);
			}

		}

		this.utilizaCor();

		return (Color) this.coresUtilizadas.get(
			this.coresUtilizadas.size() - 1);
	}

	public String getNome() {

		return "* Colorização";

	}

}

