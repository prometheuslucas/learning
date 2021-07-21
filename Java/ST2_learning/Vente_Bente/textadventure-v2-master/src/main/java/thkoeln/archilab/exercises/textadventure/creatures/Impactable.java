package thkoeln.archilab.exercises.textadventure.creatures;

import thkoeln.archilab.exercises.textadventure.fight.Impact;
import thkoeln.archilab.exercises.textadventure.fight.Strength;

public interface Impactable {

    /**
     * Receive an external impact (positive by healing, or negative by attack)
     * @param impact if positive, it is a healing, otherwise it is an attack
     */
    public void receiveImpact( Impact impact );

    /**
     * @return Maximum strength when fully healthy.
     */
    public Strength getStrength();

    /**
     * @return true if strength = 0
     */
    public boolean isDead();
}
