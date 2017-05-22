package starwars;

import starwars.SWActor;

public interface SWRobotsInterface {
    boolean hasOwner();

    void setOwner(SWActor newOwner);
}
