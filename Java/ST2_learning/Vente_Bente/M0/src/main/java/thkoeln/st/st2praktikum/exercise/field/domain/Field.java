package thkoeln.st.st2praktikum.exercise.field.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.bot.domain.Bot;

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
    private List<Barrier> barriers = new ArrayList<>();
    @Getter
    private List<Bot> bots = new ArrayList<>();

    public Field(Integer height,Integer width){
        this.id = UUID.randomUUID();
        this.fieldHeight = height;
        this.fieldWidth = width;
    }

    public void addBarrier(Barrier barrier){barriers.add(barrier);}
}
