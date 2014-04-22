/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/

package org.ugosan.rox;

import com.l2fprod.gui.plaf.skin.*;
import com.l2fprod.util.OS;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.imageio.*;

import org.ugosan.rox.grafo.*;
/**
*  Classe principal representando o núcleo do Rox.
* @author Ugo Braga Sangiorgi
**/
public class Rox extends JFrame implements ActionListener, MouseListener{
  static String versao = "Rox 0.74";
  static Properties p;      //propriedades gerais (dicionario) : <linguagem>.dic
  static Properties conf;   //configurações: rox.properties
  static PrintStream defaultPrintStream;

  private Map tools;
  private Map menus;

  private JMenuBar bar;

  JMenu menuGrafo;
  JMenu menuAcoes;
  JMenu menuAnalises;
  JMenu menuOpcoes;
  JMenu menuSobre;

  JMenuItem itemNovoGrafo;
  JMenuItem itemNovoDigrafo;

  JMenuItem itemRandomizar;
  JMenuItem itemGrafoAbrir;
  JMenuItem itemGrafoSalvar;

  //checkboxes acessados acessados no construtor de RoxFrame, simultaneo ao
  //construtor principal :/
  protected static JCheckBox itemOpcoesMatrizes;
  protected static JCheckBox itemOpcoesTDQ;
  protected static JCheckBox itemOpcoesOtimizacao;

  private JMenuItem itemSai;

  private JMenuItem analise;

  private JToolBar toolbar;
  private JToolBar toolbar_matrizAdjacencia;
  private JButton tool_vertice_add;
  private JButton tool_vertice_del;
  private JButton tool_aresta_add;
  private JButton tool_aresta_del;
  private JButton tool_mover;
  private JButton tool_random;
  private JButton tool_ultimaAnalise;
  private JButton tool_colorir_vertice;
  private JButton tool_colorir_aresta;
  private JButton tool_colorir_apaga;

  private JColorChooser colorchooser;

  Cursor cursor_colorir_vertice;
  Cursor cursor_colorir_aresta;
  Cursor cursor_colorir_apaga;
  Cursor cursor_add_vertice;
  Cursor cursor_add_aresta;
  Cursor cursor_del_vertice;
  Cursor cursor_del_aresta;
  Cursor cursor_mover;
  Cursor cursor_default;

  private JTabbedPane tabbedpane;

  private JMenuItem itemSobre;
  private JTextField tips;
  private JTextField tdq;

  private JDesktopPane area;
  private JFileChooser filechooser;
  private JOptionPane optionPane;

  private static Rox instance;
  private RoxMatrizPanel roxMatrizPanel;

  RoxFrame roxframe;
  private RoxLogFrame logframe;

  private RoxAnaliseLoader loader;
  private RoxAnaliseExecutor executor;

  private boolean waitState;

  Grafo grafo;
  static int CRIAR_VERTICE = 1;
  static int APAGAR_VERTICE = 2;
  static int CRIAR_ARESTA = 3;
  static int APAGAR_ARESTA = 4;
  static int MOVER = 5;
  static int COLORIR_VERTICE = 6;
  static int COLORIR_ARESTA = 7;
  static int SELECIONAR_VERTICES = 8;

  static int acao_selecionada = 5;
  /* acao_selecionada:
    1-cria vertice
    2-deleta vertice
    3-cria aresta
    4-deleta aresta
    5-move
    6-colore vertice
    7-colore aresta
  */

  static Color cor_selecionada;

  boolean skin;

  static{
    try{
        p = new Properties();
        conf = new Properties();
        conf.load(new FileInputStream("rox.properties"));

        defaultPrintStream = System.out;
    }catch(Exception e){
        System.out.println("Impossivel carregar configurações / Cannot load config files");
        e.printStackTrace();
        System.exit(1);
    }

  }

    public void setLingua(Locale l){
        String locale = l.toString();
        String lingua = "";

        try{
            //tenta pegar o locale do jeito que veio..
            p.load(new FileInputStream(conf.getProperty("locale."+locale)));
        }catch(Exception e){
            //nao encontrou.. tenta na forma bruta da linguagem
            if(locale.indexOf("_") == -1) lingua = locale;
            else lingua = locale.substring(0,locale.indexOf("_"));
            try{
                p.load(new FileInputStream(conf.getProperty("locale."+lingua)));
            }catch(Exception ex){
                //tambem nao encontrou..
                try{
                    p.load(new FileInputStream(conf.getProperty("locale.otherwise")));
                }catch(Exception exc){
                    //paciencia...
                    System.out.println("Impossivel carregar arquivo de linguagem / Cannot load locale dictionary");
                    exc.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }

    private Rox()throws Exception{

             this.setLingua(Locale.getDefault());

        if(conf.getProperty("skin").equals("0")) skin = false;
        else skin = true;

        Toolkit toolk = Toolkit.getDefaultToolkit();

        try{
            this.setSize(Integer.valueOf(conf.getProperty("preferredsize.width")).intValue(),
                         Integer.valueOf(conf.getProperty("preferredsize.height")).intValue());
        }catch(Exception e){
            this.setSize(800,540);

        }



        RoxSplash roxsplash = new RoxSplash(this, p.getProperty("splash.text.0"));
        try{
            if(skin)
                SkinLookAndFeel.setSkin(SkinLookAndFeel.loadThemePack(System.getProperty("user.dir")+File.separator+"themepack.zip"));
        }catch(Exception e){
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            skin = false;
        }

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(("img"+File.separator+"icone.gif")));

        this.getContentPane().setLayout(new BorderLayout());



        roxsplash.setTexto(p.getProperty("splash.text.10"));
        roxsplash.setProgresso(10);
        bar = new JMenuBar();
  	    menuGrafo = new JMenu(p.getProperty("menu.1"));
        menuAcoes = new JMenu("Acoes");
        //menuAnalises = new JMenu(p.getProperty("menu.2"));
        //em RoxAnaliseLoader

        menuOpcoes = new JMenu(p.getProperty("menu.3"));
        menuSobre = new JMenu(p.getProperty("menu.4"));

        itemNovoGrafo = new JMenuItem(p.getProperty("menu.1.1"));
        itemNovoDigrafo = new JMenuItem(p.getProperty("menu.1.2"));
        //itemNovoDigrafo.setEnabled(false);
        itemGrafoAbrir = new JMenuItem(p.getProperty("menu.1.3"));
        itemGrafoSalvar = new JMenuItem(p.getProperty("menu.1.4"));

        itemRandomizar = new JMenuItem(p.getProperty("menu.1.5"));
        itemSai = new JMenuItem(p.getProperty("menu.1.6"));

        itemOpcoesMatrizes = new JCheckBox(p.getProperty("menu.3.1"),false);
        itemOpcoesTDQ = new JCheckBox(p.getProperty("menu.3.2"),false);
        itemOpcoesOtimizacao = new JCheckBox(p.getProperty("menu.3.3"),false);


        itemSobre = new JMenuItem(p.getProperty("menu.4.1"));


        menuGrafo.add(itemNovoGrafo);
        menuGrafo.add(itemNovoDigrafo);
        menuGrafo.addSeparator();
        menuGrafo.add(itemGrafoAbrir);
        menuGrafo.add(itemGrafoSalvar);
        menuGrafo.addSeparator();
        menuGrafo.add(itemRandomizar);
        menuGrafo.addSeparator();
        menuGrafo.add(itemSai);

        menuOpcoes.add(itemOpcoesMatrizes);
        menuOpcoes.add(itemOpcoesTDQ);
        menuOpcoes.add(itemOpcoesOtimizacao);




        roxsplash.setTexto(p.getProperty("splash.text.20"));
        roxsplash.setProgresso(20);



        executor = new RoxAnaliseExecutor();

        loader = RoxAnaliseLoader.getInstance();
        this.menuAnalises = loader.getMenuAnalises();
        this.menuAnalises.addMouseListener(this);

        roxsplash.setProgresso(35);

        menuSobre.add(itemSobre);

        bar.add(menuGrafo);


        bar.add(menuAnalises);
        bar.add(menuOpcoes);
        bar.add(menuSobre);

        this.setJMenuBar(bar);

        tips = new JTextField();
        tips.setEditable(false);
        tips.setFocusable(false);


        roxsplash.setTexto(p.getProperty("splash.text.30"));




        tool_vertice_add = new JButton(new ImageIcon("img"+File.separator+"icone_vertice_add.gif"));
        tool_vertice_del = new JButton(new ImageIcon("img"+File.separator+"icone_vertice_del.gif"));
        tool_aresta_add = new JButton(new ImageIcon("img"+File.separator+"icone_aresta_add.gif"));
        tool_aresta_del = new JButton(new ImageIcon("img"+File.separator+"icone_aresta_del.gif"));
        tool_mover = new JButton(new ImageIcon("img"+File.separator+"icone_mover.gif"));
        tool_random = new JButton(new ImageIcon("img"+File.separator+"icone_random.gif"));
        tool_ultimaAnalise = new JButton(new ImageIcon("img"+File.separator+"icone_ultimaAnalise.gif"));
        tool_colorir_vertice = new JButton(new ImageIcon("img"+File.separator+"icone_colorir_vertice.gif"));
        tool_colorir_aresta = new JButton(new ImageIcon("img"+File.separator+"icone_colorir_aresta.gif"));
        tool_colorir_apaga = new JButton(new ImageIcon("img"+File.separator+"icone_colorir_apaga.gif"));


        tool_vertice_add.setToolTipText(p.getProperty("msg.1"));
        tool_vertice_del.setToolTipText(p.getProperty("msg.2"));
        tool_aresta_add.setToolTipText(p.getProperty("msg.3"));
        tool_aresta_del.setToolTipText(p.getProperty("msg.4"));
        tool_mover.setToolTipText(p.getProperty("msg.5"));
        tool_random.setToolTipText(p.getProperty("msg.6"));
        tool_ultimaAnalise.setToolTipText(p.getProperty("msg.7"));
        tool_colorir_vertice.setToolTipText(p.getProperty("msg.8"));
        tool_colorir_aresta.setToolTipText(p.getProperty("msg.9"));
        tool_colorir_apaga.setToolTipText(p.getProperty("msg.10"));


        roxsplash.setProgresso(30);
        roxsplash.setTexto(p.getProperty("splash.text.40"));

        tool_vertice_add.addActionListener(this);
        tool_vertice_del.addActionListener(this);
        tool_aresta_add.addActionListener(this);
        tool_aresta_del.addActionListener(this);
        tool_mover.addActionListener(this);
        tool_random.addActionListener(this);
        tool_ultimaAnalise.addActionListener(this);
        tool_colorir_vertice.addActionListener(this);
        tool_colorir_aresta.addActionListener(this);
        tool_colorir_apaga.addActionListener(this);

        roxsplash.setProgresso(40);
        tool_vertice_add.addMouseListener(this);
        tool_vertice_del.addMouseListener(this);
        tool_aresta_add.addMouseListener(this);
        tool_aresta_del.addMouseListener(this);
        tool_mover.addMouseListener(this);
        tool_random.addMouseListener(this);
        tool_ultimaAnalise.addMouseListener(this);
        tool_colorir_vertice.addMouseListener(this);
        tool_colorir_aresta.addMouseListener(this);
        tool_colorir_apaga.addMouseListener(this);

        roxsplash.setTexto(p.getProperty("splash.text.50"));

        roxsplash.setProgresso(50);

        tools = new TreeMap();


        //mapeia as mensagens ao usuario com base no Hash do objeto
        tools.put(new Integer(tool_vertice_add.hashCode()),p.getProperty("msg.1"));
        tools.put(new Integer(tool_vertice_del.hashCode()),p.getProperty("msg.2"));
        tools.put(new Integer(tool_aresta_add.hashCode()),p.getProperty("msg.3"));
        tools.put(new Integer(tool_aresta_del.hashCode()),p.getProperty("msg.4"));
        tools.put(new Integer(tool_mover.hashCode()),p.getProperty("msg.5"));
        tools.put(new Integer(tool_random.hashCode()),p.getProperty("msg.6"));
        tools.put(new Integer(tool_ultimaAnalise.hashCode()),p.getProperty("msg.7"));
        tools.put(new Integer(tool_colorir_vertice.hashCode()),p.getProperty("msg.8"));
        tools.put(new Integer(tool_colorir_aresta.hashCode()),p.getProperty("msg.9"));
        tools.put(new Integer(tool_colorir_apaga.hashCode()),p.getProperty("msg.10"));


        roxsplash.setTexto(p.getProperty("splash.text.60"));
        roxsplash.setProgresso(60);

        String[] icones = new File(System.getProperty("user.dir")+File.separator+"vertices"+File.separator).list();
        for(int i=0;i<icones.length;i++){
            icones[i] = System.getProperty("user.dir")+File.separator+"vertices"+File.separator +icones[i];
        }
        RoxImageCache.getInstance().addAll(icones, true);


        colorchooser = new JColorChooser();


        filechooser = new JFileChooser();
        RoxFileFilter filter = new RoxFileFilter("rox","Grafos Rox");
        filechooser.setFileFilter(filter);

        filechooser.setSelectedFile(new File(System.getProperty("user.dir")+File.separator+"."));


        roxsplash.setProgresso(70);
        toolbar= new JToolBar(p.getProperty("toolbar.name"));

        toolbar.add(tool_vertice_add);
        toolbar.add(tool_vertice_del);
        toolbar.addSeparator();
        toolbar.add(tool_aresta_add);
        toolbar.add(tool_aresta_del);
        toolbar.addSeparator();
        toolbar.add(tool_mover);
        toolbar.add(tool_random);
        toolbar.addSeparator();
        toolbar.add(tool_ultimaAnalise);
        toolbar.addSeparator();
        toolbar.add(tool_colorir_vertice);
        toolbar.add(tool_colorir_aresta);
        toolbar.add(tool_colorir_apaga);

        getContentPane().add(toolbar, BorderLayout.NORTH);

        tips.setBackground(new Color(220,220,220));
        getContentPane().add(tips, BorderLayout.PAGE_END);

        roxsplash.setTexto(p.getProperty("splash.text.110"));
        roxsplash.setProgresso(80);

        itemNovoGrafo.addActionListener(this);
        itemNovoDigrafo.addActionListener(this);

        itemRandomizar.addActionListener(this);
        itemGrafoAbrir.addActionListener(this);
        itemGrafoSalvar.addActionListener(this);

        itemSai.addActionListener(this);
        itemOpcoesMatrizes.addActionListener(this);
        itemOpcoesTDQ.addActionListener(this);
        itemOpcoesOtimizacao.addActionListener(this);

        itemSobre.addActionListener(this);
        itemSai.addActionListener(this);


        roxframe = new RoxFrame();

        roxsplash.setProgresso(90);
        roxsplash.setTexto(p.getProperty("splash.text.120"));

        if(OS.isWindows()){
            cursor_add_vertice = toolk.createCustomCursor(toolk.createImage("img"+File.separator+"cursor_add_vertice.gif"),new Point(0,0),"cursor_add_vertice");
            cursor_del_vertice = toolk.createCustomCursor(toolk.createImage("img"+File.separator+"cursor_del_vertice.gif"),new Point(0,0),"cursor_del_vertice");

            cursor_add_aresta = toolk.createCustomCursor(toolk.createImage("img"+File.separator+"cursor_add_aresta.gif"),new Point(0,0),"cursor_add_aresta");
            cursor_del_aresta = toolk.createCustomCursor(toolk.createImage("img"+File.separator+"cursor_del_aresta.gif"),new Point(0,0),"cursor_del_aresta");

            cursor_colorir_vertice = toolk.createCustomCursor(toolk.createImage("img"+File.separator+"cursor_colorir_vertice.gif"),new Point(5,15),"cursor_colorir_vertice");
            cursor_colorir_aresta = toolk.createCustomCursor(toolk.createImage("img"+File.separator+"cursor_colorir_aresta.gif"),new Point(5,15),"cursor_colorir_aresta");

            cursor_mover = toolk.createCustomCursor(toolk.createImage("img"+File.separator+"cursor_mover.gif"),new Point(10,10),"cursor_mover");
            cursor_default = toolk.createCustomCursor(toolk.createImage("img"+File.separator+"cursor_default.gif"),new Point(0,0),"cursor_default");
        }

        roxMatrizPanel = new RoxMatrizPanel();
        logframe = new RoxLogFrame();



        tabbedpane = new JTabbedPane(JTabbedPane.LEFT);

        tabbedpane.add(p.getProperty("tabbedpane.1"),roxframe);
        tabbedpane.add(p.getProperty("tabbedpane.2"),roxMatrizPanel);
        tabbedpane.add(p.getProperty("tabbedpane.3"),logframe);



        getContentPane().add(tabbedpane);

        optionPane = new JOptionPane();



        this.novoGrafo();

        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //Rox.getInstance().destroy();
                System.exit(0);
            }
        });


        this.setCursor(cursor_default);
        this.roxframe.setRoxPanelCursor(cursor_mover);
        this.roxframe.getRoxPanel().ativarOtimizacao(itemOpcoesOtimizacao.isSelected());

        if(skin)
            this.habilitaSkin();

        roxsplash.setProgresso(100);

        this.selectBotaoToolBar(5);
        this.msg(p.getProperty("msg.0"));

        roxsplash.dispose();

        filechooser.updateUI();

        RoxImageCache cache = RoxImageCache.getInstance();
        cache.atualizarImagens();



    }


    public final void destroy(){
        try{
            String[] arquivos = new File(System.getProperty("user.dir")+File.separator+"store").list();
            for (int j = 0; j < arquivos.length; j++){
                File f = new File(arquivos[j]);
                f.delete();
            }

        }catch(Exception e){
            System.setOut(Rox.defaultPrintStream);
            System.out.println(Rox.p.getProperty("out.failed"));

        }

    }

    public void setWaitState(boolean wait){
        this.waitState = wait;
        roxframe.getRoxPanel().setWaitState(wait);
        this.selectBotaoToolBar(5);


    }


    private void novoGrafo(){

        this.grafo = new Grafo();
        this.roxframe.reset();
        System.gc();
        this.roxframe.setTitle(Rox.p.getProperty("graph"));
    }
    private void novoDigrafo(){
        this.grafo = new Digrafo();
        this.roxframe.reset();
        System.gc();
        this.roxframe.setTitle(Rox.p.getProperty("digraph"));
    }

    /**Retorna a instancia da aplicação, o Rox não pode ser instanciado a
     * nao ser pelo seu próprio método main(). Dessa forma, para que os métodos
     * públicos possam ser usados, deve se usar, por exemplo:
     * <code>Rox.getInstance().getInput("Informe alguma coisa");</code>
     * @return a instancia do Rox
     **/
    public static Rox getInstance(){

        if (instance==null){
            try{
                instance = new Rox();
            }catch(Exception e){
                e.printStackTrace(System.out);
            }
        }

    return(instance);
    }

    public JMenu getMenuAnalises(){
    return(this.menuAnalises);
    }

    public Grafo getGrafo(){
    return(this.grafo);
    }
    public Properties getProperties(){
    return p;
    }

    public RoxAnaliseExecutor getRoxAnaliseExecutor(){
    return executor;
    }

    /**Seleciona uma orelha do Tabbed Pane
     * @param indice o índice da orelha, de 0..*
     **/
    public void selectTabpane(int indice){
        this.tabbedpane.setSelectedIndex(indice);
    }

    /**Mostra uma mensagem na barra de mensagens abaixo do painel
     * @param msg uma String
     **/
    public void msg(String msg){
        tips.setText(msg);
    }

    public final void actionPerformed(ActionEvent Evento){
        Object Source = Evento.getSource();
        if(!waitState){
        if((Source == itemRandomizar)||(Source== tool_random)){
            Randomizador rand = new Randomizador();

            this.grafo = rand.getNovoGrafo(30);
            this.roxframe.reset();

        }
        else if(Source == itemNovoGrafo){
            this.novoGrafo();
        }
        else if(Source == itemNovoDigrafo){
            this.novoDigrafo();
        }

        else if(Source == itemOpcoesMatrizes){
            this.roxframe.mostrarMatrizes = itemOpcoesMatrizes.isSelected();
            this.roxframe.repaint();
        }
        else if(Source == itemOpcoesTDQ){
            this.roxframe.mostrarTDQ = itemOpcoesTDQ.isSelected();
            this.roxframe.repaint();
        }
        else if(Source == itemOpcoesOtimizacao){
            this.roxframe.ativarOtimizacao = itemOpcoesOtimizacao.isSelected();
            this.roxframe.getRoxPanel().ativarOtimizacao(itemOpcoesOtimizacao.isSelected());

        }

        else if(Source == tool_vertice_add){

            this.selectBotaoToolBar(1);
            this.msg(p.getProperty("msg.help.1"));

        }
        else if(Source == tool_vertice_del){

            this.selectBotaoToolBar(2);
            this.msg(p.getProperty("msg.help.2"));

        }
        else if(Source == tool_aresta_add){

            this.selectBotaoToolBar(3);
            this.msg(p.getProperty("msg.help.3"));

        }
        else if(Source == tool_aresta_del){

            this.selectBotaoToolBar(4);
            this.msg(p.getProperty("msg.help.3"));

        }else if(Source == tool_mover){

            this.selectBotaoToolBar(5);
            this.msg(p.getProperty("msg.help.3"));

        }
        else if(Source == tool_colorir_vertice){
            this.selectBotaoToolBar(6);

            Color cor = colorchooser.showDialog(this,"Cor do Vértice",Color.LIGHT_GRAY);
            if(cor != null) cor_selecionada = cor;


            this.msg(p.getProperty("msg.help.4"));

        }else if(Source == tool_colorir_aresta){
            this.selectBotaoToolBar(7);

            Color cor = colorchooser.showDialog(this,"Cor da Aresta",Color.LIGHT_GRAY);
            if(cor != null) cor_selecionada = cor;


            this.msg(p.getProperty("msg.help.3"));
        }else if(Source == tool_colorir_apaga){
            grafo.resetAcesos();
            this.repaint();
        }

        else if(Source == tool_ultimaAnalise){
            executor.execUltimaAnalise();


        }else if(Source == itemGrafoAbrir){
            filechooser.setApproveButtonText(p.getProperty("file.open.name"));
            filechooser.setDialogTitle(p.getProperty("file.open.title"));

            int returnVal = filechooser.showOpenDialog(this);

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                this.grafo = this.abreGrafo(filechooser.getSelectedFile().getName());
                this.roxframe.repaint();
            }

        }else if(Source == itemGrafoSalvar){
            filechooser.setApproveButtonText(p.getProperty("file.save.name"));
            filechooser.setDialogTitle(p.getProperty("file.save.title"));
            int returnVal = filechooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                this.salvaGrafo(filechooser.getSelectedFile().getName());
                //this.roxframe.repaint();
            }

        }else if(Source == itemSobre){
            RoxSobreDialog dialog = new RoxSobreDialog();
            dialog.pack();
            dialog.show();
            //JOptionPane.showMessageDialog(this,versao+p.getProperty("about.text"),p.getProperty("about.title"),1,new ImageIcon("img"+File.separator+"Rox_Sobre.jpg"));
        }
        else if(Source == itemSai){
            //this.destroy();
            System.exit(0);

        }
        }else{
            if(Source == itemSai) System.exit(0);

        }
    }


    public final void mouseEntered(MouseEvent me){
        //pega a mensagem relativa ao objeto dentro do mapeamento com base no hash do objeto
        if(me.getSource()!= menuAnalises)
            msg((String)tools.get(new Integer(me.getSource().hashCode())));
        else{
            loader.reload();
        }
    }
    public final void mouseExited(MouseEvent me){}
    public final void mouseMoved(MouseEvent me){}
    public final void mouseDragged(MouseEvent me){}
    public final void mouseReleased(MouseEvent me){}
    public final void mousePressed(MouseEvent me){}
    public final void mouseClicked(MouseEvent me){}

    /**Facilitador de entrada de Usuário, use quando você precisar obter alguma informação do usuário, como seleção
     * específica de um vértice através de seu nome.
     * <p>
     * Ex: <br> <code>
     * int num;
     * String s;
     *
     * s = (String)Rox.getInstance().getInput("Informe um numero de um vertice");
     * num = Integer.valueOf(s).intValue();
     *
     * </code>
     * @param msg uma String
     **/
    public final Object getInput(String msg){
    return(optionPane.showInputDialog(this,msg,p.getProperty("input.title"),1));
    }
    /**Facilitador de entrada de Usuário, use quando você precisar obter alguma informação do usuário, como seleção
     * específica de vértices. passe um vetor de objetos
     * <p>
     *
     * Ex: <br> <code>
     * String s2="";
     * while(s2!=null){
            s2 = (String)Rox.getInstance().getInput("Escolha um vertice, ou cancele para terminar",grafo.getNomesVertices().toArray());
            if(s2!=null) verticesSelecionados.add(grafo.getVerticeByNome(s2));
        } </code>

     * @param msg uma String
     * @param vetor o vetor de Objetos (Strings de preferência)
     **/
    public final Object getInput(String msg,Object[] vetor){
    return(optionPane.showInputDialog(this,msg,p.getProperty("input.title"),1,null,vetor,vetor[0]));
    }

    /**Manda um texto para o log
     **/
    public void loga(String _log){
        logframe.add(_log);


    }

    /**Manda um texto para o log
     **/
    public void loga(String nomeAnalise, String resultado){
        logframe.addResultadoAnalise(nomeAnalise,resultado);
    }

    private void salvaGrafo(String nomeArquivo){
    try{
      ObjectOutputStream out;
      if(nomeArquivo.endsWith(".rox")){
        out = new ObjectOutputStream(new FileOutputStream(nomeArquivo));
      }else{
        out = new ObjectOutputStream(new FileOutputStream(nomeArquivo+".rox"));
      }
      out.writeObject(this.grafo);
      out.close();
    }catch(Exception e){
      JOptionPane.showMessageDialog(this,p.getProperty("error.filewrite"),e.toString(),2);
      e.printStackTrace();
    }
  }

  private Grafo abreGrafo(String nomeArquivo){
    try{
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo));
      return((Grafo)in.readObject());
    }catch(Exception e){
        JOptionPane.showMessageDialog(this,p.getProperty("error.fileread"),e.toString(),2);
         e.printStackTrace();
      }
   return(new Grafo());
    }

    private void selectBotaoToolBar(int numBotao){
        acao_selecionada = numBotao;
        tool_vertice_add.setEnabled(true);
        tool_vertice_del.setEnabled(true);
        tool_aresta_add.setEnabled(true);
        tool_aresta_del.setEnabled(true);
        tool_mover.setEnabled(true);
        tool_colorir_vertice.setEnabled(true);
        tool_colorir_aresta.setEnabled(true);


        if(numBotao==1){
            tool_vertice_add.setEnabled(false);
            if(OS.isWindows()) roxframe.setRoxPanelCursor(cursor_add_vertice);
        }else if(numBotao==2){
            tool_vertice_del.setEnabled(false);
            if(OS.isWindows()) roxframe.setRoxPanelCursor(cursor_del_vertice);
        }else if(numBotao==3){
            tool_aresta_add.setEnabled(false);
            if(OS.isWindows()) roxframe.setRoxPanelCursor(cursor_add_aresta);
        }else if(numBotao==4){
            tool_aresta_del.setEnabled(false);
            if(OS.isWindows()) roxframe.setRoxPanelCursor(cursor_del_aresta);
        }else if(numBotao==5){
            tool_mover.setEnabled(false);
            if(OS.isWindows()) roxframe.setRoxPanelCursor(cursor_mover);
        }else if(numBotao==6){
            if(OS.isWindows()) roxframe.setRoxPanelCursor(cursor_colorir_vertice);
        }else if(numBotao==7){
            if(OS.isWindows()) roxframe.setRoxPanelCursor(cursor_colorir_aresta);
        }


    }
    private final void habilitaSkin(){
        try{
        SkinLookAndFeel.enable();
        SwingUtilities.updateComponentTreeUI(this);

        }catch(UnsupportedLookAndFeelException e){}
    }


    public final void reloadAnalises(){
        try{
            loader.reload();
        }catch(Exception e){
            System.out.println("Impossivel Re-carregar Análises");
        }
    }

    public final void repaint(){
        this.roxframe.getRoxPanel().repaint();
    }

    public final long getTDQ(){
        return(roxframe.getRoxPanel().tdq);
    }

    public static void main(String[] args){
        Rox rox = Rox.getInstance();
        rox.setTitle(Rox.versao);
        rox.setVisible(true);

      
    }
}