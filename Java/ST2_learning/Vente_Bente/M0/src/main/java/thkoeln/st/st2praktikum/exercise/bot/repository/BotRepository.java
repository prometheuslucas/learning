package thkoeln.st.st2praktikum.exercise.bot.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.bot.domain.Bot;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.util.UUID;

public interface BotRepository extends CrudRepository <Bot, UUID>{
    Iterable<Bot> findTidyUpRobotsByField(Field field);

}
