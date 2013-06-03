package de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 */
public class DVDTest extends AbstractMediumTest
{
    private static final String BEZEICHNUNG = "DVD";
    private static final int LAENGE = 100;
    private static final String REGISSEUR = "DVD Regisseur";
    private DVD _dvd1;
    private DVD _dvd2;

    @Before
    public void setUp()
    {
        _dvd1 = getMedium();
        _dvd2 = getMedium();
    }

    @Test
    public void testKonstruktor()
    {
        assertEquals(LAENGE, _dvd1.getLaufzeit());
        assertEquals(REGISSEUR, _dvd1.getRegisseur());
    }

    @Test
    public final void testDVDSetter()
    {
        _dvd1.setRegisseur("Regisseur2");
        assertEquals("Regisseur2", _dvd1.getRegisseur());
        _dvd1.setLaufzeit(99);
        assertEquals(99, _dvd1.getLaufzeit());
    }

    @Override
    @Test
    public void testGetMedienBezeichnung()
    {
        String dvdBezeichnung = BEZEICHNUNG;
        assertEquals(dvdBezeichnung, _dvd1.getMedienBezeichnung());
    }

    @Test
    public void testEquals()
    {
        assertFalse("Mehrere Exemplare der gleichen DVD sind gleich",
                _dvd1.equals(_dvd2));
        assertTrue("Mehrere Exemplare der gleichen DVD sind ungleich",
                _dvd1.equals(_dvd1));
    }

    @Override
    protected DVD getMedium()
    {
        return new DVD(TITEL, KOMMENTAR, REGISSEUR, LAENGE);
    }

}
