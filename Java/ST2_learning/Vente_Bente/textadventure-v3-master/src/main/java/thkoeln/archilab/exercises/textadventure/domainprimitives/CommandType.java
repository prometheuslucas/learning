package thkoeln.archilab.exercises.textadventure.domainprimitives;

import java.util.Random;

public enum CommandType {
    s, e, w, n, u, t, d, q, r;

    public boolean isMoveCommand() {
        return ( this == s || this == n || this == e || this == w );
    }
    public boolean isPlayerCommand() {
        return ( this != q && this != r  );
    }
    public boolean isMetaCommand() {
        return ( this == q || this == r  );
    }

    public static CommandType randomDirection() {
        Random random = new Random();
        int index = random.nextInt(4 );
        return CommandType.values()[index];
    }
}
