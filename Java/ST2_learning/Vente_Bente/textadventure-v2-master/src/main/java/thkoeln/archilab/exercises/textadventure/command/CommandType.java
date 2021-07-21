package thkoeln.archilab.exercises.textadventure.command;

import java.util.Random;

public enum CommandType {
    s, e, w, n, u, t, d;

    public boolean isMoveCommand() {
        return ( this == s || this == n || this == e || this == w );
    }

    public static CommandType randomDirection() {
        Random random = new Random();
        int index = random.nextInt(4 );
        return CommandType.values()[index];
    }
}
