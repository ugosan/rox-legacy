/*
    Rox - Teoria dos Grafos
     Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/

package org.ugosan.rox;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import org.ugosan.rox.dialogs.ImageCache;
import org.ugosan.rox.dialogs.EditNodeTextField;
import org.ugosan.rox.grafo.*;

import java.util.*;
import java.io.*;
/**
*  Este componente possui um RoxPanel e monitora todos os eventos de teclado
* e mouse, implementando MouseInputListener. Toda a lógica de operação gráfica
* por parte do usuário está aqui, RoxPanel apenas desenha tudo o que é
* pertinente ao grafo. Futuramente, nesta classe nao haverá tudo o que está
* nela agora, ela será fragmentada para obedecer a padrões que sejam mais
* assimiláveis.
* @author Ugo Braga Sangiorgi
**/
public class RoxFrame extends JInternalFrame implements MouseInputListener{
    RoxPanel panel;
    Main rox;

    Vertice mov;
    Aresta novaAresta;
    Vertice vtemp1;
    Vertice vtemp2;

    RoxPopupMenu popup = new RoxPopupMenu();

    boolean criandoAresta = false;
    boolean removendoAresta = false;
    boolean colorindoAresta = false;
    boolean movendoVertice = false;

    boolean mostrarMatrizes = false;
    boolean mostrarTDQ = false;
    boolean ativarOtimizacao = false;

    boolean selecionandoVertices = false;


    int mousex;
    int mousey;


    public RoxFrame()throws Exception{

        panel = new RoxPanel(rox, this);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        getContentPane().add(panel);

        if(Main.conf.getProperty("options.showm").equals("1")) mostrarMatrizes = true;
        else mostrarMatrizes = false;

        if(Main.conf.getProperty("options.showFT").equals("1")) mostrarTDQ = true;
        else mostrarTDQ = false;

        if(Main.conf.getProperty("options.optimization").equals("1")) ativarOtimizacao = true;
        else ativarOtimizacao = false;

        Main.itemOpcoesMatrizes.setSelected(mostrarMatrizes);
        Main.itemOpcoesOtimizacao.setSelected(ativarOtimizacao);
        Main.itemOpcoesTDQ.setSelected(mostrarTDQ);

    }

    public RoxPanel getRoxPanel(){
    return panel;
    }

    public void reset(){
        mov = null;
        novaAresta = null;
        vtemp1 = null;
        vtemp2 = null;
        criandoAresta = false;
        removendoAresta = false;
        colorindoAresta =false;
        this.repaint();
    }



    public final void setRoxPanelCursor(Cursor cursor){
        panel.setCursor(cursor);
    }
    public final void mouseClicked(MouseEvent e) {

         if(!e.isControlDown()){
            if(e.getButton()==e.BUTTON1){

            if(rox.acao_selecionada==Main.CRIAR_VERTICE){
                Main.getInstance().getGrafo().addVertice(e.getX()-10,e.getY()-10);
                this.repaint();
            }else if(rox.acao_selecionada==Main.APAGAR_VERTICE){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if(nomevertice!=-1) Main.getInstance().getGrafo().delVerticeByNome(nomevertice);
                this.repaint();
            }else if(rox.acao_selecionada==Main.CRIAR_ARESTA){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if(nomevertice!=-1) {

                    if(( vtemp2 != null )&&( vtemp2 != Main.getInstance().getGrafo().getVerticeByNome(nomevertice) )){ //não permite laço
                        Main.getInstance().getGrafo().addAresta(vtemp2,Main.getInstance().getGrafo().getVerticeByNome(nomevertice));
                        vtemp1 = vtemp2;
                        vtemp2 = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);
                    }
                }
                this.repaint();
            }
            else if(rox.acao_selecionada==Main.COLORIR_ARESTA){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if(nomevertice!=-1) {

                    if(( vtemp2 != null )&&( vtemp2 != Main.getInstance().getGrafo().getVerticeByNome(nomevertice) )){ //não permite laço
                        Aresta a = Main.getInstance().getGrafo().getArestaEntreVertices(vtemp2,Main.getInstance().getGrafo().getVerticeByNome(nomevertice));
                        if(a != null) a.setCor(rox.cor_selecionada);
                        vtemp1 = vtemp2;
                        vtemp2 = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);
                    }
                 }
                 this.repaint();
            }else if(rox.acao_selecionada==Main.COLORIR_VERTICE){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if(nomevertice!=-1) Main.getInstance().getGrafo().getVerticeByNome(nomevertice).setCor(rox.cor_selecionada);
                this.repaint();
            }

            }else if((e.getButton()==e.BUTTON3)||(e.getButton()==e.BUTTON2)){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if(nomevertice!=-1)
                    try{
                    this.showPopupMenu(e.getX(),e.getY(),Main.getInstance().getGrafo().getVerticeByNome(nomevertice));
                    }catch(Exception ex){
                    ex.printStackTrace();
                    }
            }


         }else{

            if(e.getClickCount()==10){
                try{
                    Main.getInstance().itemNovoDigrafo.setEnabled(true);
                    JOptionPane.showMessageDialog(this,"Digrafo Habilitado, seu cheater ","Cheat r0x >:)",1);
                }catch(Exception ex){}
            }
         }


	}

    public final void mousePressed(MouseEvent e) {
        if (e.isControlDown()||rox.acao_selecionada==Main.MOVER){

            int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
            if(nomevertice!=-1){
                mov = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);

            }
        }else{

        if(rox.acao_selecionada==Main.CRIAR_ARESTA){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if(nomevertice!=-1){
                    if(!criandoAresta){
                        vtemp1 = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);
                        criandoAresta=true;
                    }
                }
            this.repaint();
            return;

        }
        if(rox.acao_selecionada==Main.APAGAR_ARESTA){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if(nomevertice!=-1){
                    if(!removendoAresta){
                        vtemp1 = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);
                        removendoAresta=true;
                    }
                }
            this.repaint();
            return;

        }
        if(rox.acao_selecionada==Main.COLORIR_ARESTA){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if(nomevertice!=-1){
                    if(!colorindoAresta){
                        vtemp1 = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);
                        colorindoAresta=true;
                    }
                }
            this.repaint();
            return;

        }
        }
    this.repaint();
    }

    public final void mouseReleased(MouseEvent e) {
            if((rox.acao_selecionada==Main.CRIAR_ARESTA)&&(criandoAresta)){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if((nomevertice!=-1)&&(nomevertice!=vtemp1.getNome())){ //não permite laço
                    vtemp2 = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);
                    Main.getInstance().getGrafo().addAresta(vtemp1,vtemp2);
                }
            }
            if((rox.acao_selecionada==Main.COLORIR_ARESTA)&&(colorindoAresta)){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if((nomevertice!=-1)&&(nomevertice!=vtemp1.getNome())){ //não permite laço
                    vtemp2 = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);
                    Aresta a = Main.getInstance().getGrafo().getArestaEntreVertices(vtemp1,vtemp2);
                    if(a!=null) a.setCor(rox.cor_selecionada);
                }
            }
            if((rox.acao_selecionada==Main.APAGAR_ARESTA)&&(removendoAresta)){
                int nomevertice = Main.getInstance().getGrafo().existeVertice(e.getX(),e.getY());
                if((nomevertice!=-1)&&(nomevertice!=vtemp1.getNome())){
                    vtemp2 = Main.getInstance().getGrafo().getVerticeByNome(nomevertice);
                    Main.getInstance().getGrafo().delAresta(vtemp1,vtemp2);
                }
            }

        //substituir os cod abaixo por reset
        criandoAresta=false;
        removendoAresta=false;
        movendoVertice =false;
        colorindoAresta = false;
        selecionandoVertices = false;
        this.repaint();
    }

    public final void mouseDragged(MouseEvent e) {
        if(e.isControlDown()||rox.acao_selecionada==Main.MOVER){
            if(mov!=null){
                movendoVertice = true;
                criandoAresta=false;
                removendoAresta=false;
                colorindoAresta = false;


                mov.setPosX(e.getX()-(mov.getLargura_imagem()/2));
                mov.setPosY(e.getY()-(mov.getAltura_imagem()/2));

                this.repaint();
            }


        }else if(e.isShiftDown()){
            /*  ainda nao...
            rox.acao_selecionada=8;
            selecionandoVertices = true;
            mousex=e.getX();
            mousey=e.getY();
              */
        }else{
            if((rox.acao_selecionada==Main.CRIAR_ARESTA)||(rox.acao_selecionada==Main.APAGAR_ARESTA)||(rox.acao_selecionada==Main.COLORIR_ARESTA)){
                mousex=e.getX();
                mousey=e.getY();
                this.repaint();
            }
        }


    }



    public final void showPopupMenu(int x, int y, Vertice v) throws Exception{


        RoxMenu menuImagens = ImageCache.getInstance().getMenuImagens();
        menuImagens.setVertice(v);

        EditNodeTextField verticeTextField = new EditNodeTextField(v);


        popup.removeAll();
        popup.add("Vertice "+v.getNome());
        popup.addSeparator();

        popup.add(verticeTextField);
        popup.add(menuImagens);

        popup.show(panel,x,y);



    }


    public final void mouseMoved(MouseEvent e) {}
    public final void mouseEntered(MouseEvent e) {}
	public final void mouseExited(MouseEvent e) {}


}