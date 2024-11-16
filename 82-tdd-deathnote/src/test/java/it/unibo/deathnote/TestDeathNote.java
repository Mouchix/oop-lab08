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

    private final String DEFAULT_DEATH_CAUSE = "Heart attack";
    private final String KARTING_DEATH_CAUSE = "Karting accident";
    private final String FIRST_PEOPLE = "Cile";
    private final String SECOND_PEOPLE = "Mouchi";
    private final String FIRST_DETAILS = "Run for too long";
    private final String SECOND_DETAILS = "Too much sleep";
    private final String EMPTY_STRING = "";


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
            assertNotEquals(EMPTY_STRING, deathNote.getRule(i));
        }
    }

    @Test
    void testDeath(){
        assertFalse(deathNote.isNameWritten(FIRST_PEOPLE));
        deathNote.writeName(FIRST_PEOPLE);
        assertTrue(deathNote.isNameWritten(FIRST_PEOPLE));
        assertFalse(deathNote.isNameWritten(SECOND_PEOPLE));
        assertFalse(deathNote.isNameWritten(EMPTY_STRING));
    }

    @Test
    void testCauseOfDeath() throws InterruptedException{
        try {
            deathNote.writeDeathCause(DEFAULT_DEATH_CAUSE);
            deathNote.writeName(FIRST_PEOPLE);
            assertFalse(deathNote.getDeathCause(FIRST_PEOPLE) == DEFAULT_DEATH_CAUSE);
            deathNote.writeName(SECOND_PEOPLE);
            deathNote.writeDeathCause(KARTING_DEATH_CAUSE);
            assertTrue(deathNote.getDeathCause(SECOND_PEOPLE) == KARTING_DEATH_CAUSE);
            sleep(100);
            deathNote.writeDeathCause(DEFAULT_DEATH_CAUSE);
            assertFalse(deathNote.getDeathCause(SECOND_PEOPLE) == DEFAULT_DEATH_CAUSE);
        } catch (IllegalStateException e) {
            
        }
        
    }

    @Test
    void testDeatilsOfDeath() throws InterruptedException{
        try {
            deathNote.writeDetails(FIRST_DETAILS);
            deathNote.writeName(FIRST_PEOPLE);
            assertTrue(deathNote.getDeathDetails(FIRST_PEOPLE) == EMPTY_STRING);
            deathNote.writeDetails(FIRST_DETAILS);
            assertTrue(deathNote.getDeathDetails(FIRST_PEOPLE) == FIRST_DETAILS);
            deathNote.writeName(SECOND_PEOPLE);
            sleep(6100);
            deathNote.writeDetails(SECOND_DETAILS);
            assertFalse(deathNote.getDeathDetails(SECOND_PEOPLE) == SECOND_DETAILS);
        } catch (IllegalStateException e) {
            
        }   
    }
}