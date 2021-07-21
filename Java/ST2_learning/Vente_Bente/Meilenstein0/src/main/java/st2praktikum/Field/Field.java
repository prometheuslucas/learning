package st2praktikum.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Field {
    @Id
    @Getter
    private UUID id;
    @Getter
    private Integer fieldHeight;
    @Getter
    private Integer fieldWidth;

    @Getter
    private List<thkoeln.st.st2praktikum.exercise.Barrier> barriers = new ArrayList<>();

    public Field(Integer height,Integer width){
        this.id = UUID.randomUUID();
        this.fieldHeight = height;
        this.fieldWidth = width;
    }

    public void addBarrier(thkoeln.st.st2praktikum.exercise.Barrier barrier){barriers.add(barrier);}
}
