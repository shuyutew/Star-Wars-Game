package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWRobots;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.TalkingBehaviour;

public class C3PO extends SWRobots {

	public C3PO(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);
		
		this.setShortDescription("C-3PO");
		this.setLongDescription("C-3PO, Protocol Droid");
		this.setSymbol("??");
		
		ArrayList<String> possibleQuotes = new ArrayList<String>();
		possibleQuotes.add("Don't call me a mindless philosopher, you overweight glob of grease!");
		possibleQuotes.add("This sandStorm is killing me.");
		possibleQuotes.add("I've forgotten how much I hate space travel.");
		possibleQuotes.add("This is suicide.");
		possibleQuotes.add("If I told you half the things I've heard about this Jabba the Hutt, you'd probably short circuit.");
		possibleQuotes.add("Something's not right because now I can't see.");
		
		behaviours.add(new TalkingBehaviour(this, world, possibleQuotes));
		
	}

}
