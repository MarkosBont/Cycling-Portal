/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cycling;

import java.io.Serializable;

/**
 * Represents a rider participating in cycling events, identified by a unique rider ID --
 * Stores attributes such as name and year of birth --
 * Provides methods to access rider attributes, including name and year of birth, and to delete rider data.
 * 
 * @author markos
 */
public class Rider implements Serializable {
    
    private final int riderID; // final as it the rider ID is never changed
    private static int nextID = 1; // static as this attribute belongs to the class, not to the instances
    private final String RIDER_NAME; 
    private final int YEAR_OF_BIRTH;
    
    //constructor for a rider
    public Rider(String riderName, int yearOfBirth){
        riderID = nextID;
        nextID++; // automatically increments the ID so that no rider has the same ID
        this.RIDER_NAME = riderName;
        this.YEAR_OF_BIRTH = yearOfBirth;
    }
    
    //getter methods
    public int getRiderID(){
        return riderID;
    }
    public String getRiderName(){
        return RIDER_NAME;
    }
    public int getRiderYOB(){
        return YEAR_OF_BIRTH;
    }
    
    //no setter methods needed
    
    //deletion method that resets internal ID counter to 1.
    public void deleteRider(){
        nextID = 1;
    }
}