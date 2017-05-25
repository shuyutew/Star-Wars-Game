/**
 * 
 */
package starwars.entities.actors;
import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.TalkingBehaviour;

public class AdmiralAckbar extends SWOrganicActor {

	/**
	 * Create a new character called Admiral Ackbar, who works at the 
	 * headquarters of the Rebel Alliance which is located on the moon Yavin IV
	 * He needs to stay still at the headquarters and has a 10% chance of saying "It's a trap!"
	 * 
	 * @param hitpoints the hit points of this <code>SWOrganicActor</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>SWOrganicActor</code> belongs to
	 */
	public AdmiralAckbar(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);

		this.setShortDescription("Admiral Ackbar");
		this.setLongDescription("Admiral Ackbar from the Rebel Alliance.");
		this.setSymbol("A*");
		
		ArrayList<String> onelineQuote = new ArrayList<String>();
		onelineQuote.add("It's a trap!");
		
		behaviours.add(new TalkingBehaviour(this, world, onelineQuote, 0.9));
	}

    @Override
    protected void executeBehaviours() {
	//say(describeLocation()); // Too verbose, good for debugging though.
    	super.executeBehaviours();
    }
	
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		executeBehaviours();
	}
	
	
}
