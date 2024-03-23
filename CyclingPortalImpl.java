/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cycling;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.time.Duration;
import java.util.Comparator;
import java.util.Map;
/**
 *
 *  The main Implementation of the MiniCyclingPortal, including all methods and storing objects in arrayLists.
 * 
 * @author markos
 */
public class CyclingPortalImpl implements MiniCyclingPortal {
    ArrayList<Race> races = new ArrayList<>();  //holds all the races
    ArrayList<Stage> stagesImpl = new ArrayList<>(); // holds all the stages ever made, regardlesss of their race
    ArrayList<Checkpoint> checkpoints = new ArrayList<>(); // holds all the checkpoints ever made, regardlesss of their stage
    ArrayList<Team> teams = new ArrayList<>(); // holds all the teams ever made
    ArrayList<Rider> riders = new ArrayList<>(); // holds all the riders, regardless of their team
   
    
    /**
	 * Get the races currently created in the platform.
	 * 
	 * @return An array of race IDs in the system or an empty array if none exists.
	 */
    @Override
    public int[] getRaceIds(){
        int[] raceIDs = new int[races.size()]; // creates array of the size of the arrayList which holds all the races
        for(int i = 0; i < races.size(); i++){
            raceIDs[i] = races.get(i).getRaceID(); // copies each value from the arrayList objects to the array
        
        }
        return raceIDs;
    }
    
    /**
	 * The method creates a staged race in the platform with the given name and
	 * description.
	 * <p>
	 * 
	 * 
	 * @param name        Race's name.
	 * @param description Race's description (can be null).
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 * @return the unique ID of the created race.
	 * 
	 */
    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        

        if(!races.isEmpty()){
            for(Race race : races){
            if(name.equals(race.getRaceName()) ){ //gets the name of each race and compares it to the name enetered
                throw new IllegalNameException("This name of the race already exists: " + name);
            }
        }
        
        if(name == null || name.isBlank() || name.isEmpty() || name.length() > 30){ // checks whether the name is null, empty, or contains 30 or more characters
            throw new InvalidNameException("This name is invalid, it is either empty or exceeds 30 characters");
        }
        }
        
   
        Race race = new Race(name, description); //creates a new race by calling the constructor
        races.add(race); // adds the race to the races arrayList
        return race.getRaceID();
    }
    
    /**
	 * Get the details from a race.
	 * <p>
	 * 
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return Any formatted string containing the race ID, name, description, the
	 *         number of stages, and the total length (i.e., the sum of all stages'
	 *         length).
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        for(Race race : races){ 
            if(race.getRaceID() == raceId){ // checks every race made for the id
                return race.toString(); 
            }
        }
        
        throw new IDNotRecognisedException("Race ID not recognized: " + raceId); // if the ID is not found, this exception is thrown
    }

    /**
	 * The method removes the race and all its related information, i.e., stages,
	 * checkpoints, and results.
	 * <p>
	 *
	 *
	 * @param raceId The ID of the race to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        int checkBit = 0; // integer used as a flag
        
        for(Race race : races){ // checks each race created
            if(race.getRaceID() == raceId){
                races.remove(race);
                checkBit = 1; //verifies that the race has been found
                break;
            }
        }
        if(checkBit == 0){
            throw new IDNotRecognisedException("Race ID not recognized: " + raceId); // if the raceID has not been found, then an exception is thrown. 
        }
        
    }

    /**
	 * The method queries the number of stages created for a race.
	 * <p>
	 * s
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return The number of stages created for the race.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        
        for(Race race : races){ // checks each race created
            if(race.getRaceID() == raceId){
                return race.getNumberOfStages(); 
            }
        }
            throw new IDNotRecognisedException("Race ID not recognized: " + raceId); // if the raceID has not been found, then an exception is thrown. 
        }

    /**
	 * Creates a new stage and adds it to the race.
	 * <p>
	 * 
	 * 
	 * @param raceId      The race which the stage will be added to.
	 * @param stageName   An identifier name for the stage.
	 * @param description A descriptive text for the stage.
	 * @param length      Stage length in kilometres.
	 * @param startTime   The date and time in which the stage will be raced. It
	 *                    cannot be null.
	 * @param type        The type of the stage. This is used to determine the
	 *                    amount of points given to the winner.
	 * @return the unique ID of the stage.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 * @throws IllegalNameException     If the name already exists in the platform.
	 * @throws InvalidNameException     If the name is null, empty, has more than 30
	 *                              	characters, or has white spaces.
	 * @throws InvalidLengthException   If the length is less than 5km.
	 */
    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        int checkBit = 0;
        Race correctRace = null;
        
        for(Race race : races){
            if(race.getRaceID() == raceId){ //checks whether the raceID is valid
                checkBit = 1;
                correctRace = race;
                break; //exits for loop
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("Race ID not recognized: " + raceId); // if the raceID is invalid, the exception is thrown
        }
        ArrayList<Stage> raceStages = correctRace.getRaceStages();
        
        if(!raceStages.isEmpty()){ // checks whether the stage name is the same to a pre-existing stage, only if the stage ArrayList is not empty.
            for(Stage stage : raceStages){
                if(stageName.equals(stage.getStageName())){ //gets the name of each stage and compares it to the name entered
                    throw new IllegalNameException("This name of the stage already exists: " + stageName);
                }
            }
        }
        
        if(stageName == null || stageName.isBlank() || stageName.isEmpty() || stageName.length() > 30){ // checks whether the name is null, empty, or contains 30 or more characters
            throw new InvalidNameException("This name is invalid, it is either empty or exceeds 30 characters");
            }

        
        if(length < 5.0){
            throw new InvalidLengthException("This length is under 5km, you have entered: " + length); // if the length is shorter than 5, then an exception is thrown
        }
        
        Stage stage = new Stage(stageName, description, length, startTime, type);// calls the stage constructor to create the new stage
        correctRace.addStage(stage); //adds the stage to the race
        stagesImpl.add(stage); // adds the stage to the stages arraylist
        
        return stage.getStageID();
    }

    /**
	 * Retrieves the list of stage IDs of a race.
	 * <p>
	 * 
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return An array of stage IDs ordered (from first to last) by their sequence in the
	 *         race or an empty array if none exists.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        ArrayList<Stage> raceStages = new ArrayList<>(); // initialises the raceStages arrayList
        int checkBit = 0;
        
        for(Race race : races){
            if(race.getRaceID() == raceId){ //checks whether the raceID is valid
                checkBit = 1;
                break; //exits for loop
            }
        }
        if(checkBit == 0){
            throw new IDNotRecognisedException("Race ID not recognized: " + raceId); // if the raceID is invalid, the exception is thrown
        }
        
        for(Race race : races){
            if(race.getRaceID() == raceId){ //finds the valid raceID
                raceStages = race.getRaceStages(); // assigns the stages ArrayList of the object to a variable
                break; //exits for loop
            }
        }
        
        int[] stagesArray = new int[raceStages.size()]; //makes an integer array with the same size as the stages arrayList
        
        for(int i = 0; i < raceStages.size(); i++){
            stagesArray[i] = raceStages.get(i).getStageID(); //assigns each stageID to a position of the stageArray, in the same order as the
        }
        return stagesArray;
    }

    /**
	 * Gets the length of a stage in a race, in kilometres.
	 * <p>
	 *
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The stage's length.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        for(Stage stage : stagesImpl){ // goes through every stage in any race
            if(stage.getStageID() == stageId){
                return stage.getStageLength();
            }
        }
        
        throw new IDNotRecognisedException("Race ID not recognized: " + stageId); // if the stageID is invalid, the exception is thrown
    } 

    /**
	 * Removes a stage and all its related data, i.e., checkpoints and results.
	 * <p>
	 * 
	 * 
	 * @param stageId The ID of the stage being removed.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        int checkBit = 0;
        Stage foundStage = null;
        
        for(Stage stage : stagesImpl){ // goes through every stage in any race
            if(stage.getStageID() == stageId){
                foundStage = stage; //finds the stage with the ID in the parameter
                checkBit = 1;
                break;
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }
        
        foundStage.deleteStage();
        stagesImpl.remove(foundStage); // remove this stage from this arrayList
        
        //we also need to remove the stage from any race that it exists in.
        ArrayList<Stage> foundAL = new ArrayList<>(); 
        for(Race race : races){
            for(int i = 0; i < race.getRaceStages().size(); i++){ // removes the stage from any race that it was in, according to its unique id 
                if(race.getRaceStages().get(i).getStageID() == stageId){
                    foundAL = race.getRaceStages(); // assigns the arrayList of the race stages to a variable
                    foundAL.remove(i); // removes this race from the arrayList
                    race.setRaceStages(foundAL); // reassigns the arrayList to the race, now without the deleted stage.
                }
            }
        }
        
    }

    /**
	 * Adds a climb checkpoint to a stage.
	 * <p>
	 * 
	 * 
	 * @param stageId         The ID of the stage to which the climb checkpoint is
	 *                        being added.
	 * @param location        The kilometre location where the climb finishes within
	 *                        the stage.
	 * @param type            The category of the climb - {@link CheckpointType#C4},
	 *                        {@link CheckpointType#C3}, {@link CheckpointType#C2},
	 *                        {@link CheckpointType#C1}, or {@link CheckpointType#HC}.
	 * @param averageGradient The average gradient for the climb.
	 * @param length          The length of the climb in kilometre.
	 * @return The ID of the checkpoint created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    checkpoint.
	 */
    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        
        int checkBit = 0;
        Stage foundStage = null; //initialises foundStage
        for(Stage stage: stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage with the stageId passed as a parameter
                foundStage = stage;
                checkBit = 1;
                break;
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId); //if the ID was not found, an exception was thrown
        }
        
        if(foundStage.getStageType() == StageType.TT){
            throw new InvalidStageStateException("The stage is a Time Trial, thus cannot contain any Categorised Climbs"); //If the stage is a TT, this is invalid, so an exception is thrown
        }
        
        if(location > foundStage.getStageLength() || location < 0){
            throw new InvalidLocationException("The Location exceeds the length of the stage or is less than 0");
        }
            
        if(foundStage.getStageState().equals("waiting for results")){
            throw new InvalidStageStateException("The stage is currently waiting for results");
        }
        
        Checkpoint newCheckpoint = new Checkpoint(type, averageGradient, length); // creates a new checkpoint
        checkpoints.add(newCheckpoint); // adds the checkpoint to the checkpoint arrayList
        foundStage.addCheckpoint(newCheckpoint); // adds the checkPoint to the relevant stage
        System.out.println(newCheckpoint.getCheckpointId());
        
        return newCheckpoint.getCheckpointId(); // returns the unique ID of the new checkpoint
    }

    /**
	 * Adds an intermediate sprint to a stage.
	 * <p>
	 * 
	 * 
	 * @param stageId  The ID of the stage to which the intermediate sprint checkpoint
	 *                 is being added.
	 * @param location The kilometre location where the intermediate sprint finishes
	 *                 within the stage.
	 * @return The ID of the checkpoint created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    checkpoint.
	 */
    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        int checkBit = 0;
        Stage foundStage = null; //initialises foundStage
        for(Stage stage: stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage with the stageId passed as a parameter
                foundStage = stage;
                checkBit = 1;
                break;
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId); //if the ID was not found, an exception was thrown
        }
        
        if(foundStage.getStageType() == StageType.TT){
            throw new InvalidStageStateException("The stage is a Time Trial, thus cannot contain any Categorised Climbs"); //If the stage is a TT, this is invalid, so an exception is thrown
        }
        
        if(location > foundStage.getStageLength() || location < 0){
            throw new InvalidLocationException("The Location exceeds the length of the stage or is less than 0");
        }
            
        if(foundStage.getStageState().equals("waiting for results")){
            throw new InvalidStageStateException("The stage is currently waiting for results");
        }
        
        Checkpoint newCheckpoint = new Checkpoint(location); // creates a new checkpoint
        checkpoints.add(newCheckpoint); // adds the checkpoint to the checkpoint arrayList
        foundStage.addCheckpoint(newCheckpoint); // adds the checkPoint to the relevant stage
        System.out.println(newCheckpoint.getCheckpointId());
        
        return newCheckpoint.getCheckpointId(); // returns the unique ID of the new checkpoint
    }

    /**
	 * Removes a checkpoint from a stage.
	 * <p>
	 * 
	 * 
	 * @param checkpointId The ID of the checkpoint to be removed.
	 * @throws IDNotRecognisedException   If the ID does not match to any checkpoint in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        int checkBit = 0;
        
        for(Checkpoint checkpoint : checkpoints){ // goes through every checkpoint in any stage
            if(checkpoint.getCheckpointId() == checkpointId){
                checkpoints.remove(checkpoint); // remove this checkpoint from this arrayList
                checkBit = 1;
                break;
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("Checkpoint ID not recognized: " + checkpointId);
        }
        
        
        //we also need to remove the checkpoint from any stage that it exists in.
        
        ArrayList<Checkpoint> foundAL = new ArrayList<>(); 
        for(Stage stage : stagesImpl){
            for(int i = 0; i < stage.getStageCheckpoints().size(); i++){ // removes the checkpoint from any stage that it was in, according to its unique id 
                if(stage.getStageCheckpoints().get(i).getCheckpointId() == checkpointId){
                    if(stage.getStageState().equals("waiting for results")){
                        throw new InvalidStageStateException("The stage is currently waiting for results");
                    }
                    foundAL = stage.getStageCheckpoints(); // assigns the arrayList of the checkpoints to a variable
                    foundAL.remove(i); // removes this checkpoint from the arrayList
                    stage.setStageCheckpoints(foundAL); // reassigns the arrayList to the stage, now without the deleted checkpoint.
                }
            }
        }
    }

    /**
	 * Concludes the preparation of a stage. After conclusion, the stage's state
	 * should be "waiting for results".
	 * <p>
	 * 
	 * 
	 * @param stageId The ID of the stage to be concluded.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        int checkBit = 0;
        Stage foundStage = null; // intiialises the foundStage
        for(Stage stage: stagesImpl){
            if(stage.getStageID() == stageId){ //finds the stage corresponding to the parameter stageId
                foundStage = stage;
                checkBit = 1;
                break; // breaks once the correct stage has been found
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("This ID does not correspond to an exisiting stage: " + stageId);
        }
        
        if(foundStage.getStageState().equals("waiting for results")){
            throw new InvalidStageStateException("The stage is already waiting for results");
        }
        
        foundStage.setStageState("waiting for results");
    }

    /**
	 * Retrieves the list of checkpoint (mountains and sprints) IDs of a stage.
	 * <p>
	 * 
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The list of checkpoint IDs ordered (from first to last) by their location in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    @Override
    public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        int checkBit = 0;
        Stage foundStage = null; // intiialises the foundStage
        for(Stage stage: stagesImpl){
            if(stage.getStageID() == stageId){ //finds the stage corresponding to the parameter stageId
                foundStage = stage;
                checkBit = 1;
                break; // breaks once the correct stage has been found
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("This ID does not correspond to an exisiting stage: " + stageId);
        }
        
        Checkpoint[] checkpointsInStage = new Checkpoint[foundStage.getStageCheckpoints().size()]; //creates an array of the checkpoints with the size of the checkpoints
        for(int i = 0; i < foundStage.getStageCheckpoints().size(); i++){ //adds each checkpoint to the array
            checkpointsInStage[i] = foundStage.getStageCheckpoints().get(i);
        }
        for(int i = 0; i < checkpointsInStage.length -1; i++){ // bubble sort algorithm for sorting the checkpoints based on their location in the stage
            for(int j = 0; j < checkpointsInStage.length - i -1; j++){
                if(checkpointsInStage[j].getLocation() < checkpointsInStage[j+1].getLocation()){
                    Checkpoint temp = checkpointsInStage[j];
                    checkpointsInStage[j] = checkpointsInStage[j+1];
                    checkpointsInStage[j+1] = temp;
                }
            }
        }
        
        int[] checkpointIds = new int[checkpointsInStage.length]; // making new array to store the checkpoints
        for(int i = 0; i < checkpointsInStage.length; i++){
            checkpointIds[i] = checkpointsInStage[i].getCheckpointId(); // assigns the checkpoint Ids to a value in the array
        }
        
        return checkpointIds;
    }

    /**
	 * Creates a team with name and description.
	 * <p>
	 * 
	 * 
	 * @param name        The identifier name of the team.
	 * @param description A description of the team.
	 * @return The ID of the created team.
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 */
    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        if(!teams.isEmpty()){
            for(Team team : teams){
            if(name.equals(team.getTeamName()) ){ //gets the name of each team and compares it to the name enetered
                throw new IllegalNameException("This name of the race already exists: " + name);
            }
        }
        
        if(name == null || name.isBlank() || name.isEmpty() || name.length() > 30){ // checks whether the name is null, empty, or contains 30 or more characters
            throw new InvalidNameException("This name is invalid, it is either empty or exceeds 30 characters");
        }
        }
        
   
        Team team = new Team(name, description); //creates a new race by calling the constructor
        teams.add(team); // adds the race to the races arrayList
        
        return team.getTeamID();
    }

    /**
	 * Removes a team from the system.
	 * <p>
	 * 
	 * 
	 * @param teamId The ID of the team to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        int checkBit = 0;
        for(Team team : teams){
            if(team.getTeamID() == teamId){ // removes the team with the same ID as the one passed as a parameter.
                teams.remove(team);
                checkBit = 1;
                break;
            }
        }
        if(checkBit == 0){
            throw new IDNotRecognisedException("This ID does not correspond to any team: " + teamId);
        }
    }

    /**
	 * Get the list of teams IDs in the system.
	 * <p>
	 * 
	 * 
	 * @return The list of IDs from the teams in the system. An empty list if there
	 *         are no teams in the system.
	 * 
	 */
    @Override
    public int[] getTeams() {
        int[] empty = new int[0];
        if(teams.isEmpty()){ // if there are no teams, an empty array is returned
            return empty;
        }else{
            
            int[] teamIDs = new int[teams.size()];
            for(int i = 0; i < teams.size(); i++){
                teamIDs[i] = teams.get(i).getTeamID(); // each team ID is added to the array in the order of the arrayList.
            }
            
            return teamIDs;
        }
    }

    /**
	 * Get the riders of a team.
	 * <p>
	 * 
	 * @param teamId The ID of the team being queried.
	 * @return A list with riders' ID.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        Team foundTeam = null; // initialises the found team
        int checkBit = 0;
        for(Team team : teams){
            if(team.getTeamID() == teamId){ //goes through every team, attempting to find the corresponding teamId
                foundTeam = team;
                checkBit = 1;
                break;
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("The team ID is invalid: " + teamId);
        }
        
        int[] riderIds = new int[foundTeam.getRiders().size()]; // creates an array with the same size as the number of riders
        
        for(int i = 0; i < foundTeam.getRiders().size(); i++){
            riderIds[i] = foundTeam.getRiders().get(i).getRiderID(); // copies each rider's ID into the array
        }
        
        return riderIds;
    }

    /**
	 * Creates a rider.
	 * <p>
	 * 
	 * @param teamID      The ID rider's team.
	 * @param name        The name of the rider.
	 * @param yearOfBirth The year of birth of the rider.
	 * @return The ID of the rider in the system.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 * @throws IllegalArgumentException If the name of the rider is null or empty,
	 *                                  or the year of birth is less than 1900.
	 */
    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        if(name.isEmpty() || name.isBlank() ){
            throw new IllegalArgumentException("The name you have entered is blank or empty"); 
        }
        if(yearOfBirth < 1900){
            throw new IllegalArgumentException("The year of birth of the rider is before 1900: " + yearOfBirth); 
        }
        
        Team foundTeam = null; // initialises the found team
        int checkBit = 0;
        for(Team team : teams){
            if(team.getTeamID() == teamID){ //goes through every team, attempting to find the corresponding teamId
                foundTeam = team;
                checkBit = 1;
                break;
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("The team ID is invalid: " + teamID);
        }

        Rider rider = new Rider(name, yearOfBirth); // initialises the new rider
        foundTeam.addRider(rider); // adds the rider to the team
        riders.add(rider); // adds the rider to the riders arrayList. 
        
        return rider.getRiderID(); // returns the ID of the new rider
    }

    /**
	 * Removes a rider from the system. When a rider is removed from the platform,
	 * all of its results should be also removed. Race results must be updated.
	 * <p>
	 * 
	 * @param riderId The ID of the rider to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any rider in the
	 *                                  system.
	 */
    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        int checkBit = 0;
        Rider foundRider = null; // initialises the founfRider
        for(Rider rider: riders){
            if(rider.getRiderID() == riderId){ // finds the rider with the associated riderId
                foundRider = rider;
                checkBit = 1;
                break;
            }
        }
        
        if(checkBit == 0){
            throw new IDNotRecognisedException("The Rider ID is invalid: " + riderId);
        }
        
        riders.remove(foundRider); // removes the rider from the riders arrayList
        
        Team ridersTeam = null;
        for(Team team : teams){
            ArrayList<Rider> ridersInTeam = team.getRiders();
            if(ridersInTeam.contains(foundRider)){
                ridersInTeam.remove(foundRider);
            }
        }
        
        for(Stage stage : stagesImpl){
            if(stage.getStageResults().containsKey(riderId)){
                HashMap<Integer, LocalTime[]>  results = stage.getStageResults();
                results.remove(riderId);
            stage.setStageResults(results);
            }else{
                continue;
            }
            
        }
        
    }

    /**
	 * Record the times of a rider in a stage.
	 * <p>
         * 
	 * @param stageId     The ID of the stage the result refers to.
	 * @param riderId     The ID of the rider.
	 * @param checkpointTimes An array of times at which the rider reached each of the
	 *                    checkpoints of the stage, including the start time and the
	 *                    finish line.
	 * @throws IDNotRecognisedException    If the ID does not match to any rider or
	 *                                     stage in the system.
	 * @throws DuplicatedResultException   Thrown if the rider has already a result
	 *                                     for the stage. Each rider can have only
	 *                                     one result per stage.
	 * @throws InvalidCheckpointTimesException Thrown if the length of checkpointTimes is
	 *                                     not equal to n+2, where n is the number
	 *                                     of checkpoints in the stage; +2 represents
	 *                                     the start time and the finish time of the
	 *                                     stage.
	 * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
	 *                                     results". Results can only be added to a
	 *                                     stage while it is "waiting for results".
	 */
    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
        Stage foundStage = null;
        int stageCheckBit = 0;
        for(Stage stage : stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage associated with the stageId
                foundStage = stage;
                stageCheckBit = 1;
                break;
            }
        }
        
        int riderCheckBit = 0;
        Rider foundRider = null;
        for(Rider rider: riders){
            if(rider.getRiderID() == riderId){ // finds the rider associated with the riderId
                foundRider = rider;
                riderCheckBit = 1;
                break;
            }
        }
        
        // checking all cases that will cause an exception 
        if(stageCheckBit == 0 || riderCheckBit == 0){
            throw new IDNotRecognisedException("The Stage or Rider ID is invalid. Stage ID: " + stageId + ". Rider ID: " + riderId + ".");
        }
        
        if(foundStage.getStageResults().containsKey(foundRider.getRiderID())){
            throw new DuplicatedResultException("There already exists a result for this rider. Rider ID:" + riderId);
        }
        
        if(!foundStage.getStageState().equals("waiting for results")){
            throw new InvalidStageStateException("The stage state is not set to 'waiting for results'.");
        }
        
        if(!(checkpointTimes.length == (foundStage.getStageCheckpoints().size() + 2))){
            throw new InvalidCheckpointTimesException("There are not n + 2 number of checkpointTimes in the parameter checkPointTimes");
        }
        
        
        foundStage.addResult(riderId, checkpointTimes); // adds the result of the rider to the stageResult
        
                
    }

    /**
	 * Get the times of a rider in a stage.
	 * <p>
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The array of times at which the rider reached each of the checkpoints
	 *         of the stage and the total elapsed time. The elapsed time is the
	 *         difference between the finish time and the start time. Return an
	 *         empty array if there is no result registered for the rider in the
	 *         stage. Assume the total elapsed time of a stage never exceeds 24h
	 *         and, therefore, can be represented by a LocalTime variable. There is
	 *         no need to check for this condition or raise any exception.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage foundStage = null;
        int stageCheckBit = 0;
        for(Stage stage : stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage associated with the stageId
                foundStage = stage;
                stageCheckBit = 1;
                break;
            }
        }
        
        int riderCheckBit = 0;
        Rider foundRider = null;
        for(Rider rider: riders){
            if(rider.getRiderID() == riderId){ // finds the rider associated with the riderId
                foundRider = rider;
                riderCheckBit = 1;
                break;
            }
        }
        
        if(stageCheckBit == 0 || riderCheckBit == 0){
            throw new IDNotRecognisedException("The Stage or Rider ID is invalid. Stage ID: " + stageId + ". Rider ID: " + riderId + ".");
        }
        
        LocalTime[] empty = {};
        if(!foundStage.getStageResults().containsKey(foundRider.getRiderID())){ // if the rider has no result in that stage, return an empty array.
            return empty;
        }
        
        HashMap<Integer, LocalTime[]> allRiderResults = foundStage.getStageResults(); // hashmap containing rider Ids as keys and the array of times as values
        LocalTime[] riderResults = allRiderResults.get(foundRider.getRiderID()); // gets the LocalTime array associated with the rider. 
        Arrays.sort(riderResults); // sorts the array in ascending order (earliest to laterst time)
        
        //getting the total time elapsed of the rider
        LocalTime[] riderResults_withTotal = new LocalTime[riderResults.length+1]; // new array made to store all checkpoint results and the total result
        Duration totalTime = Duration.between(riderResults[0], riderResults[riderResults.length - 1]); // the total time taken to pass the stage
        
        // converting the Duration object to a LocalTime object
        long seconds = totalTime.getSeconds();
        long hours = seconds/3600;
        seconds = seconds % 3600;
        long minutes = seconds/60;
        seconds  = seconds%60;
        
        LocalTime totalLocalTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);
        riderResults_withTotal[riderResults_withTotal.length -1] = totalLocalTime;
        
        for(int i = 0; i < riderResults_withTotal.length - 1; i++){
            riderResults_withTotal[i] = riderResults[i];
        }
        
        foundStage.addTotalTimeResult(riderId, totalLocalTime);
        
        return riderResults_withTotal;
        
    }

    /**
	 * For the general classification, the aggregated time is based on the adjusted
	 * elapsed time, not the real elapsed time. Adjustments are made to take into
	 * account groups of riders finishing very close together, e.g., the peloton. If
	 * a rider has a finishing time less than one second slower than the
	 * previous rider, then their adjusted elapsed time is the smallest of both. For
	 * instance, a stage with 200 riders finishing "together" (i.e., less than 1
	 * second between consecutive riders), the adjusted elapsed time of all riders
	 * should be the same as the first of all these riders, even if the real gap
	 * between the 200th and the 1st rider is much bigger than 1 second. There is no
	 * adjustments on elapsed time on time-trials.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The adjusted elapsed time for the rider in the stage. Return null if 
	 * 		  there is no result registered for the rider in the stage.
	 * @throws IDNotRecognisedException   If the ID does not match to any rider or
	 *                                    stage in the system.
	 */
    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage foundStage = null;
        int stageCheckBit = 0;
        for(Stage stage : stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage associated with the stageId
                foundStage = stage;
                stageCheckBit = 1;
                break;
            }
        }
        
        int riderCheckBit = 0;
        Rider foundRider = null;
        for(Rider rider: riders){
            if(rider.getRiderID() == riderId){ // finds the rider associated with the riderId
                foundRider = rider;
                riderCheckBit = 1;
                break;
            }
        }
        
        if(stageCheckBit == 0 || riderCheckBit == 0){
            throw new IDNotRecognisedException("The Stage or Rider ID is invalid. Stage ID: " + stageId + ". Rider ID: " + riderId + ".");
        }
        
        
        if(!foundStage.getStageResults().containsKey(foundRider.getRiderID())){ // if the rider has no result in that stage, return null.
            return null;
        }
        
        HashMap<Integer, LocalTime[]> allRiderResults = foundStage.getStageResults();
        HashMap<Integer, Duration> totalTimeResults = new HashMap<>(); // new HashMap which will store Rider IDs as keys and the real elapsed time as values
        LocalTime[] times = allRiderResults.get(foundRider.getRiderID());
        Arrays.sort(times);
        
        if(!(allRiderResults.containsKey(foundRider.getRiderID()))){ // if the rider ID is not found in the stage results, null is returned
            return null;
        }
        
        for(Integer Id : allRiderResults.keySet()){
            
            LocalTime firstCheckpoint = times[0]; // accesses the first time
            LocalTime lastCheckpoint = times[times.length-1]; // accesses the last time
            
            Duration realElapsedTime = Duration.between(firstCheckpoint, lastCheckpoint); // computes the difference between the two times
            
            totalTimeResults.put(Id, realElapsedTime); // appends the result to the hashmap
        }
        
        int[] forRiderIds = new int[allRiderResults.keySet().size()]; // creates an array of the size equal to the number of riders in the stage
        int index = 0;
        for(int key : allRiderResults.keySet()){
            forRiderIds[index] = key; // copies all the riderIds into the array
            index++;
        }
        
        //find whether or not two consecutive riders have a difference of one second between their real elapsed times. 
        for(int i = 0; i < forRiderIds.length -1 ; i++){
            Duration firstDuration = totalTimeResults.get(forRiderIds[i]);
            Duration nextDuration = totalTimeResults.get(forRiderIds[i+1]);
            Duration difference = firstDuration.minus(nextDuration).abs(); 
            if(difference.compareTo(Duration.ofSeconds(1)) <=0 ){ // if the difference is 1 second or less
                totalTimeResults.put(forRiderIds[i], nextDuration); // rounds down the time of the largest time.
            }
        }
        
        Duration riderTime = totalTimeResults.get(riderId);
        
        // converting the Duration object to a LocalTime object
        long seconds = riderTime.getSeconds();
        long hours = seconds/3600;
        seconds = seconds % 3600;
        long minutes = seconds/60;
        seconds  = seconds%60;
        
        LocalTime AdjustedElapsedTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);
        
        return AdjustedElapsedTime;
        
    }

    /**
	 * Removes the stage results from the rider.
	 * <p>
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage foundStage = null;
        int stageCheckBit = 0;
        for(Stage stage : stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage associated with the stageId
                foundStage = stage;
                stageCheckBit = 1;
                break;
            }
        }
        
        int riderCheckBit = 0;
        Rider foundRider = null;
        for(Rider rider: riders){
            if(rider.getRiderID() == riderId){ // finds the rider associated with the riderId
                foundRider = rider;
                riderCheckBit = 1;
                break;
            }
        }
        
        if(stageCheckBit == 0 || riderCheckBit == 0){
            throw new IDNotRecognisedException("The Stage or Rider ID is invalid. Stage ID: " + stageId + ". Rider ID: " + riderId + ".");
        }
        
        HashMap<Integer, LocalTime[]>  results = foundStage.getStageResults();
        results.remove(riderId);
        foundStage.setStageResults(results);
    }

    /**
	 * Get the riders finished position in a a stage.
	 * <p>
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return A list of riders ID sorted by their elapsed time. An empty list if
	 *         there is no result for the stage.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        Stage foundStage = null;
        int stageCheckBit = 0;
        for(Stage stage : stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage associated with the stageId
                foundStage = stage;
                stageCheckBit = 1;
                break;
            }
        }
        
        if(stageCheckBit == 0){
            throw new IDNotRecognisedException("The Stage is invalid. Stage ID: " + stageId + ".");
        }
        
        HashMap<Integer, LocalTime[]> riderCheckpointTimes = foundStage.getStageResults(); // copies the results of riders and their checkpoint times into this hashmap
        
        for(Map.Entry<Integer,LocalTime[]> entry : riderCheckpointTimes.entrySet() ){
            Integer riderId = entry.getKey(); // gets the rider ID
            LocalTime[] checkpointTimes = entry.getValue(); // gets the array containing the checkpoint times
            
            Arrays.sort(checkpointTimes, Comparator.naturalOrder()); // sorts the array from earliest to latest time
            
            Duration totalDuration =  Duration.between(checkpointTimes[0], checkpointTimes[checkpointTimes.length-1]);
            
            // converting the Duration object to a LocalTime object
            long seconds = totalDuration.getSeconds();
            long hours = seconds/3600;
            seconds = seconds % 3600;
            long minutes = seconds/60;
            seconds  = seconds%60;
            
            LocalTime totalTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);
            
            foundStage.addTotalTimeResult(riderId, totalTime);
        }
        
        HashMap<Integer, LocalTime> riderTotalTimes = foundStage.getTotalStageResults(); //creates a new hashmap which will store the total times of the riders.
        
        Integer[] riderIds = riderTotalTimes.keySet().toArray(new Integer[riderTotalTimes.size()]); // creates an array the same size as the hashmap which stores all the rider IDs.
        LocalTime[] riderTimes = riderTotalTimes.values().toArray(new LocalTime[riderTotalTimes.size()]); // creates an array the same size as the hashmap which stores all the total rider times.
        
        //sorts the times using bubble sort
        int n = riderTimes.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (riderTimes[j].isAfter(riderTimes[j + 1])) { // Compare adjacent elements and swap if out of order
                    // Swapping the elements of both arrays, so that the rider ID indexes are updated. 
                    LocalTime temp = riderTimes[j];
                    riderTimes[j] = riderTimes[j + 1];
                    riderTimes[j + 1] = temp;
                    
                    Integer temp1 = riderIds[j];
                    riderIds[j] = riderIds[j+1];
                    riderIds[j+1] = temp1;
                    
                }
            }
        }
        
        int[] rankedIds = new int[riderIds.length];
        for(int i = 0; i < rankedIds.length; i++){
            rankedIds[i] = riderIds[i];
        }
        
        return rankedIds;
    }

    /**
	 * Get the adjusted elapsed times of riders in a stage.
	 * <p>
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of adjusted elapsed times sorted by their finish
	 *         time. An empty list if there is no result for the stage. These times
	 *         should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}. Assume the total elapsed time of
	 *         in a stage never exceeds 24h and, therefore, can be represented by a
	 *         LocalTime variable. There is no need to check for this condition or
	 *         raise any exception.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        Stage foundStage = null;
        int stageCheckBit = 0;
        for(Stage stage : stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage associated with the stageId
                foundStage = stage;
                stageCheckBit = 1;
                break;
            }
        }
        
        if(stageCheckBit == 0){
            throw new IDNotRecognisedException("The Stage is invalid. Stage ID: " + stageId + ".");
        }
        
        getRidersRankInStage(stageId); // runs the previous method, so that the total times are stored in the instance of the stage.
        
        
        HashMap<Integer, LocalTime> riderTotalTimes = foundStage.getTotalStageResults();
        
        LocalTime[] empty = {};
        if(riderTotalTimes.isEmpty()){ // if there is no result for the stage, an empty array is returned. 
            return empty;
        }
        
        
        Integer[] riderIds = riderTotalTimes.keySet().toArray(new Integer[riderTotalTimes.size()]); // creates an array the same size as the hashmap which stores all the rider IDs.
        LocalTime[] riderTimes = riderTotalTimes.values().toArray(new LocalTime[riderTotalTimes.size()]); // creates an array the same size as the hashmap which stores all the total rider times.
        
        //sorts the times using bubble sort
        int n = riderTimes.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (riderTimes[j].isAfter(riderTimes[j + 1])) { // Compare adjacent elements and swap if out of order
                    // Swapping the elements of both arrays, so that the rider ID indexes are updated. 
                    LocalTime temp = riderTimes[j];
                    riderTimes[j] = riderTimes[j + 1];
                    riderTimes[j + 1] = temp;
                    
                    Integer temp1 = riderIds[j];
                    riderIds[j] = riderIds[j+1];
                    riderIds[j+1] = temp1;
                    
                }
            }
        }
        
        for(int i = 0; i < riderIds.length; i++){
            int riderId = riderIds[i];
            LocalTime riderTime = riderTimes[i];
            foundStage.addTotalTimeResult(riderId, riderTime); // stores the total time results to the stage, so that they can be accessed by other methods
        }
        
        //checks if there is a 1 second difference or less between two consecutive times
        for(int i = 1; i<riderTimes.length; i++){
            Duration duration = Duration.between(riderTimes[i-1], riderTimes[i]);
            if(duration.toSeconds() <= 1){
                riderTimes[i] = riderTimes[i-1]; // since they are sorted, only the successor can be 1 second or less closer, so it is rounded down. 
            }
        }
        
        return riderTimes;
        
    }

    /**
	 * Get the number of points obtained by each rider in a stage.
	 * <p>
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of points each riders received in the stage, sorted
	 *         by their elapsed time. An empty list if there is no result for the
	 *         stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage foundStage = null;
        int stageCheckBit = 0;
        for(Stage stage : stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage associated with the stageId
                foundStage = stage;
                stageCheckBit = 1;
                break;
            }
        }
        
        if(stageCheckBit == 0){
            throw new IDNotRecognisedException("The Stage is invalid. Stage ID: " + stageId + ".");
        }
        
        getRidersRankInStage(stageId); // runs the before the previous method, so that the total times are stored in the instance of the stage.
        
        int[] empty = {};
        if(foundStage.getTotalStageResults().isEmpty()){ // checks if there is a result for the stage, if not, an empty array is returned
            return empty;
        }
        
        int[] riderSortedIds = getRidersRankInStage(stageId); // assigns the sorted rider IDs by time to this array. 
        
        //points distibution according to figure 1 in coursework spec.
        int[] riderPointsFLAT = {50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};
        int[] riderPointsMM = {30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
        int[] riderPointsHM = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] riderPointsTT = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] riderPointsIS = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        
        
        ArrayList<Integer> riderPoints = new ArrayList<>(); //arraylist that will store 
        
        //finding the type of the stage and addressing the points accordingly according to the final finish without any sprints taken into account.
        
        for(int i = 0; i<riderSortedIds.length && i <= 15; i++){
            if(foundStage.getStageType() == StageType.FLAT){
                riderPoints.add(riderPointsFLAT[i]);
            }else if(foundStage.getStageType() == StageType.MEDIUM_MOUNTAIN){
                riderPoints.add(riderPointsMM[i]);
            }else if(foundStage.getStageType() == StageType.HIGH_MOUNTAIN){
                riderPoints.add(riderPointsHM[i]);
            }else if(foundStage.getStageType() == StageType.TT){
                riderPoints.add(riderPointsTT[i]);
            }
        }
        
        /*
        ArrayList<Checkpoint> checkpointsInStage = foundStage.getStageCheckpoints(); // stores the checkpoints of the stage
        ArrayList<Integer> indexOfIS = new ArrayList<>(); // new arraylist used to store the index of any Intermediate Sprint checkpoints in the stage
        
        //adding the indexes of all sprints into the arraylist.
        int j = 0;
        for(Checkpoint i : checkpointsInStage){
            if(i.getCheckpointType() == CheckpointType.SPRINT){
                indexOfIS.add(j);
                j++;
            }else{
                j++;
            }
        }
        
        HashMap<Integer, LocalTime[]> checkpointID_Times = foundStage.getStageResults();
        Integer[] riderIds = checkpointID_Times.keySet().toArray(new Integer[checkpointID_Times.size()]); // creates an array the same size as the hashmap which stores all the rider IDs.
        
        // creates an array which will store arrays of localtime objects for each rider. 
        LocalTime[][] checkpointTimesArray = new LocalTime[checkpointID_Times.size()][];
        int index = 0;
        for (LocalTime[] checkpointTimes : checkpointID_Times.values()) {
            checkpointTimesArray[index++] = checkpointTimes;
        }
        
        
        ArrayList<LocalTime> sprintTimes = new ArrayList<>(); // will store all times of intermediate sprints.
        for(int i = 0; i < checkpointTimesArray.length; i++){
            for(int l = 1; l < checkpointsInStage.size() - 1; l++ ){
                if(indexOfIS.contains(l)){
                    sprintTimes.add(checkpointTimesArray[i][l]); // adds all the intermediate sprints to the arraylist
                }
            }
        }
        
        //sorts the times using bubble sort
        int n = sprintTimes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int l = 0; l < n - i - 1; l++) {
                if (sprintTimes.get(l).isAfter(sprintTimes.get(l+1))) { // Compare adjacent elements and swap if out of order
                    // Swapping the elements of both arrays, so that the rider ID indexes are updated. 
                    LocalTime temp = sprintTimes.get(l);
                    sprintTimes.set(l, sprintTimes.get(l+1));
                    sprintTimes.set(l+1, temp);
                    
                    Integer temp1 = riderIds[j];
                    riderIds[j] = riderIds[j+1];
                    riderIds[j+1] = temp1;
                    
                }
            }
        }
        
        ArrayList<Integer> pointsIS = new ArrayList<>();
        

        
        */
        
        
        int[] int_riderPoints = new int[riderPoints.size()];
        for(int i = 0; i < riderPoints.size(); i++){
            int_riderPoints[i] = riderPoints.get(i);
        }
        
        return int_riderPoints;
    }

    /**
	 * Get the number of mountain points obtained by each rider in a stage.
	 * <p>
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of mountain points each riders received in the stage,
	 *         sorted by their finish time. An empty list if there is no result for
	 *         the stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage foundStage = null;
        int stageCheckBit = 0;
        for(Stage stage : stagesImpl){
            if(stage.getStageID() == stageId){ // finds the stage associated with the stageId
                foundStage = stage;
                stageCheckBit = 1;
                break;
            }
        }
        
        if(stageCheckBit == 0){
            throw new IDNotRecognisedException("The Stage is invalid. Stage ID: " + stageId + ".");
        }
        
        getRidersRankInStage(stageId); // runs the before the previous method, so that the total times are stored in the instance of the stage.
        
        int[] empty = {};
        if(foundStage.getTotalStageResults().isEmpty()){ // checks if there is a result for the stage, if not, an empty array is returned
            return empty;
        }
        
        int[] mount_4C = {1};
        int[] mount_3C = {2,1};
        int[] mount_2C = {5,3,2,1};
        int[] mount_1C = {10,8,6,4,2,1};
        int[] mount_HC = {20,15,12,10,8,6,4,2};
        
        
        return empty;
    }

    /**
	 * Method empties this MiniCyclingPortal of its contents and resets all
	 * internal counters.
	 */
    @Override
    public void eraseCyclingPortal() {
        //removes all stages and results from races and resets ID counter to 1
        for(Race race : races){
            race.deleteRace();
        }
        
        races.clear(); //removes all races from the portal
        
        
        //removes all checkpoints and results from stages and resets ID counter to 1
        for(Stage stage : stagesImpl){
            stage.deleteStage();
        }
        
        stagesImpl.clear(); //removes all stages from the portal 
        
        
        //resets the internal ID counter of all checkpoints to 1.
        for(Checkpoint cp : checkpoints){
            cp.deleteCP();
        }
        
        checkpoints.clear(); //removes all checkpoints from the portal
        
        
        //removes all riders from the team and resets ID counter to 1
        for(Team team : teams){
            team.deleteteam();
        }
        
        teams.clear(); //removes all stages from the portal
        
        //resets all rider counters to 1.
        for(Rider rider : riders){
            rider.deleteRider();
        }
        
        riders.clear(); //removes all riders from the portal
        
    }

    /**
	 * Method saves this MiniCyclingPortal contents into a serialised file,
	 * with the filename given in the argument.
	 * <p>
	 *
	 * @param filename Location of the file to be saved.
	 * @throws IOException If there is a problem experienced when trying to save the
	 *                     store contents to the file.
	 */
    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        
        ObjectOutputStream objectOut = null;
        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
            objectOut = new ObjectOutputStream(fileOut); // Write the portal  to the file
        
            objectOut.writeObject(this); // "this" refers to the current portal
            System.out.println("The cycling portal has saved to " + filename);
        } catch (IOException e) {
            // If an IOException occurs, it is thrown
            throw e;
        } finally {
            // Close the stream in the finally statement to ensure they are closed even if an exception occurs
            try {
                if (objectOut != null) {
                    objectOut.close();
                }
            } catch (IOException ex) {
                

            }
        }
    
    }

    /**
	 * Method should load and replace this MiniCyclingPortal contents with the
	 * serialised contents stored in the file given in the argument.
	 * <p>
	 *
	 * @param filename Location of the file to be loaded.
	 * @throws IOException            If there is a problem experienced when trying
	 *                                to load the store contents from the file.
	 * @throws ClassNotFoundException If required class files cannot be found when
	 *                                loading.
	 */
    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        CyclingPortalImpl portal = null;
        
        try (FileInputStream fileIn = new FileInputStream(filename);
         ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
        
        Object obj = objectIn.readObject(); // Read the portal object from the file and assign it to the current instance
        if(obj instanceof CyclingPortalImpl){
            portal = (CyclingPortalImpl) obj;
            
            //assigns all attributes which hold all objects to this instances attributes
            this.races = portal.races;
            this.stagesImpl = portal.stagesImpl;
            this.checkpoints = portal.checkpoints;
            this.teams = portal.teams;
            this.riders = portal.riders;
        }

        System.out.println("Cycling portal has been loaded from " + filename);
    } catch (IOException | ClassNotFoundException e) { // catches any exception
        
        System.err.println("Error occurred while loading the cycling portal: " + e.getMessage());
        throw e; // throw the caught exception
    }

   }
}