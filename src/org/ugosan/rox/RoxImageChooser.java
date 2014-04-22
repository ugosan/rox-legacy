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

import java.io.*;

/**
*  O dialog que é mostrado quando se necessita adicionar mais imagens.
* @author Ugo Braga Sangiorgi
**/
public class RoxImageChooser extends JFrame implements ActionListener, MouseListener, KeyListener{
    JList list_imagens;
    JScrollPane sp_list_imagens;
    JPanel panel_imagem;
    JLabel label_imagens;
    JButton button_adicionar;
    JButton button_ok;
    RoxImageCache cache;
    private static RoxImageChooser instance;
    DefaultListModel listModel_list_imagens;
    private JFileChooser filechooser;

    private RoxImageChooser() {
        RoxImageChooserLayout customLayout = new RoxImageChooserLayout();


        getContentPane().setLayout(customLayout);

        listModel_list_imagens = new DefaultListModel();

        filechooser = new JFileChooser();

        list_imagens = new JList(listModel_list_imagens);
        list_imagens.addMouseListener(this);
        list_imagens.addKeyListener(this);
        list_imagens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sp_list_imagens = new JScrollPane(list_imagens);

        getContentPane().add(sp_list_imagens);

        panel_imagem = new JPanel();
        getContentPane().add(panel_imagem);

        label_imagens = new JLabel("Imagens");
        getContentPane().add(label_imagens);

        button_adicionar = new JButton("Adicionar uma imagem");
        button_adicionar.addActionListener(this);
        getContentPane().add(button_adicionar);

        button_ok = new JButton("Ok");
        button_ok.addActionListener(this);
        getContentPane().add(button_ok);

        setIconImage(Rox.getInstance().getIconImage());
        setSize(getPreferredSize());
        setTitle("Adicionar Imagens");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                instance.setVisible(false);
            }
        });
    }

     public static RoxImageChooser getInstance(){
        try{
        if (instance==null){
            instance = new RoxImageChooser();
            instance.setResizable(false);
        }
        }catch(Exception e){
            System.out.println("Impossivel instanciar RoxImageChooser: "+e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        instance.montaLista();

        instance.setVisible(true);
    return(instance);
    }

    public void montaLista(){
        cache = RoxImageCache.getInstance();
        Object[] caminhos = cache.getAllKeys();
        listModel_list_imagens.removeAllElements();

        for(int i=0;i<caminhos.length;i++){
            String caminho = (String)caminhos[i];
            listModel_list_imagens.addElement(caminho.substring(caminho.lastIndexOf(File.separator)+1));
        }

    }
    private void desenhaImagem(){
        Graphics2D g = (Graphics2D)panel_imagem.getGraphics();
        g.setColor(g.getBackground());
        g.fillRect(0,0, panel_imagem.getWidth(), panel_imagem.getWidth());
        g.setColor(Color.BLACK);
        g.drawRect(1,1, panel_imagem.getWidth(), panel_imagem.getWidth());

        Object[] caminhos = cache.getAllKeys();
        g.drawImage(cache.getImagem((String)caminhos[list_imagens.getSelectedIndex()]),2,2,panel_imagem);
        panel_imagem.paintComponents(g);
    }



    public final void actionPerformed(ActionEvent e){
        if(e.getSource()==button_ok){
            instance.setVisible(false);
        }else if(e.getSource()==button_adicionar){
            //filechooser.setApproveButtonText(p.getProperty("file.open.name"));

            int returnVal = filechooser.showOpenDialog(this);

            if(returnVal == JFileChooser.APPROVE_OPTION) {

                File imagem = filechooser.getSelectedFile();

                    try{
                        FileInputStream in = new FileInputStream(imagem.getAbsolutePath());
                        byte[] arquivo = new byte[(int)imagem.length()];
                        in.read(arquivo);


                        FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")+File.separator+"vertices"+File.separator+imagem.getName());
                        out.write(arquivo);
                        out.flush();
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(this,ex.getMessage()+":"+ex.getCause());
                    }
                cache.addImagem(System.getProperty("user.dir")+File.separator+"vertices"+File.separator+imagem.getName());
                instance.montaLista();

            }

        }

    }






    public final void mouseReleased(MouseEvent e){
        this.desenhaImagem();

    }

     public final void mouseClicked(MouseEvent e){}
    public final void mousePressed(MouseEvent e){}
    public final void mouseDragged(MouseEvent e){}
    public final void mouseEntered(MouseEvent e){}
    public final void mouseExited(MouseEvent e){}


    public final void keyPressed(KeyEvent e){
    }

    public final void keyTyped(KeyEvent e){}
    public final void keyReleased(KeyEvent e){
     this.desenhaImagem();
    }
}











class RoxImageChooserLayout implements LayoutManager {

    public RoxImageChooserLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 440 + insets.left + insets.right;
        dim.height = 240 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+24,280,104);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+296,insets.top+24,120,104);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+0,112,24);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+128,170,24);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+168,insets.top+184,72,24);}
    }
}