package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.TalkingBehaviour;

public class C3PO extends SWRobots {
	
	/**
	 * A robot called C-3PO, who remains still when it's not owned.
	 * He has a 10% chance of talking from a list of quotes.
	 * 
	 * @param hitpoints the hit points of this <code>C3PO</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>C-3PO</code> belongs to
	 */

	public C3PO(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);
		
		this.setShortDescription("C-3PO");
		this.setLongDescription("C-3PO, Protocol Droid");
		this.setSymbol("CP");
		
		ArrayList<String> possibleQuotes = new ArrayList<String>();
		possibleQuotes.add("Don't call me a mindless philosopher, you overweight glob of grease!");
		possibleQuotes.add("This sandStorm is killing me.");
		possibleQuotes.add("I've forgotten how much I hate space travel.");
		possibleQuotes.add("This is suicide.");
		possibleQuotes.add("If I told you half the things I've heard about this Jabba the Hutt, you'd probably short circuit.");
		possibleQuotes.add("Something's not right because now I can't see.");
		
		behaviours.add(followBehaviour);
		behaviours.add(new TalkingBehaviour(this, world, possibleQuotes, 0.9));
	}

}
