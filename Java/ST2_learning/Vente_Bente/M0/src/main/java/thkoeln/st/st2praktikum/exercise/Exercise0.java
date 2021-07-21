package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.bot.domain.Bot;
import thkoeln.st.st2praktikum.exercise.bot.services.BotService;
import thkoeln.st.st2praktikum.exercise.domainPrimitives.Coordinates2D;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.util.UUID;

public class Exercise0 implements Walkable {
    private BotService botService;
    UUID bot1 = botService.addBot("Wall-E");



    @Override
    public String walk(String walkCommandString) {

        throw new UnsupportedOperationException();
    }
}
