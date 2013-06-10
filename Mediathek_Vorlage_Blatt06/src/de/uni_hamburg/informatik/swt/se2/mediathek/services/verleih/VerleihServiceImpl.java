package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Verleihkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Vormerkkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.AbstractObservableService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;

/**
 * Diese Klasse implementiert das Interface VerleihService. Siehe dortiger
 * Kommentar.
 * 
 * @author SE2-Team
 * @version SoSe 2013
 */
public class VerleihServiceImpl extends AbstractObservableService implements
		VerleihService
{
	/**
	 * Die Menge mit den Verleihkarten.
	 */
	private Map<Medium, Verleihkarte> _verleihkarten;

	/**
	 * Die Menge mit den Vormerkkarten.
	 */
	private Map<Medium, Vormerkkarte> _vormerkkarten;

	/**
	 * Der Medienbestand.
	 */
	private MedienbestandService _medienbestand;

	/**
	 * Der Kundenstamm.
	 */
	private KundenstammService _kundenstamm;

	/**
	 * Der Protokollierer für die Verleihvorgänge.
	 */
	private VerleihProtokollierer _protokollierer;

	/**
	 * Konstruktor. Erzeugt einen neuen VerleihServiceImpl.
	 * 
	 * @param kundenstamm
	 *            Der KundenstammService.
	 * @param medienbestand
	 *            Der MedienbestandService.
	 * @param initialBestand
	 *            Der initiale Bestand.
	 * 
	 * @require kundenstamm != null
	 * @require medienbestand != null
	 * @require initialBestand != null
	 */
	public VerleihServiceImpl(KundenstammService kundenstamm,
			MedienbestandService medienbestand,
			List<Verleihkarte> initialBestand)
	{
		assert kundenstamm != null : "Vorbedingung verletzt: kundenstamm  != null";
		assert medienbestand != null : "Vorbedingung verletzt: medienbestand  != null";
		assert initialBestand != null : "Vorbedingung verletzt: initialBestand  != null";
		_verleihkarten = erzeugeVerleihkartenBestand(initialBestand);
		_vormerkkarten = new HashMap<Medium, Vormerkkarte>();
		_kundenstamm = kundenstamm;
		_medienbestand = medienbestand;
		_protokollierer = new VerleihProtokollierer();
	}

	/**
	 * Erzeugt eine neue HashMap aus dem Initialbestand.
	 */
	private HashMap<Medium, Verleihkarte> erzeugeVerleihkartenBestand(
			List<Verleihkarte> initialBestand)
	{
		HashMap<Medium, Verleihkarte> result = new HashMap<Medium, Verleihkarte>();
		for (Verleihkarte verleihkarte : initialBestand)
		{
			result.put(verleihkarte.getMedium(), verleihkarte);
		}
		return result;
	}

	@Override
	public List<Verleihkarte> getVerleihkarten()
	{
		return new ArrayList<Verleihkarte>(_verleihkarten.values());
	}

	@Override
	public List<Vormerkkarte> getVormerkkarten()
	{
		return new ArrayList<Vormerkkarte>(_vormerkkarten.values());
	}

	@Override
	public boolean istVerliehen(Medium medium)
	{
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumExistiert(medium)";
		return _verleihkarten.containsKey(medium);
	}

	@Override
	public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien)
	{
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

		return sindAlleNichtVerliehen(medien);
	}

	@Override
	public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum)
			throws ProtokollierException
	{
		assert sindAlleVerliehen(medien) : "Vorbedingung verletzt: sindVerliehen(medien)";
		assert rueckgabeDatum != null : "Vorbedingung verletzt: rueckgabeDatum != null";

		for (Medium medium : medien)
		{
			Verleihkarte verleihkarte = _verleihkarten.get(medium);
			_verleihkarten.remove(medium);
			_protokollierer.protokolliere(
					VerleihProtokollierer.EREIGNIS_RUECKGABE, verleihkarte);
		}

		informiereUeberAenderung();
	}

	@Override
	public boolean sindAlleNichtVerliehen(List<Medium> medien)
	{
		assert medienImBestand(medien) : "Vorbedingung verletzt: medienExistieren(medien)";
		boolean result = true;
		for (Medium medium : medien)
		{
			if (istVerliehen(medium))
			{
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean sindAlleVerliehenAn(Kunde kunde, List<Medium> medien)
	{
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

		boolean result = true;
		for (Medium medium : medien)
		{
			if (!istVerliehenAn(kunde, medium))
			{
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean istVerliehenAn(Kunde kunde, Medium medium)
	{
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";

		return istVerliehen(medium) && getEntleiherFuer(medium).equals(kunde);
	}

	@Override
	public boolean sindAlleVerliehen(List<Medium> medien)
	{
		assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

		boolean result = true;
		for (Medium medium : medien)
		{
			if (!istVerliehen(medium))
			{
				result = false;
			}
		}
		return result;
	}

	@Override
	public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum)
			throws ProtokollierException
	{
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		assert sindAlleNichtVerliehen(medien) : "Vorbedingung verletzt: sindNichtVerliehen(medien) ";
		assert ausleihDatum != null : "Vorbedingung verletzt: ausleihDatum != null";
		assert istVerleihenMoeglich(kunde, medien) : "Vorbedingung verletzt:  istVerleihenMoeglich(kunde, medien)";

		for (Medium medium : medien)
		{

			if (istVorgemerkt(medium))
			{
				getVormerkkarteFuer(medium).deleteErstenVormerker();
				if (getVormerkkarteFuer(medium).istLeer())
				{
					_vormerkkarten.remove(medium);
				}
			}
			Verleihkarte verleihkarte = new Verleihkarte(kunde, medium,
					ausleihDatum);

			_verleihkarten.put(medium, verleihkarte);
			_protokollierer.protokolliere(
					VerleihProtokollierer.EREIGNIS_AUSLEIHE, verleihkarte);
		}

		informiereUeberAenderung();
	}

	@Override
	public boolean kundeImBestand(Kunde kunde)
	{
		return _kundenstamm.enthaeltKunden(kunde);
	}

	@Override
	public boolean mediumImBestand(Medium medium)
	{
		return _medienbestand.enthaeltMedium(medium);
	}

	@Override
	public boolean medienImBestand(List<Medium> medien)
	{
		assert medien != null : "Vorbedingung verletzt: medien != null";
		assert !medien.isEmpty() : "Vorbedingung verletzt: !medien.isEmpty()";

		boolean result = true;
		for (Medium medium : medien)
		{
			if (!mediumImBestand(medium))
			{
				result = false;
				break;
			}
		}
		return result;
	}

	@Override
	public List<Medium> getAusgelieheneMedienFuer(Kunde kunde)
	{
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		List<Medium> result = new ArrayList<Medium>();
		for (Verleihkarte verleihkarte : _verleihkarten.values())
		{
			if (verleihkarte.getEntleiher().equals(kunde))
			{
				result.add(verleihkarte.getMedium());
			}
		}
		return result;
	}

	@Override
	public Kunde getEntleiherFuer(Medium medium)
	{
		assert istVerliehen(medium) : "Vorbedingung verletzt: istVerliehen(medium)";
		Verleihkarte verleihkarte = getVerleihkarteFuer(medium);
		return verleihkarte.getEntleiher();
	}

	@Override
	public Verleihkarte getVerleihkarteFuer(Medium medium)
	{
		assert istVerliehen(medium) : "Vorbedingung verletzt: istVerliehen(medium)";
		return _verleihkarten.get(medium);
	}

	@Override
	public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde)
	{
		assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		List<Verleihkarte> result = new ArrayList<Verleihkarte>();
		for (Verleihkarte verleihkarte : _verleihkarten.values())
		{
			if (verleihkarte.getEntleiher().equals(kunde))
			{
				result.add(verleihkarte);
			}
		}
		return result;
	}

	@Override
	public boolean vormerkeAn(Kunde kunde, List<Medium> medien)
	{
		assert medien != null : "Vorbedingung verletzt: medium != null";
		assert kunde != null : "Vorbedingung verletzt: kunde != null";
		assert medienImBestand(medien) : "Vorbedingung verletzt: mediumImBestand(medium)";
		if (istVormerkenMoeglich(medien, kunde))
		{
			for (Medium m : medien)
			{
				if (_vormerkkarten.containsKey(m))
				{
					_vormerkkarten.get(m).addVormerker(kunde);
				}
				else
				{
					_vormerkkarten.put(m,
							new Vormerkkarte(kunde, m, Datum.heute()));
					// System.out.print(_vormerkkarten.size());
				}
			}
			informiereUeberAenderung();
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean istVormerkenMoeglich(List<Medium> medium, Kunde kunde)
	{
		assert medium != null : "Vorbedingung verletzt: medium != null";
		assert kunde != null : "Vorbedingung verletzt: kunde != null";

		for (Medium m : medium)
		{
			if (!istVormerkenMoeglich(m, kunde))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean istVormerkenMoeglich(Medium medium, Kunde kunde)
	{
		assert medium != null : "Vorbedingung verletzt: medium != null";
		assert kunde != null : "Vorbedingung verletzt: kunde != null";

		boolean nichtMoeglich = (istVerliehen(medium)
				  && getVerleihkarteFuer(medium).getEntleiher() == kunde)
				|| (istVorgemerkt(medium) && (getVormerkkarteFuer(medium)
						.istVoll() || getVormerkkarteFuer(medium)
						.istKundeInVormerkkarte(kunde)));

		return !nichtMoeglich;

	}

	@Override
	public void vormerkeZurueck(Medium medium, Kunde kunde)
	{
		assert getVormerkkarteFuer(medium) != null : "Vorbedingung verletzt:  getVormerkkarteFuer(medium) != null";
		assert getVormerkkarteFuer(medium).istKundeInVormerkkarte(kunde) : "Vorbedingung verletzt: getVormerkkarteFuer(medium).istKundeInVormerkkarte(kunde)";
		getVormerkkarteFuer(medium).deleteVormerker(kunde);
		if (getVormerkkarteFuer(medium).istLeer())
		{
			_vormerkkarten.remove(medium);
			informiereUeberAenderung();
		}
	}

	@Override
	public Vormerkkarte getVormerkkarteFuer(Medium medium)
	{
		
		assert medium != null : "Vorbedingung verletzt: medium != null";
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";
		return _vormerkkarten.get(medium);
	}

	@Override
	public boolean kannKundeEntleihenMedien(List<Medium> medien, Kunde kunde)
	{
		assert medien != null : "Vorbedingung verletzt: medien != null";
		assert kunde != null : "Vorbedingung verletzt: kunde != null";

		for (Medium m : medien)
		{
			if (_vormerkkarten.containsKey(m)
					&& getVormerkkarteFuer(m).getErstenVormerker() != kunde)
				return false;
		}
		return true;
	}

	@Override
	public boolean istVorgemerkt(Medium medium)
	{
		assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";
		return _vormerkkarten.containsKey(medium);
	}
}
