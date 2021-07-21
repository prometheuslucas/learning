package thkoeln.archilab.exercises.textadventure.creatures;

import lombok.Getter;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Coordinate;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Impact;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Printable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Embeddable
public class Item implements Printable {
    private String name;
    private Impact impactOnSelf;
    private Impact impactOnOthers;
    private Integer limitOfUse = null;

    public Item( String name, Impact impactOnSelf, Impact impactOnOthers, Integer limitOfUse ) {
        this.name = name;
        this.impactOnSelf = impactOnSelf;
        this.impactOnOthers = impactOnOthers;
        this.limitOfUse = limitOfUse;
    }

    public Item( String name, Impact impactOnSelf, Impact impactOnOthers ) {
        this.name = name;
        this.impactOnSelf = impactOnSelf;
        this.impactOnOthers = impactOnOthers;
    }

    protected Item() {}

    @Override
    public void print() {
        System.out.print( name.substring(0, 1) );
    }

    @Override
    public String toString() {
        return name;
    }
}
