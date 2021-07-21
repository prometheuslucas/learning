package thkoeln.archilab.exercises.textadventure.dungeon;

import thkoeln.archilab.exercises.textadventure.command.CommandException;

public class AlreadyBlockedException extends CommandException {
    AlreadyBlockedException( String message ) {
        super( message );
    }
}
