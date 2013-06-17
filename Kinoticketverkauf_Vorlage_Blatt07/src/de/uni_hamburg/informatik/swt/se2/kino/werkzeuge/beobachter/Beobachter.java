package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.beobachter;

public interface Beobachter
{
	/**
	 * Methode wird für jeden registrierten Beobachter aufgerufen,
	 * wenn eine andere Vorstellung ausgewählt worden ist.
	 */
	void beachteVorstellungsAenderung();

	/**
	 * Methode wird für jeden registrierten Beobachter aufgerufen,
	 * wenn ein anderes Datum ausgewählt worden ist.
	 */
	void beachteDatumsAenderung();
}
