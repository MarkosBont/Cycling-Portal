/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cycling;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * Represents a team participating in cycling events, identified by a unique team ID -- 
 * Stores information such as name, description, and a arrayList of riders --
 * Provides methods to access and modify team attributes --
 * as well as methods to add or remove riders from the team and delete team data.
 * 
 * @author markos
 */
public class Team implements Serializable{
    private final int teamID; // final as it the rider ID is never changed
    private static int nextID = 1; // static as this attribute belongs to the class, not to the instances
    private final String TEAM_NAME;
    private final String TEAM_DESCRIPTION;
    private ArrayList<Rider> riders = new ArrayList<>();
    
    //constructor for a Team object
    public Team(String teamName, String teamDescription){
        teamID = nextID++; // id is assigned, and then incremented so that ID's are unique
        this.TEAM_NAME = teamName;
        this.TEAM_DESCRIPTION = teamDescription;
    }
    
    //getter methods
    public int getTeamID(){
        return teamID;
    }
    public String getTeamName(){
        return TEAM_NAME;
    }
    public String getTeamDescription(){
        return TEAM_DESCRIPTION;
    }
    public ArrayList<Rider> getRiders(){
        return riders;
    }
    
    //No setter methods needed
    
    // other methods
    public void addRider(Rider rider){
        riders.add(rider);
    }
    public void removeRider(Rider rider){
        riders.remove(rider);
    }
    
    //deletes a team by clearing the riders which are stored in it and setting the internal ID counter to 1.
    public void deleteteam(){
        riders.clear();
        nextID = 1;
    }
}
