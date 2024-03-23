package cycling;

import cycling.BadMiniCyclingPortalImpl;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import cycling.MiniCyclingPortal;
import cycling.CyclingPortalImpl;
import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortal interface -- note you
 * will want to increase these checks, and run it on your CyclingPortalImpl class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 2.0
 */
public class CyclingPortalTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
     * @throws cycling.IllegalNameException
     * @throws cycling.InvalidNameException
     * @throws cycling.IDNotRecognisedException
     * @throws cycling.InvalidLengthException
     * @throws cycling.InvalidLocationException
     * @throws cycling.InvalidStageStateException
     * @throws cycling.InvalidStageTypeException
     * @throws cycling.DuplicatedResultException
     * @throws cycling.InvalidCheckpointTimesException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException, DuplicatedResultException, InvalidCheckpointTimesException, IOException, ClassNotFoundException {
		System.out.println("The system compiled and started the execution...");
                
		// TODO replace BadMiniCyclingPortalImpl by CyclingPortalImpl
		MiniCyclingPortal portal1 = new CyclingPortalImpl();
		MiniCyclingPortal portal2 = new CyclingPortalImpl();
                
                portal1.createRace("Race1", "This is a race");
                portal1.createRace("Race2", "This is another race");
                portal1.createRace("Race3", "This is THE race");
                
                int[] IDs = portal1.getRaceIds();
                
                System.out.println("Before Race IDs:");
                for (int id : IDs) {
                System.out.println(id);
                }
                
                String raceDescription = portal1.viewRaceDetails(2);
                System.out.println(raceDescription);
                
                portal1.removeRaceById(1);
                
                
                portal1.addStageToRace(2, "Stage1", "This is a stage", 7 , LocalDateTime.now(), StageType.FLAT);
                portal1.addStageToRace(2, "Stage2", "This is another stage", 10 , LocalDateTime.now(), StageType.TT);
                portal1.addStageToRace(2, "Stage3", "This is another stage", 10 , LocalDateTime.now(), StageType.HIGH_MOUNTAIN);
                
                IDs = portal1.getRaceIds();
                System.out.println("After Race IDs:");
                for (int id : IDs) {
                    System.out.println(id);
                }
                
                int numStages = portal1.getNumberOfStages(2);
                System.out.println("the number of stages is "+ numStages);
                
                
                int[] raceStages = portal1.getRaceStages(2);
                for(int i : raceStages){
                    System.out.println(i);
                }
                
                double stageNumber = portal1.getStageLength(3);
                System.out.println("The length of your stage is " + stageNumber);
                
                portal1.removeStageById(2);
                int[] raceStagess = portal1.getRaceStages(2);
                for(int i : raceStagess){
                    System.out.println(i);
                }
                
                portal1.addCategorizedClimbToStage(3, 3.0, CheckpointType.C4, 4.7, 4.0);
                portal1.addCategorizedClimbToStage(3, 2.0, CheckpointType.C4, 4.7, 7.0);
                portal1.addCategorizedClimbToStage(3, 5.0, CheckpointType.C4, 4.8, 4.0);
                portal1.addCategorizedClimbToStage(3, 7.0, CheckpointType.C4, 4.7, 2.0);
                portal1.addCategorizedClimbToStage(1, 6.0, CheckpointType.C3, 3.2, 1.0);
                
                
                
                
                System.out.println("___________________");
                int[] inOrder = portal1.getStageCheckpoints(3);
                for(int i : inOrder){
                    System.out.println(i);
                }
                
                System.out.println("__________________");
                portal1.createTeam("Team1", "this is a team");
                portal1.createTeam("Team2", "this is another team");
                portal1.createTeam("Team3", "this is an other another team");
                int[] teamsLength = portal1.getTeams();
                System.out.println(teamsLength.length);
                
                System.out.println("__________________");
                //test getTeamRiders, createRdier, other rider stuff
                portal1.removeTeam(1);
                teamsLength = portal1.getTeams();
                System.out.println(teamsLength.length);
                
                System.out.println("__________________");
                portal1.createRider(2, "Joseph Washington", 2004);
                portal1.createRider(2, "Malnor Roberts", 1998);
                portal1.createRider(2, "Georgie Walnuts", 1998);
                //portal1.removeRider(3);
                int[] nummm = portal1.getTeamRiders(2);
                System.out.println(nummm.length);
                
                System.out.println("__________________");
                LocalTime[] times3 = {
                    LocalTime.of(8, 45, 20),
                    LocalTime.of(12, 15, 5),
                    LocalTime.of(17, 30, 40),
                    LocalTime.of(21, 10, 16),
                    LocalTime.of(3, 55, 50),
                    LocalTime.of(2, 12, 5)
                    };
                LocalTime[] times2 = {
                    LocalTime.of(5, 45, 20),
                    LocalTime.of(12, 15, 5),
                    LocalTime.of(16, 30, 40),
                    LocalTime.of(11, 10, 16),
                    LocalTime.of(3, 20, 50),
                    LocalTime.of(8, 12, 5)
                    };
                
                LocalTime[] times1 = {
                    LocalTime.of(5, 45, 20),
                    LocalTime.of(12, 15, 5),
                    LocalTime.of(16, 30, 40),
                    LocalTime.of(11, 10, 16),
                    LocalTime.of(3, 20, 50),
                    LocalTime.of(8, 12, 6)
                    };
        
                portal1.concludeStagePreparation(3);
                portal1.concludeStagePreparation(1);
                portal1.registerRiderResultsInStage(3, 1, times3);
                portal1.registerRiderResultsInStage(3, 3, times2);
                portal1.registerRiderResultsInStage(3, 2, times1);
                System.out.println("_____._________");
                LocalTime[] riderTime = portal1.getRiderResultsInStage(3, 2);
                for(LocalTime time: riderTime){
                    System.out.println(time);
                }
                
                System.out.println("________Here__________");
                LocalTime correct = portal1.getRiderAdjustedElapsedTimeInStage(3, 2);
                LocalTime correct1 = portal1.getRiderAdjustedElapsedTimeInStage(3, 1);
                System.out.println(correct);
                System.out.println(correct1);
                
                System.out.println("__________________");
                portal1.deleteRiderResultsInStage(1, 1);
                LocalTime[] riderTimee = portal1.getRiderResultsInStage(1, 1);
                for(LocalTime time: riderTimee){
                    System.out.println(time);
                }
                
                //portal1.removeRider(1);
                
                
                System.out.println("_______RANK TEST___________");
                int[] results = portal1.getRidersRankInStage(3);
                for(int ID : results){
                    System.out.println(ID);
                }
                
                System.out.println("_______TIME TEST___________");
                LocalTime[] resultsTime = portal1.getRankedAdjustedElapsedTimesInStage(3);
                for(LocalTime ID : resultsTime){
                    System.out.println(ID);
                }
                
                System.out.println("_______POINTS TEST___________");
                int[] points = portal1.getRidersPointsInStage(3);
                for(int i : points){
                    System.out.println(i);
                }
                
                System.out.println("_______SAVE TEST___________");
                portal1.saveCyclingPortal("cycling_portal1.ser");
                
                System.out.println("_______ERASE TEST___________");
                portal1.eraseCyclingPortal();
                
                System.out.println("_______LOAD TEST___________");
                portal1.loadCyclingPortal("cycling_portal1.ser");
                
                System.out.println("_______LOADED TEST___________");
                int[] spoints = portal1.getRidersPointsInStage(3);
                for(int i : spoints){
                    System.out.println(i);
                }
                
                
                
    }
}


