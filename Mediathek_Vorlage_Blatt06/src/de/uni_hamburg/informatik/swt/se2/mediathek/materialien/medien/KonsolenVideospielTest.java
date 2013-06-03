package de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Geldbetrag;

/**
 * 
 */
public class KonsolenVideospielTest extends AbstractMediumTest
{

    private static final String BEZEICHNUNG = "KonsolenVideospiel";
    private static final String SYSTEM = "System";
    private KonsolenVideospiel _videoSpiel;

    @Before
    public void setUp()
    {
        _videoSpiel = getMedium();
    }

    @Test
    @Override
    public void testBerechneMietgebuehr()
    {
        KonsolenVideospiel medium = getMedium();
        assertEquals(new Geldbetrag(200), medium.berechneMietgebuehr(1));
        assertEquals(new Geldbetrag(900), medium.berechneMietgebuehr(3));
        assertEquals(new Geldbetrag(900), medium.berechneMietgebuehr(5));
        assertEquals(new Geldbetrag(1600), medium.berechneMietgebuehr(7));
    }

    @Test
    public void testeKonstruktoren()
    {
        assertEquals(SYSTEM, _videoSpiel.getSystem());
    }

    @Test
    public final void testKonsolenVideospielSetter()
    {
        _videoSpiel.setSystem("System2");
        assertEquals("System2", _videoSpiel.getSystem());
    }

    @Override
    @Test
    public void testGetMedienBezeichnung()
    {
        String videospielBezeichnung = BEZEICHNUNG;
        assertEquals(videospielBezeichnung, _videoSpiel.getMedienBezeichnung());
    }

    @Override
    protected KonsolenVideospiel getMedium()
    {
        return new KonsolenVideospiel(TITEL, KOMMENTAR, SYSTEM);
    }

}
