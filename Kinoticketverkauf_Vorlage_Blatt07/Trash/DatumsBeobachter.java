package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.beobachter;

import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.kasse.KassenWerkzeug;

public class DatumsBeobachter implements Beobachter
{
	private KassenWerkzeug _k;
	
	public DatumsBeobachter(KassenWerkzeug k)
	{
		_k = k;
	}
	
	@Override
	public void beachteAenderung()
	{
		_k.setzeTagesplanFuerAusgewaehltesDatum();
	}

}
