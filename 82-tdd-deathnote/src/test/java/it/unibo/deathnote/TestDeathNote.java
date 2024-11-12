package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import static java.lang.Thread.sleep;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    
    private DeathNote deathNote;

    @BeforeEach
    public void setUp(){
        this.deathNote = new DeathNoteImpl();
    }

    @Test
    void testIrregularRules(){
        try {
            deathNote.getRule(0);
            deathNote.getRule(-1);
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex.getMessage());
            assertFalse(ex.getMessage() == null);
            assertFalse(ex.getMessage().isBlank());
            assertFalse(ex.getMessage().isEmpty());
        }
    }

    @Test
    void testNoRuleEmpty(){
        for(int i = 1; i < DeathNote.RULES.size(); i++){
            assertNotNull(deathNote.getRule(i));
            assertNotEquals("", deathNote.getRule(i));
        }
    }

    @Test
    void testDeath(){
        assertFalse(deathNote.isNameWritten("Cile"));
        deathNote.writeName("Cile");
        assertTrue(deathNote.isNameWritten("Cile"));
        assertFalse(deathNote.isNameWritten("Mouchi"));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void testCauseOfDeath() throws InterruptedException{
        try {
            deathNote.writeDeathCause("HearthAttack");
            deathNote.writeName("Cile");
            assertFalse(deathNote.getDeathCause("Cile") == "Hearth Attack");
            deathNote.writeName("Mouchi");
            deathNote.writeDeathCause("karting accident");
            assertTrue(deathNote.getDeathCause("Mouchi") == "karting accident");
            sleep(100);
            deathNote.writeDeathCause("Hearth Attack");
            assertFalse(deathNote.getDeathCause("Mouchi") == "Hearth Attack");
        } catch (IllegalStateException e) {
            
        }
        
    }

    @Test
    void testDeatilsOfDeath() throws InterruptedException{
        try {
            deathNote.writeDetails("HearthAttack");
            deathNote.writeName("Cile");
            assertTrue(deathNote.getDeathDetails("Cile") == "");
            deathNote.writeDetails("run for too long");
            assertTrue(deathNote.getDeathDetails("Cile") == "run for too long");
            deathNote.writeName("Mouchi");
            sleep(6100);
            deathNote.writeDetails("Too much sleep");
            assertFalse(deathNote.getDeathDetails("Mouchi") == "Too much sleep");
        } catch (IllegalStateException e) {
            
        }
        
        
    }
}