/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cycling;

import java.io.Serializable;

/**
*
*    Represents a checkpoint in a stage, storing information such as type, a unique ID, average gradient, and location --It
*    Supports serialization for storage and retrieval  --Includes methods for setting and retrieving checkpoint attributes.
*
* 
* 
@author markos
*/
public class Checkpoint implements Serializable{
    private CheckpointType checkpointType;
    private final int checkpointId;
    private static int nextId = 1;
    private double averageGradient;
    private double location;
    
    //constructor for a checkpoint. 
    public Checkpoint(CheckpointType checkpointType, double averageGradient, double location){ 
        this.checkpointId = nextId;
        nextId++;
        this.checkpointType = checkpointType;
        this.averageGradient = averageGradient;
        this.location = location;
    }
    
    //constructor overloading with checkpoint object being created only with the location.
    public Checkpoint(double location){
        this.checkpointId = nextId;
        nextId++;
        this.checkpointType = checkpointType.SPRINT;
        this.location = location;
    }
   
    //getter methods
    public int getCheckpointId(){
        return checkpointId;
    }
    
    public CheckpointType getCheckpointType(){
        return checkpointType;
    }
    
    public double getAverageGradient(){
        return averageGradient;
    }
    
    public double getLocation(){
        return location;
    }
    
    //setter methods
    public void setCheckpointType(CheckpointType checkpointType){
        this.checkpointType = checkpointType;
    }
    
    public void setAverageGradient(double averageGradient){
        this.averageGradient = averageGradient;
    }
    
    public void setLocation(double location){
        this.location = location;
    }
    
    //deletes the checkpoints by setting its internal ID counter to 1.
    public void deleteCP(){
        nextId = 1;
    }
}
