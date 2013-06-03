package de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Geldbetrag;

/**
 * 
 */
public class PCVideospielTest extends AbstractMediumTest
{
    private static final String BEZEICHNUNG = "PCVideospiel";
    private static final String SYSTEM = "System";
    private PCVideospiel _videoSpiel;

    @Before
    public void setUp()
    {
        _videoSpiel = getMedium();
    }

    @Test
    @Override
    public void testBerechneMietgebuehr()
    {
        PCVideospiel medium = getMedium();
        assertEquals(new Geldbetrag(200), medium.berechneMietgebuehr(1));
        assertEquals(new Geldbetrag(200), medium.berechneMietgebuehr(7));
        assertEquals(new Geldbetrag(700), medium.berechneMietgebuehr(8));
        assertEquals(new Geldbetrag(700), medium.berechneMietgebuehr(12));
        assertEquals(new Geldbetrag(1200), medium.berechneMietgebuehr(13));
    }

    @Test
    public void testeKonstruktoren()
    {
        assertEquals(SYSTEM, _videoSpiel.getSystem());
    }

    @Test
    public final void testPCVideospielSetter()
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
    protected PCVideospiel getMedium()
    {
        return new PCVideospiel(TITEL, KOMMENTAR, SYSTEM);
    }

}
