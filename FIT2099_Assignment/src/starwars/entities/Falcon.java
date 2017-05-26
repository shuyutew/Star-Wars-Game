package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.FlyToDeath;
import starwars.actions.FlyToYavin;
import starwars.actions.FlyToMain;

public class Falcon extends SWEntity{
	
	/**
	 * The Millenium Falcon is a spaceship that can transport the <code>Player</code>
	 * and his travelling companions to other maps
	 * It is placed on each maps that it can be flown to.
	 *
	 * @param m <code>MessageRenderer</code> to display messages.
	 */

	public Falcon(MessageRenderer m) {
		super(m);
		
		this.setLongDescription("A Millennium Falcon.");
		this.setShortDescription("A Millennium Falcon, ");
		this.setSymbol("M");
		
		this.addAffordance(new FlyToDeath(this,m));
		this.addAffordance(new FlyToYavin(this,m));
		this.addAffordance(new FlyToMain(this,m));
	}
	
	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}

}
