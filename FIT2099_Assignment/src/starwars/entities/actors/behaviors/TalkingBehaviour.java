package starwars.entities.actors.behaviors;

import java.util.*;

import starwars.TRandom;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Speak;

public class TalkingBehaviour extends BehaviourInterface {

    private ArrayList<String> quotes;
    
    public TalkingBehaviour(SWActor actor, SWWorld world, ArrayList<String> quotes) {
    	super(actor, world);
    	this.quotes = quotes;
    }

    @Override
    public boolean ExecuteBehaviour() {
    	actor.schedule(new Speak(messageRenderer, TRandom.itemFrom(quotes)));
    	return true;
    }
    
}