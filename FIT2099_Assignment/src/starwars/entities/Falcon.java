package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.FlyToDeath;
import starwars.actions.FlyToYavin;
import starwars.actions.FlyToMain;

public class Falcon extends SWEntity{

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
