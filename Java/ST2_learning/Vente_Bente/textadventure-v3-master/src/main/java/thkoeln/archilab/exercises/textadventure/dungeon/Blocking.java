package thkoeln.archilab.exercises.textadventure.dungeon;

import thkoeln.archilab.exercises.textadventure.domainprimitives.Printable;

public interface Blocking extends Printable {
    public void place( Field field ) throws AlreadyBlockedException;
}
