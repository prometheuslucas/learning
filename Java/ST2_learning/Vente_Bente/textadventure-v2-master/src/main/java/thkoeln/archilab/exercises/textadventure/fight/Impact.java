package thkoeln.archilab.exercises.textadventure.fight;

public class Impact {
    private Float effectOnStrength;

    protected Impact( Float effectOnStrength ) {
        this.effectOnStrength = effectOnStrength;
    }

    public static Impact from( Float effectOnStrength ) {
        return new Impact( effectOnStrength );
    }

    public boolean isNone() {
        return Float.compare(effectOnStrength, 0f) == 0;
    }

    /**
     * Visibility "package" => only to be used by Strength.receiveImpact( impact )
     * @return changeToStrength
     */
    Float effectOnStrength() {
        return effectOnStrength;
    }

    @Override
    public String toString() {
        return String.format("%.01f", effectOnStrength);
    }
}
