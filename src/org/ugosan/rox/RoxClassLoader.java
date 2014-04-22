/*
    Rox - Teoria dos Grafos
    Copyright (C) 2003  Ugo Braga Sangiorgi
    A licensa completa se encontra no diret�rio-raiz em gpl.txt
*/
package org.ugosan.rox;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.File;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;

/**
 *  Carregador de classes simples
 *  @author Ugo Braga Sangiorgi
 *  @author Algu�m na internet
 */
public class RoxClassLoader extends ClassLoader
{

  /**
   *  Construtor para inst�ncia relativa � outro ClassLoader pr�-existente.
   *
   */
  RoxClassLoader(ClassLoader parent)
  {
    super(parent);
    init();

  }

  /**
   *  Contrutor comum.
   *
   */
  RoxClassLoader()
  {
    super();
    init();
  }


  private void init()
  {
    FileInputStream fi = null;

    // Checa o manifesto
    try
    {
      fi = new FileInputStream("store\\MANIFEST.MF");
      manifest = new Manifest(fi);
    }
    catch (Exception e)
    {
      // Sem manifesto..
    }
    finally
    {
      if ( null != fi )
      {
        try
        {
          fi.close();
        }
        catch (Exception e){}
      }
    }
  }


   /**
   *  Este m�todo � o b�sico de carregamento.
   *
   * @param  name o nome da classe ( sem o .class )
   * @return objeto &lt;code&gt;Class&lt;/code&gt; resultante
   * @exception ClassNotFoundException se nada foi encontrado
   */
  protected Class findClass(String name) throws ClassNotFoundException
  {
    FileInputStream fi = null;

    try
    {

      //System.out.println("RoxClassLoader -> " + name);


      String path = name.replace('.', '/');
      fi = new FileInputStream("store/" + path + ".class");
      byte[] classBytes = new byte[fi.available()];
      fi.read(classBytes);
      definePackage(name);
      return defineClass(name, classBytes, 0, classBytes.length);

    }
    catch (Exception e)
    {

      throw new ClassNotFoundException(name);
    }
    finally
    {
      if ( null != fi )
      {
        try
        {
          fi.close();
        }
        catch (Exception e){}
      }
    }
  }
    protected void preparaClasse(String classe) {
        try{
        File arquivo = new File(classe);
        File imp = new File("store"+File.separator+classe);


        FileOutputStream fo = new FileOutputStream(imp);

        byte arrayBytes[] = new byte[(int)arquivo.length()];
        FileInputStream stream = new FileInputStream(arquivo);

        stream.read(arrayBytes);
        stream.close();

        fo.write(arrayBytes);
        fo.close();
        }catch(Exception e){
            e.printStackTrace();
        }
  }


    /**
   *  Identifica onde encontrar um recurso, que est�o, neste caso
   *  em "store"
   *
   *  @param name o nome do recurso
   *  @return URL para o recurso ou null se n�o encontrado
   */

  protected URL findResource(String name)
  {
    File searchResource = new File("store\\" + name);
    URL result = null;

    if ( searchResource.exists() )
    {
      try
      {
      return searchResource.toURL();
      }
      catch (MalformedURLException mfe)
      {
      }
    }

    return result;
  }

   /**
   *  Usado para identificar recursos de m�ltiplas URLS, mas
   * no caso deste ClassLoader, apenas um reposit�rio � usado.
   * O objeto Enumeration retornado cont�m apenas 0 ou 1 elementos.
   *
   *  @param name o nome do recurso
   *  @return Enumeration da URL
   */
  protected Enumeration findResources(final String name) throws IOException
  {
    return new Enumeration()
    {
      URL resource = findResource(name);
      public boolean hasMoreElements()
      {
        return ( resource != null ? true : false);
      }

      public Object nextElement()
      {
        if ( !hasMoreElements() )
        {
          throw new NoSuchElementException();
        }
        else
        {
          URL result = resource;
          resource = null;
          return result;
        }
      }
    };
  }

  /**
   *  Defini��o do package para a classe
   *
   */
  private void definePackage(String className)
  {
    // Extract the package name from the class name,
    String pkgName = className;
    int index = className.lastIndexOf('.');
    if (-1 != index)
    {
      pkgName =  className.substring(0, index);
    }


    if ( null == manifest ||
         getPackage(pkgName) != null)
    {
      return;
    }

    String specTitle,
           specVersion,
           specVendor,
           implTitle,
           implVersion,
           implVendor;


    Attributes attr = manifest.getMainAttributes();

    if ( null != attr)
    {
      specTitle   = attr.getValue(Name.SPECIFICATION_TITLE);
      specVersion = attr.getValue(Name.SPECIFICATION_VERSION);
      specVendor  = attr.getValue(Name.SPECIFICATION_VENDOR);
      implTitle   = attr.getValue(Name.IMPLEMENTATION_TITLE);
      implVersion = attr.getValue(Name.IMPLEMENTATION_VERSION);
      implVendor  = attr.getValue(Name.IMPLEMENTATION_VENDOR);

      definePackage(pkgName,
                    specTitle,
                    specVersion,
                    specVendor,
                    implTitle,
                    implVersion,
                    implVendor,
                    null);
    }
  }

  private Manifest manifest;
}