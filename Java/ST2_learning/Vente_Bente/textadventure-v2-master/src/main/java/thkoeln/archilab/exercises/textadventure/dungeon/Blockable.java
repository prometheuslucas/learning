package thkoeln.archilab.exercises.textadventure.dungeon;

public interface Blockable {
    /**
     * Blocker will be removed.
     */
    void unblock();

    /**
     * New blocker added
     * @param blocker
     * @throws AlreadyBlockedException if there is already a blocker.
     */
    void block(Blocking blocker) throws AlreadyBlockedException;
}
