package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntityInterface;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.Capability;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.actions.Train;
import starwars.actions.Healing;
import starwars.entities.LightSaber;
import starwars.entities.actors.SWOrganicActor;
import starwars.entities.actors.behaviors.AttackNeighboursBehaviour;
import starwars.entities.actors.behaviors.PatrolBehaviour;
import starwars.entities.actors.behaviors.TrainerBehaviour;

/**
 * Ben (aka Obe-Wan) Kenobi.  
 * 
 * At this stage, he's an extremely strong critter with a <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * 
 * He has a Train affordance and is able to train the Player in order to increase the Player's force level.
 * 
 * Note that you can only create ONE Ben, like all SWLegends.
 * @author rober_000
 *
 */
public class BenKenobi extends SWOrganicActor {

	private static BenKenobi ben = null; // yes, it is OK to return the static instance!
	private MessageRenderer m;
	private boolean taken;
	private SWEntityInterface benSS;	// ben's original holding stuff
	
	/**
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world world the <code>SWWorld</code> world to which this <code>SWOrganicActor</code> belongs to
	 * @param moves patrol moves for the <code>SWOrganicActor</code>.
	 */
	
	
	private BenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.GOOD, 800, m, world);
		this.m = m;
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		SWEntity bensweapon = new LightSaber(m);
		bensweapon.removeAffordance(Take.class);
		bensweapon.addAffordance(new Leave(bensweapon, m));
		setItemCarried(bensweapon);
		this.taken = false;
		this.benSS = null;

		this.addAffordance(new Train(this, m));
		behaviours.add(new TrainerBehaviour(this, world));
		behaviours.add(new AttackNeighboursBehaviour(this, world, m, true, true, "%s suddenly looks sprightly and attacks %2s", null, 0.0));
		behaviours.add(new PatrolBehaviour(this, world, moves));
	}

	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		if(ben == null){
			ben = new BenKenobi(m, world, moves);
		}
		return ben;
	}
	
    @Override
    protected void executeBehaviours() {
	//say(describeLocation()); // Too verbose, good for debugging though.
    	super.executeBehaviours();
    }

    /**
     * Ben Kenobi checks for entities in his location
     * <ul>
     * <li>If there is an entity that is drinkable, and the level is not 0, 
     * returns there is a canteen available at Ben's location.</li>
     * 
     * <li>If Ben Kenobi is already holding a drinkable, 
     * returns available canteen as false at Ben's location.</li>
     * 
     * <li>If a canteen exists and Ben Kenobi's hitpoint is not maximum, 
     * and he is not holding a canteen, make Ben take the canteen, if he is holding something,
     * make Ben leave the item and take the canteen (in a single turn).</li>
     * 
     * <li>If Ben is already holding the canteen, and his hitpoint is not maximum, 
     * Ben heals himself.</li>
     * 
     * <li>If Ben has healed himself or if the canteen's level reaches 0,
     * make Ben leave the canteen and take his lightsaber (in a single turn).</li>
     * 
     * </ul>
     */
	@Override
	public void act() {
		
		boolean isCanteen = false;
		SWEntityInterface fullCan = null;
		boolean done = false;
		
		SWLocation location = this.world.getEntityManager().whereIs(this);

		if(isDead()) {
			return;
		}
		
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		
		if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
			for (SWEntityInterface entity : contents) {
				if (entity != this && !(entity instanceof SWActor)){
					if (entity.hasCapability(Capability.DRINKABLE) == true){ // don't include self in scene description
						fullCan = entity;
						if(entity.getLevel() != 0){
							isCanteen = true;
						}
					}
				}
			}
		}
		
		//if Ben Kenobi is already holding a drinkable, returns available canteen in surroundings as false
		if (this.getItemCarried()!=null){
			if (this.getItemCarried().hasCapability(Capability.DRINKABLE)){
				isCanteen = false;
			}
		}
		
		
		//if a canteen exist and ben's hitpoint is not maximum and he is not holding a canteen
		if(isCanteen && this.getHitpoints()!= this.getmaxHitpoints() && !taken){
			if (this.getItemCarried() == null && !(taken)){
				Take theCan = new Take(fullCan,m);
				this.taken = true;
				scheduler.schedule(theCan, this, 1);
				done = true;
			}
			else{
				this.benSS = this.getItemCarried();      // to store whatever Ben is holding previously
				Leave byeItem = new Leave(this.getItemCarried(), m);
				scheduler.schedule(byeItem, this, 0);
				
				Take theCan = new Take(fullCan,m);
				this.taken = true;
				scheduler.schedule(theCan, this, 1);
				done = true;
			}
		}
		
		//when ben is holding a centeen.
		else if (taken && this.getHitpoints()!= this.getmaxHitpoints() && this.getItemCarried().getLevel() > 0){
			Healing heal = new Healing(ben, m);
			scheduler.schedule(heal, this, 1);
			done = true;
		}
		
		//when his hitpoints are fully recovered and he is holding a canteen. Drop canteen and pick up his light saber.
		// when the canteen level <=0 drop canteen and pick up whatever he left.
		else if((this.getHitpoints() == this.getmaxHitpoints() && this.getItemCarried().hasCapability(Capability.DRINKABLE)) || (taken && this.getItemCarried().getLevel() <= 0)){
			Leave byecanteen = new Leave(this.getItemCarried(), m);
			taken = false;
			scheduler.schedule(byecanteen, this, 0);
			done = true;
			
			if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
				for (SWEntityInterface entity : contents) {
					if (entity != this && !(entity instanceof SWActor)){
						if (entity == this.benSS){ // check is Ben's previous belongings is still around 
							Take benbuddy= new Take(benSS,m);
							scheduler.schedule(benbuddy, this, 1);
						}
					}
				}
			}
		}
		
		if (!done){
			executeBehaviours();
		}
	}

}
