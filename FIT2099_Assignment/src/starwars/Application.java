package starwars;

import edu.monash.fit2099.simulator.time.Scheduler;
import starwars.swinterfaces.SWGridController;

/**
 * Driver class for the Star Wars package with <code>GridController</code>.  Contains nothing but a main().
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02	The TextInterface handles the responsibly of displaying the grid not the SWGrid or SWWorld classes (asel)
 * 2017-02-10	GridController controls the interactions with the user and will determine which UI it should use to do this. 
 * 			    Therefore there is tight coupling with the user interfaces and the driver. The application no longer has to worry about the
 * 				UI(asel)
 * 2017-02-19	Removed the show banner method. The text interface will deal with showing the banner. (asel)
 */

public class Application {
	public static void main(String args[]) {
		
		/**
		 * A boolean called test is added to check if the game is won or lost,
		 * while the game has not been won or lost, it will keep running the world,
		 * displaying the user interface(enable user commands) and hence the game.
		 * When the game has been won or lost, it will print out a message depending on 
		 * whether is it a win or lose, and the loop breaks
		 * therefore the game will end.
		 * 
		 */
		SWWorld world = new SWWorld();
		boolean test = world.getRun();
		
		//Grid controller controls the data and commands between the UI and the model
		SWGridController uiController = new SWGridController(world);
		
		Scheduler theScheduler = new Scheduler(1, world);
		SWActor.setScheduler(theScheduler);
		
		// set up the world
		world.initializeWorld(uiController);
	
		// kick off the scheduler
		while(test) {
			test = world.getRun();
			uiController.render();
			theScheduler.tick();
		}
		
		
		if (!test){
			System.out.println("****************************");
			System.out.println(world.getENDGame());
		}
		
		
	}
	
	

}
