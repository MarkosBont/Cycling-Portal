/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cycling;
import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalTime;
/**
 * Represents a stage in a race, identified by a unique stage ID --
 * Stores attributes such as name, description, length, start time, type, checkpoints, and stage results --
 * Provides methods to access and modify the attributes, as well as delete the stage and some other methods  --
 * 
 * @author markos
 */
public class Stage implements Serializable{
    private final int stageID; // final as each stage ID is unique and will never change
    private static int nextID = 1; // static as this attribute belongs to the class, not to the instances
    private String stageName;
    private String stageDescription;
    private double stageLength;
    private LocalDateTime startTime; 
    private StageType stageType;
    private ArrayList<Checkpoint> checkpoints = new ArrayList<>();
    private String state = "";
    private HashMap<Integer, LocalTime[]> stageResults = new HashMap<>(); // stores the riderIds and an array of LocalTime objects, representing the time taken to reach each checkpoint
    private HashMap<Integer, LocalTime> totalTimeStageResults = new HashMap<>(); //stores the riderIDs and their real total elapsed time
    
    //constructor for a Stage object.
    public Stage(String stageName, String stageDescription, double length, LocalDateTime startTime, StageType type){
        stageID = nextID++; // automatically increments the ID for each race
        this.stageName = stageName;
        this.stageDescription = stageDescription;
        this.stageLength = length;
        this.startTime = startTime; 
        this.stageType = type;
    }
    
    //getter methods
    public int getStageID(){
        return stageID;
    }
    public String getStageName(){
        return stageName;
    }
    public String getStageDescription(){
        return stageDescription;
    }
    public double getStageLength(){
        return stageLength;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }
    public StageType getStageType(){
        return stageType;
    }
    public ArrayList<Checkpoint> getStageCheckpoints(){
        return checkpoints;
    }
    public String getStageState(){
        return state;
    }
    public HashMap<Integer, LocalTime[]> getStageResults(){
        return stageResults;
    }
    public HashMap<Integer, LocalTime> getTotalStageResults(){
        return totalTimeStageResults;
    }
        
    
    //setter methods
    public void setStageName(String stageName){
        this.stageName = stageName;
    }
    public void setStageDescription(String stageDescription){
        this.stageDescription = stageDescription;
    }
    public void setStageLength(double length){
        this.stageLength = length;
    }
    public void setStartTime(LocalDateTime time){
        this.startTime = time;
    }
    public void setStageType(StageType stageType){
        this.stageType = stageType;
    }
    public void setStageCheckpoints(ArrayList<Checkpoint> checkpoints){
        this.checkpoints = checkpoints;
    }
    public void setStageState(String state){
        this.state = state;
    }
    public void setStageResults(HashMap<Integer, LocalTime[]> stageResults){
        this.stageResults = stageResults;
    }
    public void setTotalStageResults(HashMap<Integer, LocalTime> stageResults){
        this.totalTimeStageResults = stageResults;
    }
    
    //other methods
    public void addCheckpoint(Checkpoint checkpoint){
        checkpoints.add(checkpoint);
    }
    public void removeCheckpoint(Checkpoint checkpoint){ // removes the first instance of a checkpoint from a stage
        checkpoints.remove(checkpoint); 
    }
    public void removeAllCheckpoint(Checkpoint checkpoint){ // removes all instances of a checkpoint from a stage
        while(checkpoints.contains(checkpoint)){
            checkpoints.remove(checkpoint);
        }
    }
    
    //adds a result of checkpoint times which have been reached by the rider
    public void addResult(int riderId, LocalTime[] checkpointTimes){
        stageResults.put(riderId, checkpointTimes);
    }
    
    //adds a result of the total time in which a rider has finished the stage
    public void addTotalTimeResult(int riderId, LocalTime checkpointTimes){
        totalTimeStageResults.put(riderId, checkpointTimes);
    }
    
    //deletes a stage by clearing all it's arraylists and setting the internal ID counter to 1.
    public void deleteStage(){
        checkpoints.clear();
        stageResults.clear();
        totalTimeStageResults.clear();
        nextID = 1;
    }
    
}
