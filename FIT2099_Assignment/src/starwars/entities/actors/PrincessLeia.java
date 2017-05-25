package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.Team;
import starwars.entities.actors.SWOrganicActor;
import starwars.entities.actors.behaviors.FollowBehaviour;

public class PrincessLeia extends SWOrganicActor{
	
	private boolean meeted = false;
	
	/**
	 * Princess Leia Organa
	 * 
	 * At the beginning of the game, Princess Leia, a General of the Rebel forces 
	 * is a prisoner sitting motionlessly at the edge of Death Star. 
	 * She has medium Force Ability Level, hence cannot be mind controlled.
	 * If the princess dies, the game is lost.
	 * If Luke carries both Leia and R2-D2 to the Rebel Headquarters on Yavin IV,
	 * the game is won.
	 * 
	 * @param hitpoints the hit points of this <code>SWOrganicActor</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>SWOrganicActor</code> belongs to
	 */

	public PrincessLeia(MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 600, m, world);
		
		this.setShortDescription("Leia");
		this.setLongDescription("Leia, Princess Leia Organa");
		this.setSymbol("%");

	}
	
    @Override
    protected void executeBehaviours() {
	//say(describeLocation()); // Too verbose, good for debugging though.
    	super.executeBehaviours();
    }
	
    /**
     * Checks what other entities/actors are at Princess Leia's location,
     * if it is Luke, mark 'meeted' as true, and
     * give Leia a FollowBehaviour so she will follow Luke.
     * when 'meeted' is true, she will execute the behaviour.
     * 
     * When Leia isDead(), it takes one round to check whether is she died or not. Then
     * another round to check whether the "run" boolean in SWWorld is true or false, thus, there
     * will be a game lag of 2 rounds before the game will end and prints out "Game Over! Princess Leia has died!".
     */

	@Override
	public void act() {
		
		SWLocation location = this.world.getEntityManager().whereIs(this);
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		
		if (isDead()) {
			world.setNotRun();
			world.setEndGame("Game Over! Princess Leia has died!");
			return;
		}
		
		if (meeted){
			executeBehaviours();
		}
		
		else if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
			for (SWEntityInterface entity : contents) {
				if (entity != this && entity instanceof SWOrganicActor){
					if (entity.getShortDescription() == "Luke"){ // check if entity is Luke
						this.behaviours.add(new FollowBehaviour(this, this.world, (SWOrganicActor)entity));
						executeBehaviours();
						meeted = true;
						return;
					}
				}
			}
		}
	
	}
}
