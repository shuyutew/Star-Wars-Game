
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
	
	@Override
	public void act() {
		
		SWLocation location = this.world.getEntityManager().whereIs(this);
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		System.out.println(contents.size());
	
		if (isDead()) {
			return;
		}
		if (contents.size() > 1) { // check who is accompanying the Player 
			for (SWEntityInterface entity : contents) {
				if (entity != this && entity instanceof SWActor){
					if (entity.getShortDescription() == "Luke"){ 
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
