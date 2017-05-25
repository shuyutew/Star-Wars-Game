
package starwars.entities.actors;
import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;

public class MonMothma extends SWActor {

	/**
	 * Create a new character called Mon Mothma, who works at the headquarters of the Rebel Alliance
	 * which is located on the moon Yavin IV
	 * He needs to stay still at the headquarters and will tell Luke 
	 * to bring the necessary characters to the Yavin IV in order to win
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	public MonMothma(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);

		this.setShortDescription("Mon Mothma");
		this.setLongDescription("Mon Mothma from the Rebel Alliance.");
		this.setSymbol("M*");
	}
	
	/**
	 * Checks what are around the Yavin IV, if Luke is found on the map,
	 * check whether Luke owns R2-D2 and whether Princess Leia is following him using boolean statuses,
	 * If both are true, the game is won, otherwise, this actor will tell Luke to get
	 * R2-D2 and Leia to the moon.
	 */
	@Override
	public void act() {
		
		List<SWLocation> locations = this.getWorld()
		// SWLocation location = this.world.getEntityManager().whereIs(this);
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		System.out.println(contents.size());
	
		if (isDead()) {
			return;
		}
		if (contents.size() > 1) { // // if it is equal to one, the only thing here is this Player, so there is nothing to report
			for (SWEntityInterface entity : contents) {
				if (entity != this && entity instanceof SWActor){
					if (entity.getShortDescription() == "Luke"){ //check who is accompanying the Player 
						SWActor a = (SWActor) entity;
						if (a.getDroidOwned().getShortDescription() == "R2-D2" && a.princesshere() == true){
						say("Good job");
						}		
					else{
						say(nag());
						}
					}
				}
			}
		}
	}
	
	private String nag() {
		return this.getShortDescription() + " yells: 'What are you doing here, farmboy? Bring us General Organa and the plans!'";
	}
		
}
