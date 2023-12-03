package model.data_structures;

import java.util.Comparator;

public class Vertex<K extends Comparable<K>,V  extends Comparable <V>> implements Comparable<Vertex<K, V>>
{
	private K key;
	private V value;
	private ILista<Edge<K, V>> arcos;
	private boolean marked;
	
	public Vertex(K id, V value)
	{
		this.key=id;
		this.value=value;
		this.arcos= new ArregloDinamico<Edge<K, V>>(1);
	}

	public K getId()
	{
		return key;
	}
	
	public V getInfo()
	{
		return value;
	}
	
	public boolean getMark()
	{
		return marked;
	}
	
	public void mark()
	{
		marked=true;
	}
	
	public void unmark()
	{
		marked=false;
	}
	
	public int outdegree()
	{
		return arcos.size();
	}
	
	public int indegree() 
	{
		return arcos.size();
	}
	
	@Override
	public int compareTo(Vertex<K, V> o) 
	{
		return key.compareTo(o.getId());
	}
	
	 public static class ComparadorXKey implements Comparator<Vertex<String, Landing>>
	 {

		/** Comparador alterno de acuerdo al número de likes
		* @return valor 0 si video1 y video2 tiene los mismos likes.
		 valor negativo si video1 tiene menos likes que video2.
		 valor positivo si video1 tiene más likes que video2. */
		 public int compare(Vertex vertice1, Vertex vertice2) 
		 {
			 return ((String)vertice1.getId()).compareToIgnoreCase((String) vertice2.getId());
		 }

	}
}
