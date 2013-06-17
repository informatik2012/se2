package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.beobachter;

import java.util.HashSet;
import java.util.Set;

public abstract class Beobachtbar
{
	protected Set<Beobachter> _beobachter;
	
	/**
	 * Methode fügt einen Beobachter zur Beobachterliste hinzu. 
	 * 
	 * @param b zu registrierender Beobachter
	 */
	public Beobachtbar()
	{
		_beobachter = new HashSet<Beobachter>();
	}
	
	/**
	 * Methode fügt einen Beobachter zur Beobachterliste hinzu. 
	 * 
	 * @param b zu registrierender Beobachter
	 */
	public void setzeBeobachter(Beobachter b)
	{
		_beobachter.add(b);
	}

	abstract public void meldeAenderung();
}
