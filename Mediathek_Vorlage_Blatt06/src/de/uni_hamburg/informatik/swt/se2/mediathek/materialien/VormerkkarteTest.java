package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class VormerkkarteTest
{
	private Datum _datum;
	private Vormerkkarte _karte;
	private Kunde _kunde;
	private Medium _medium;

	@Before
	public void setUp()
	{
		_kunde = new Kunde(new Kundennummer(123456), "ich", "du");

		_datum = Datum.heute();

		_medium = new CD("bar", "baz", "foo", 123);
		_karte = new Vormerkkarte(_kunde, _medium, _datum);
	}

	@Test
	public void testegetFormatiertenString()
	{
		assertNotNull(_karte.getFormatiertenString());
	}

	@Test
	public void testeKonstruktor()
	{
		assertEquals(_kunde, _karte.getErstenVormerker());
		assertEquals(_medium, _karte.getMedium());
		assertEquals(_datum, _karte.getVormerkdatum());
	}

	@Test
	public void testeVormerken()
	{
		Kunde _kunde2 = new Kunde(new Kundennummer(111111), "Hal", "Lo");
		_karte.addVormerker(_kunde2);
		List<Kunde> _vormerker = new LinkedList<Kunde>();
		_vormerker.add(_kunde);
		_vormerker.add(_kunde2);

		assertEquals(_vormerker, _karte.getVormerker());

	}

	@Test
	public void testeVormerkerVerschiebung()
	{
		Kunde _kunde2 = new Kunde(new Kundennummer(111111), "Hal", "Lo");
		_karte.addVormerker(_kunde2);
		_karte.deleteErstenVormerker();
		assertEquals(_kunde2, _karte.getErstenVormerker());
	}

	@Test
	public void testeIstKundeeinVormerker()
	{
		Kunde _kunde2 = new Kunde(new Kundennummer(111111), "Hal", "Lo");
		_karte.addVormerker(_kunde2);
		Kunde _kunde3 = new Kunde(new Kundennummer(111112), "Tom", "Müller");
		_karte.addVormerker(_kunde3);
		assertTrue(_karte.istKundeInVormerkkarte(_kunde2));
		assertTrue(_karte.istKundeInVormerkkarte(_kunde3));

		Kunde _kunde4 = new Kunde(new Kundennummer(111113), "Tim", "Hans");
		assertFalse(_karte.istKundeInVormerkkarte(_kunde4));
	}

	@Test
	public void testVormerkkarteIstVoll()
	{
		Kunde _kunde2 = new Kunde(new Kundennummer(111111), "Hal", "Lo");
		_karte.addVormerker(_kunde2);
		Kunde _kunde3 = new Kunde(new Kundennummer(111112), "Tom", "Müller");
		_karte.addVormerker(_kunde3);

		assertTrue(_karte.istVoll());
	}

	@Test
	public void testVormerkkarteIstLeer()
	{
		_karte.deleteErstenVormerker();

		assertTrue(_karte.istLeer());
	}

	@Test
	public void testEquals()
	{
		Vormerkkarte karte1 = new Vormerkkarte(_kunde, _medium, _datum);

		assertTrue(_karte.equals(karte1));
		assertEquals(_karte.hashCode(), karte1.hashCode());

		Kunde kunde2 = new Kunde(new Kundennummer(654321), "ich", "du");
		CD medium2 = new CD("hallo", "welt", "foo", 321);
		Datum datum2 = Datum.heute().minus(1);
		Vormerkkarte karte2 = new Vormerkkarte(kunde2, medium2, datum2);

		assertFalse(_karte.equals(karte2));
		assertNotSame(_karte.hashCode(), karte2.hashCode());

	}
}
