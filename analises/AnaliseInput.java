import java.util.*;
import java.awt.Color;

public class AnaliseInput implements RoxAnalise {


    public AnaliseInput(){}



    public String execAnalise(Grafo grafo)throws Exception{
        Rox rox = Rox.getInstance();

        String s,s2="";
        int num;
        ArrayList verticesSelecionados = new ArrayList();

        s = (String)rox.getInput("Informe um numero de um vertice");
        num = Integer.valueOf(s).intValue();

        while(s2!=null){
            s2 = (String)rox.getInput("Escolha um vertice, ou cancele para terminar",grafo.getNomesVertices().toArray());
            if(s2!=null) verticesSelecionados.add(grafo.getVerticeByNome(s2));
        }

        for(int i=0;i<verticesSelecionados.size();i++){
            grafo.acender((Vertice)verticesSelecionados.get(i),Color.yellow);
        }

        return("input veio: "+num+"\nQuantidade de Vertices selecionados: "+verticesSelecionados.size());
    }

    public String getNome(){
        return("Analise de Input");
    }

}