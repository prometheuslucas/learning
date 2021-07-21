package thkoeln.archilab.exercises.textadventure.creatures;

import lombok.Getter;
import thkoeln.archilab.exercises.textadventure.fight.Impact;
import thkoeln.archilab.exercises.textadventure.Printable;

@Getter
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

    @Override
    public void print() {
        System.out.print( name.substring(0, 1) );
    }

    @Override
    public String toString() {
        return name;
    }
}
