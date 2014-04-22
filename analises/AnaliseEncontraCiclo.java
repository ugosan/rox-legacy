import java.util.ArrayList;

import ugo.rox.RoxAnalise;
import ugo.rox.grafo.Grafo;
import ugo.rox.grafo.Vertice;

public class AnaliseEncontraCiclo
	extends AnaliseBuscaProfundidade
	implements RoxAnalise {

	private boolean temCiclo = false;

	public String execAnalise(Grafo pGrafo) throws Exception {

		ArrayList lVertices = pGrafo.getVertices();

		for (int i = 0; i < lVertices.size(); i++) {

			Vertice v = (Vertice) lVertices.get(i);

			/* Se nao encontrar o vertice chama o visitaVertice */
			if (!(this.buscaVertice(v))) {
				this.visitaVertice(pGrafo, v, null);
			}

		}

		if (this.temCiclo) {
			return "O grafo POSSUI ciclo!";
		} else {
			return "O grafo NÃO POSSUI ciclo!";
		}

	}

	private void visitaVertice(
		Grafo pGrafo,
		Vertice pVertice,
		Vertice chamador) {
		int tamanhoVisitados = this.lVisitados.size();

		this.lVisitados.add(pVertice);

		ArrayList listaAdj = pGrafo.getAdjacencias(pVertice);

		if (chamador != null) {
			for (int k = 0; k < listaAdj.size(); k++) {
				Vertice lVertice = (Vertice) listaAdj.get(k);
				if (lVertice.equals(chamador)) {
					listaAdj.remove(k);
				}
			}
		}

		for (int i = 0; i < listaAdj.size(); i++) {

			Vertice v = (Vertice) listaAdj.get(i);

			/* Se nao encontrar o vertice chama o metodo novamente */
			if (!(this.buscaVertice(v))) {

				this.visitaVertice(pGrafo, v, pVertice);

			} else {
				if (this.lVisitados.contains(v)) {

					this.temCiclo = true;

				}

			}

		}

	}

	public String getNome() {

		return "* Verifica Ciclo";

	}

	public void imprimeArray(ArrayList newArray) {

		System.out.print("ArrayList: ");

		for (int i = 0; i < newArray.size(); i++) {

			System.out.print(((Vertice) newArray.get(i)).getNome() + " ");
		}
		System.out.println();

	}

}

