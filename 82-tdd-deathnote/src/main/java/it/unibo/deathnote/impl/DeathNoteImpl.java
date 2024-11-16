package it.unibo.deathnote.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private final int TIME_FOR_CAUSE_OF_DEATH = 40;
    private final int TIME_FOR_DETAILS_OF_DEATH = 6040;

    final List<DeathPerson> list = new LinkedList<>();
    long time;

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber > RULES.size()){
            throw new IllegalArgumentException("Rule number not valid");
        }

        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        if(name == null) {
            throw new NullPointerException("Write a valid name");
        }

        list.add(new DeathPerson(name));
        time = System.nanoTime();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(cause == null || list.isEmpty()){
            throw new IllegalStateException();
        }


        if((TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time)) < TIME_FOR_CAUSE_OF_DEATH){
            list.getLast().setCauseOfDeath(cause);
            return true;
        }

        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if(details == null || list.isEmpty()){
            throw new IllegalStateException();
        }

        if((TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time)) < TIME_FOR_DETAILS_OF_DEATH){
            list.getLast().setDetailsOfDeath(details);
            return true;
        }

        return false;
    }

    @Override
    public String getDeathCause(String name) {
        if(!isNameWritten(name)){
            throw new IllegalArgumentException("Name: " + name + "is not written in the death note");
        }

        for(DeathPerson p: list) {
            if(p.getName().equals(name)){
                return p.getCauseOfDeath();
            }
        }

        return "Heart attack";
    }

    @Override
    public String getDeathDetails(String name) {
        if(!isNameWritten(name)){
            throw new IllegalArgumentException("Name: " + name + "is not written in the death note");
        }

        for(DeathPerson p: list) {
            if(p.getName().equals(name)){
                if(p.getDetailsOfDeath().isEmpty()){
                    return "";
                } 

                return p.getDetailsOfDeath();
            }
        }

        return "";
    }

    @Override
    public boolean isNameWritten(String name) {
        for(DeathPerson p: list) {
            if(p.getName().equals(name)){
                return true;
            }
        }

        return false;
    }

    static private class DeathPerson{
        final String name;
        String causeOfDeath;
        String detailsOfDeath;

        public DeathPerson(String name) {
            this.name = name;
        }

        public String getName(){
            return this.name;
        }

        public String getCauseOfDeath() {
            return this.causeOfDeath;
        }
        public String getDetailsOfDeath() {
            return this.detailsOfDeath;
        }

        public void setCauseOfDeath(String s) {
            this.causeOfDeath = s;
        }
        
        public void setDetailsOfDeath(String s) {
            this.detailsOfDeath = s;
        }

    }

}
