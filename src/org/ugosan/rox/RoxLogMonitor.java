/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diret�rio-raiz em gpl.txt
*/

package org.ugosan.rox;


import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
/**
*    RoxLogMonitor monitora a atividade das sa�das padr�o (System.out e System.err)
*    e atribui leitores � ela. Suas sa�das ent�o s�o direcionadas ao JTextArea
*    em RoxLogFrame atraves de rox.loga();
* @author Ugo Braga Sangiorgi
**/
public class RoxLogMonitor implements Runnable
{

	private Thread reader;
	private Thread reader2;

	private final PipedInputStream pin=new PipedInputStream();
	private final PipedInputStream pin2=new PipedInputStream();


    private Rox rox;

	public RoxLogMonitor()
	{
		rox = Rox.getInstance();
        try
		{
			PipedOutputStream pout=new PipedOutputStream(this.pin);
			System.setOut(new PrintStream(pout,true));
			PipedOutputStream pout2=new PipedOutputStream(this.pin2);
			System.setErr(new PrintStream(pout2,true));
		}
		catch (Exception e)
		{
			rox.loga("Erro: N�o foi possivel atribuir a saida ao Log");
			e.printStackTrace();

		}

		reader=new Thread(this);
		reader.setDaemon(true);
		reader.start();

		reader2=new Thread(this);
		reader2.setDaemon(true);
		reader2.start();


	}

	public synchronized void run()
	{

        try
		{
			while (Thread.currentThread()==reader)
			{
				try { this.wait(10);}catch(InterruptedException ie) {}
				if (pin.available()!=0)
				{

                    rox.loga(this.readLine(pin));

				}

			}

			while (Thread.currentThread()==reader2)
			{
				try { this.wait(10);}catch(InterruptedException ie) {}
				if (pin2.available()!=0)
				{
					rox.loga(this.readLine(pin2));
				}

			}
		} catch (Exception e)
		{
			rox.loga("Erro: "+e);
		}



	}

	public synchronized String readLine(PipedInputStream in) throws IOException
	{
		String input="";
		do
		{
			int available=in.available();
			if (available==0) break;
			byte b[]=new byte[available];
			in.read(b);
			input=input+new String(b,0,b.length);
		}while( !input.endsWith("\n") &&  !input.endsWith("\r\n"));
		return input;
	}

}