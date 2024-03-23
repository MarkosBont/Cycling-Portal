/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cycling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalTime;
/**
 *  
 * Represents a cycling race with unique race ID, name, description, stages, and race results --
 *
 * Supports serialization for storage and retrieval --
 * 
 * Provides methods to access and modify race attributes, add or remove stages, add race results, calculate total race length, and delete race data.
 *  
 *  
 * 
 * @author markos
 */
public class Race implements Serializable{
    private final int raceID; 
    private static int nextID = 1;
    private final String NAME; 
    private String DESCRIPTION;
    private ArrayList<Stage> stages = new ArrayList<>(); 
    private HashMap<Rider, LocalTime[]> raceResult = new HashMap<>(); 
    
    // simple race constructor with the race name, and race description
    public Race(String name, String description){ 
        this.raceID = nextID;
        nextID++; // automatically increments the ID so that no race has the same ID
        this.NAME = name;
        this.DESCRIPTION = description;
    }
    
    //overloaded race object constructor including its stages and its checkPoints.
    public Race(String name, String description, ArrayList<Stage> stages, ArrayList<CheckpointType> checkPoints){ 
        this.raceID = nextID;
        nextID++; // automatically increments the ID so that no race has the same ID
        this.NAME = name;
        this.DESCRIPTION = description;
        this.stages = stages;
    }
    
    //getter methods
    public int getRaceID(){
        return raceID;
    }
    public String getRaceName(){
        return NAME;
    }
    public String getRaceDescription(){
        return DESCRIPTION; 
    }
    public ArrayList<Stage> getRaceStages(){
        return stages; 
    }
    public HashMap<Rider, LocalTime[]> getRaceResult(){
        return raceResult; 
    }
    
    // setter methods - No setID(...) provided as it is auto-incremented.
    public void setRaceStages(ArrayList<Stage> stages){
        this.stages = stages;
    }
    public void setRaceResult(HashMap<Rider, LocalTime[]> raceResult){
        this.raceResult = raceResult;
    }
    
    //other methods
    public void addStage(Stage stage){
        stages.add(stage);
    }
    public void removeStage(Stage stage){
        stages.remove(stage);
    }
    
    //adds a result of a rider to the race.
    public void addResult(Rider rider, LocalTime[] stageTimes){
        raceResult.put(rider, stageTimes);
    }
    
    //gets the total length of the race by summing up the length of all previous stages.
    public double totalLength(){
        double totalLength = 0;
    
        for(Stage stage : stages){
            totalLength += stage.getStageLength();
        }
        return totalLength;
    }
    
    //Prints out a nicely formatted string whenever an object is printed
    @Override
    public String toString(){
        if(stages.isEmpty()){
            
        }
        return  "Race{" +
                "raceID =" + raceID +
                ", name = " + NAME +
                ", description = " + DESCRIPTION +
                ", number of stages = " + stages.size() +
                ", total length = " + totalLength() +
                '}';
    }
    
    public int getNumberOfStages(){
        return stages.size();
    }
    
    //deletes a race by clearing out its stages and setting it's ID counter to 0.
    public void deleteRace(){
        stages.clear();
        raceResult.clear();
        nextID = 1;
    }
    
}
