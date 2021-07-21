package thkoeln.archilab.exercises.textadventure.dungeon;

import thkoeln.archilab.exercises.textadventure.Printable;

public interface Blocking extends Printable {
    public void place( Field field ) throws AlreadyBlockedException;
}
