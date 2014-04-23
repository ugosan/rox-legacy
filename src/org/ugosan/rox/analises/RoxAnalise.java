/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diretório-raiz em gpl.txt
*/
package org.ugosan.rox.analises;

import org.ugosan.rox.grafo.*;


/**
 * <p>
 * Esta interface deve ser implementada para que a sua análise seja reconhecida
 * e executada.
 * </p>
 * @author Ugo Braga Sangiorgi
 **/
public interface RoxAnalise {

/**
 * <p>
 * Execução da análise, é onde o algoritmo efetivamente reside.
 * </p><p>
 *
 * @return uma String ou null representando o resultado da análise
 * </p><p>
 * @param grafo um Grafo qualquer
 * </p>
 */
    public String execAnalise(Grafo grafo)throws Exception;
/**
 * @return uma String representando o nome da análise
 */

    public String getNome();

}