package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.LinkedList;
import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

/**
 * Mit Hilfe von Vormerkkarten werden beim Vormerken eines Mediums alle
 * relevanten Daten notiert.
 * 
 * Sie beantwortet die folgenden Fragen: Welches Medium wurde vorgemerkt? Wer
 * hat das Medium vorgemerkt? Wann wurde das Medium vorgemerkt?
 * 
 * Wenn es keine Vormerkkungen wird die Vormerkkarte entfernt. Um die Verwaltung
 * der Karten kümmert sich der VerleihService
 * 
 * @author Marco, Diana, Merve, Melanie
 * @version SoSe 2013
 */

public class Vormerkkarte
{

	// Eigenschaften einer Vormerkkarte
	private final Datum _vormerkdatum;
	private final List<Kunde> _vormerker;
	private final Medium _medium;
	private static final int MAX_NO_OF_KUNDE = 3;

	/**
	 * Initialisert eine neue Vormerkkarte mit den gegebenen Daten.
	 * 
	 * @param vormerker
	 *            Ein Kunde, der das Medium vormerken will.
	 * @param medium
	 *            Ein Medium.
	 * @param vormerkdatum
	 *            Ein Datum, an dem der Kunde das Medium vorgemerkt wird.
	 * 
	 * @require vormerker != null
	 * @require medium != null
	 * @require vormerkdatum != null
	 * 
	 * @ensure #getVormerker() == vormerker
	 * @ensure #getMedium() == medium
	 * @ensure #getVormerkdatum() == vormerkdatum
	 */
	public Vormerkkarte(Kunde vormerker, Medium medium, Datum vormerkdatum)
	{
		assert vormerker != null : "Vorbedingung verletzt: vormerker != null";
		assert medium != null : "Vorbedingung verletzt: medium != null";
		assert vormerkdatum != null : "Vorbedingung verletzt: vormerkdatum != null";

		_vormerker = new LinkedList<Kunde>();
		_vormerker.add(vormerker);
		_medium = medium;
		_vormerkdatum = vormerkdatum;
	}

	/**
	 * Fügt einen Vormerker zur Liste hinzu.
	 * 
	 * @param kunde
	 *            Ein Kunde, der das Medium vormerken möchte und nicht selber
	 *            Entleiher ist.
	 * 
	 * @require kunde != null
	 */
	public void addVormerker(Kunde kunde)
	{
		assert kunde != null : "Vorbedingung verletzt : kunde != null";

		;
		_vormerker.add(kunde);
	}

	/**
	 * Gibt das Vormerkdatum zurück.
	 * 
	 * @return Das Vormerkdatum.
	 * 
	 * @require _vormerkdatum != null
	 * 
	 * @ensure result != null
	 */
	public Datum getVormerkdatum()
	{
		assert _vormerkdatum != null : "Vorbedingung verletzt : _vormerkdatum != null";
		return _vormerkdatum;
	}

	/**
	 * Gibt den Vormerker zurück.
	 * 
	 * @return den Kunden, der das Medium entliehen hat.
	 * 
	 * @ensure result != nul
	 */
	public List<Kunde> getVormerker()
	{

		return _vormerker;
	}

	/**
	 * Prueft ob Kunde in der Vormerkkarte ist.
	 * 
	 * @param kunde
	 *            Ein Kunde
	 * 
	 * @return true, wenn der Kunde das Medium noch nicht entliehen hat sonst
	 *         false
	 * 
	 * @require kunde != null
	 */
	public boolean istKundeInVormerkkarte(Kunde kunde)
	{
		assert kunde != null : "Vorbedingung verletzt : kunde != null";

		return _vormerker.contains(kunde);
	}

	/**
	 * Gibt den ersten den Vormerker zurueck.
	 * 
	 * @require _vormerker.size()>0
	 * @return Der erste Vormerker
	 */
	public Kunde getErstenVormerker()
	{
		assert _vormerker.size() > 0 : "Vorbedingung verletzt: _vormerker.size()>0";
		return _vormerker.get(0);
	}

	/**
	 * Entfernt den ersten Vormerker und gibt die Anzahl der verbleibenen
	 * Kunden.
	 * 
	 * @return result != null
	 * @require _vormerker.size()>0
	 * 
	 */
	public int deleteErstenVormerker()
	{
		assert _vormerker.size() > 0 : "Vorbedingung verletzt: _vormerker.size()>0";
		_vormerker.remove(0);
		return _vormerker.size();
	}

	/**
	 * Gibt eine String-Darstellung der Verleihkarte (enhält Zeilenumbrüche)
	 * zurück.
	 * 
	 * @return Eine formatierte Stringrepäsentation der Verleihkarte. Enthält
	 *         Zeilenumbrüche.
	 * 
	 * @ensure result != null
	 */
	public String getFormatiertenString()
	{

		String s = _medium.getFormatiertenString() + "am "
				+ _vormerkdatum.toString() + " vorgemerkt an\n";
		boolean firstIteration = true;
		for (Kunde k : _vormerker)
		{
			s += (!firstIteration ? ",\n" : "") + k.getFormatiertenString();
			firstIteration = false;
		}
		return s;
	}

	/**
	 * Gibt das Medium, dessen Ausleihe auf der Karte vermerkt ist, zurück.
	 * 
	 * @return Das Medium, dessen Ausleihe auf dieser Karte vermerkt ist.
	 * 
	 * @ensure result != null
	 */
	public Medium getMedium()
	{
		return _medium;
	}

	/**
	 * Berechnet die Vormerkdauer in Tagen.
	 * 
	 * @return Die Vormerkdauer in Tagen.
	 * @ensure result != null
	 */
	public int getVormerkdauer()
	{
		return Datum.heute().tageSeit(getVormerkdatum()) + 1;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_vormerkdatum == null) ? 0 : _vormerkdatum.hashCode());
		result = prime * result
				+ ((_vormerker == null) ? 0 : _vormerker.hashCode());
		result = prime * result + ((_medium == null) ? 0 : _medium.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if (obj instanceof Vormerkkarte)
		{
			Vormerkkarte other = (Vormerkkarte) obj;

			if (other.getVormerkdatum().equals(_vormerkdatum)
					&& other.getVormerker().equals(_vormerker)
					&& other.getMedium().equals(_medium))

				result = true;
		}
		return result;
	}

	@Override
	public String toString()
	{
		return getFormatiertenString();
	}

	/**
	 * Gib zurück ob die Liste voll ist.
	 * 
	 * @return true wenn die Vormerkliste voll ist, sonst false
	 */
	public boolean istVoll()
	{
		return _vormerker.size() == Vormerkkarte.MAX_NO_OF_KUNDE;
	}

	/**
	 * Gibt zurück, ob die Liste leer ist.
	 * 
	 * @return true wenn die Vormerkliste leer ist sonst false
	 */
	public boolean istLeer()
	{
		return _vormerker.size() == 0;
	}

	/**
	 * Entfernt einen Vormerker aus der Liste der Vormerker
	 * 
	 * @param kunde
	 *            Ein Kunde
	 * 
	 * @require istKundeInVormerkkarte(kunde)
	 */
	public void deleteVormerker(Kunde kunde)
	{
		assert istKundeInVormerkkarte(kunde) : "Vorbedingung verletzt: istKundeInVormerkkarte(kunde) ";
		_vormerker.remove(kunde);
	}

}
