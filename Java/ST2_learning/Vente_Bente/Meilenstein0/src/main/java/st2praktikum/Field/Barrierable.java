package st2praktikum.Field;

public interface Barrierable {
    /**
     * Blocker will be removed.
     */
    void unblock();

    /**
     * New blocker added
     * @param blocker
     * @throws AlreadyBlockedException if there is already a blocker.
     */
    void block(Blocking blocker) throws AlreadyBarrierException;
}
