package thkoeln.archilab.exercises.textadventure.domainprimitives;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Impact {
    // todo
    @Setter
    private Float effectOnStrength;

    public Impact( Float effectOnStrength ) {
        this.effectOnStrength = effectOnStrength;
    }
    protected Impact() {}


    public static Impact from( Float effectOnStrength ) {
        return new Impact( effectOnStrength );
    }

    public boolean hasNoEffect() {
        return Float.compare(effectOnStrength, 0f) == 0;
    }

    @Override
    public String toString() {
        return String.format("%.01f", effectOnStrength);
    }
}
