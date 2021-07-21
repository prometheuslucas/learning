package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.GenericTests;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests extends GenericTests {

    protected static final String ROBOT_NAME_1 = "Marvin";
    protected static final String ROBOT_NAME_2 = "Darvin";

    protected UUID room1;
    protected UUID room2;
    protected UUID room3;

    protected UUID tidyUpRobot1;
    protected UUID tidyUpRobot2;

    protected UUID transportCategory1;
    protected UUID transportCategory2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;


    protected MovementTests(WebApplicationContext appContext) {
        super(appContext);
    }


    protected void createWorld(TidyUpRobotService tidyUpRobotService, RoomService roomService, TransportCategoryService transportCategoryService) {
        room1 = roomService.addRoom(6,6);
        room2 = roomService.addRoom(5,5);
        room3 = roomService.addRoom(3,3);

        roomService.addObstacle(room1, Obstacle.fromString("(0,3)-(2,3)"));
        roomService.addObstacle(room1, Obstacle.fromString("(3,0)-(3,3)"));
        roomService.addObstacle(room2, Obstacle.fromString("(0,2)-(4,2)"));

        tidyUpRobot1 = tidyUpRobotService.addTidyUpRobot(ROBOT_NAME_1);
        tidyUpRobot2 = tidyUpRobotService.addTidyUpRobot(ROBOT_NAME_2);

        transportCategory1 = transportCategoryService.addTransportCategory("Hallway");
        transportCategory2 = transportCategoryService.addTransportCategory("Staircase");

        connection1 = transportCategoryService.addConnection(transportCategory1, room1, Vector2D.fromString("(1,1)"), room2, Vector2D.fromString("(0,1)"));
        connection2 = transportCategoryService.addConnection(transportCategory1, room2, Vector2D.fromString("(1,0)"), room3, Vector2D.fromString("(2,2)"));
        connection3 = transportCategoryService.addConnection(transportCategory2, room3, Vector2D.fromString("(2,2)"), room2, Vector2D.fromString("(1,0)"));
    }

    protected void assertPosition(UUID tidyUpRobotId, UUID expectedRoomId, int expectedX, int expectedY) throws Exception {
        Object tidyUpRobot = getTidyUpRobotRepository().findById(tidyUpRobotId).get();

        // Assert Grid
        Method getRoomMethod = tidyUpRobot.getClass().getMethod("getRoomId");
        assertEquals(expectedRoomId,
                getRoomMethod.invoke(tidyUpRobot));

        // Assert Pos
        Method getVector2DMethod = tidyUpRobot.getClass().getMethod("getVector2D");
        assertEquals(new Vector2D(expectedX, expectedY),
                getVector2DMethod.invoke(tidyUpRobot));
    }

    protected CrudRepository<Object, UUID> getTidyUpRobotRepository() throws Exception {
        return oir.getRepository("thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot");
    }

    protected void executeTasks(TidyUpRobotService service, UUID tidyUpRobot, Task[] tasks) {
        for (Task task : tasks) {
            service.executeCommand(tidyUpRobot, task);
        }
    }
}
