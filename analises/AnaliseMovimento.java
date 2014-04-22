/*
    Análise demonstrando movimento
    Copyright (C) 2003  Ugo Braga Sangiorgi
    Esta classe é integrante do software Rox - Teoria dos Grafos, seu
    código deve permanecer livre segundo a GNU General Public License.
*/
import ugo.rox.*;
import ugo.rox.grafo.*;
import java.util.*;

public class AnaliseMovimento implements RoxAnalise {


    public AnaliseMovimento(){}

    //JAMAIS, EU DISSE, JAMAIS FAÇA ISSO:
    //
    //private Rox rox = Rox.getInstance();
    //
    //isso vai pro construtor, levei 3 dias pra descobrir
    //que o que havia de errado era isso, inclusive precisei
    //descompilar a classe, senao nao ia saber nunca!!!! aaaaaaaargh
    public String execAnalise(Grafo grafo) throws Exception{

        Rox.getInstance().setWaitState(false);
        int velocidade = 100;
        int passo = 3;
        for(int i=1;i<5;i++){
		Vertice v = grafo.getVerticeByNome(1);
		
		
        do{
            velocidade = velocidade - passo;
            v.setPosX(v.getPosX()+ (velocidade /5 ));

            this.repaint();

        }while(velocidade>0);
		}
    return(null);
    }

    private void movimenta(Vertice v, int x, int y){
        for(int i=v.getPosX(); i>x ;i--){
            for(int j=v.getPosY(); j>y ; j--){

            }
        }




    }

    private void repaint(){
        Rox.getInstance().repaint();
            try{
                Thread.sleep(Rox.getInstance().getTDQ());
            }catch(Exception e){}

    }

    public String getNome(){
        return("Teste de Movimento");
    }


}