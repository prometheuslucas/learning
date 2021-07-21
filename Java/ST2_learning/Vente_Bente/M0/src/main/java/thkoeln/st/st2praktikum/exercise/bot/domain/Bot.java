package thkoeln.st.st2praktikum.exercise.bot.domain;

import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.domainPrimitives.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Entity
@NoArgsConstructor
public class Bot {

    @Id
    @Getter
    private UUID id;
    @Getter
    private String name;
    @Getter
    private Coordinates2D position;
    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    private Field field;
    //maybe Task list

    public Bot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public void placeBot(Field destinationField, Coordinates2D destinationPosition) {
        this.field = destinationField;
        this.position = destinationPosition;
    }

    public void moveBot(Coordinates2D destinationPosition) { this.position = destinationPosition; }
}
