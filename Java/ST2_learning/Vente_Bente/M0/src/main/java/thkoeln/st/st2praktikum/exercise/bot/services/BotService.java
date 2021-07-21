package thkoeln.st.st2praktikum.exercise.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainPrimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainPrimitives.Coordinates2D;
import thkoeln.st.st2praktikum.exercise.field.repository.FieldRepository;
import thkoeln.st.st2praktikum.exercise.bot.domain.Bot;
import thkoeln.st.st2praktikum.exercise.bot.repository.BotRepository;

import java.util.Optional;
import java.util.UUID;


@Service
public class BotService {

    private final BotRepository BotRepository;
    private final FieldRepository FieldRepository;

    @Autowired
    public BotService(BotRepository BotRepository,
                      FieldRepository FieldRepository) {
        this.BotRepository = BotRepository;
        this.FieldRepository = FieldRepository;
    }
    
    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addBot(String name) {
        Bot tidyUpRobot = new Bot(name);
        return BotRepository.save(tidyUpRobot).getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
//    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
//        Bot tidyUpRobot = BotRepository.findById(tidyUpRobotId).get();
//        TidyUpRobotTaskService tidyUpRobotTaskService = new TidyUpRobotTaskService(BotRepository, FieldRepository, transportCategoryService);
//        Boolean taskResponse = tidyUpRobotTaskService.processTask(tidyUpRobot, task);
//        BotRepository.save(tidyUpRobot);
//        return taskResponse;
//    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getBotRoomId(UUID tidyUpRobotId) {
        return BotRepository.findById(tidyUpRobotId).get().getField().getId();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Coordinates2D getBotCoordinates2D(UUID BotId) {
        return BotRepository.findById(BotId).get().getPosition();
    }

    public Iterable<Bot> getAllBots() {
        return BotRepository.findAll();
    }

    public Optional<Bot> getBotById(UUID id) {
        return BotRepository.findById(id);
    }

    public Boolean deleteBotById(UUID id) {
        if (getBotById(id).isPresent()) {
            BotRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Bot updateBot(Bot updatedBot) {
        return BotRepository.save(updatedBot);
    }
}
