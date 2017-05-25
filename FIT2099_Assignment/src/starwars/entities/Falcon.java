package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;

public class Falcon extends SWEntity{

	public Falcon(MessageRenderer m) {
		super(m);
		
		this.setLongDescription("A Millennium Falcon.");
		this.setShortDescription("A Millennium Falcon, ");
		this.setSymbol("M");
		
		//this.addAffordance(new FlyToDeathStar());
	}
	
	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}

}
