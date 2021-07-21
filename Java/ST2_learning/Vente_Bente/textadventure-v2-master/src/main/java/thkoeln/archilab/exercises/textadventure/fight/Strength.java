package thkoeln.archilab.exercises.textadventure.fight;


public class Strength {
    private Float max;
    private Float current;


    public static Strength fromMax( Float maxStrength ) {
        return new Strength( maxStrength );
    }

    public static Strength fromCurrentAndMax( Float current, Float maxStrength ) {
        Strength strength = new Strength( maxStrength );
        strength.setCurrent( current );
        return strength;
    }

    public Strength afterImpact( Impact impact ) {
        if ( impact.isNone() ) {
            return this;
        }
        else {
            float newCurrent = this.current + impact.effectOnStrength();
            if ( newCurrent > max ) newCurrent = max;
            if ( newCurrent <= 0f ) newCurrent = 0f;
            return Strength.fromCurrentAndMax( newCurrent, max );
        }
    }

    public Float relative() {
        return current / max;
    }

    public boolean meansDeath() {
        return current <= 0f;
    }

    @Override
    public String toString() {
        return String.format("%.01f", current) + " out of " + String.format("%.01f", max);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Strength strength = (Strength) o;
        return Float.compare(strength.max, max) == 0 &&
                Float.compare(strength.current, current) == 0;
    }

    protected Strength( Float maxStrength ) {
        if ( maxStrength <= 0f ) throw new StrengthException( "Maximum strength must be > 0" );
        this.max = maxStrength;
        current = maxStrength;
    }

    protected void setCurrent( Float current ) {
        if ( current < 0f ) throw new StrengthException( "Current strength must be >= 0" );
        if ( current > this.max ) throw new StrengthException( "Current strength must be < max" );
        this.current = current;
    }

}
