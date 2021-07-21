package thkoeln.st.st2praktikum.exercise.domainPrimitives;

import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
//import java.util.UUID; when enter is ready


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Task {
    @Getter
    private TaskType taskType;
    @Getter
    private Integer numberOfSteps;

    public Task(TaskType taskType, Integer numberOfSteps) {
        if (numberOfSteps < 0) {
            throw new InvalidInputException("Number of steps for a Task is not allowed to be negative.");
        }
        this.taskType = taskType;
        this.numberOfSteps = numberOfSteps;
    }

    private static Task fromString(String taskString) throws InvalidInputException {
        if (!validateStringFormatting(taskString)) {
            throw new InvalidInputException("String Format Invalid");
        }
        Pair<String, String> taskSplit = splitTaskString(taskString);
        TaskType taskType = parseStringToTaskType(taskSplit.getLeft());
        Integer numberOfSteps = Integer.parseInt(taskSplit.getRight());
        return new Task(taskType, numberOfSteps);

//        use when enter ready
//        if(getTaskStringValueType(taskType) == TaskStringValueType.INTEGER) {
//            if(Integer.parseInt(taskSplit.getRight())>0) {
//                throw new InvalidInputException("Only positive movement numbers are permitted.");
//            }
//            Integer numberOfSteps = (Integer.parseInt(taskSplit.getRight()));
//            return new Task(taskType, numberOfSteps);
//        }
//        if(getTaskStringValueType(taskType) == TaskStringValueType.UUID) {
//            UUID gridId = UUID.fromString(taskSplit.getRight());
//            return new Task(taskType, gridId);
//        }

    }

    private static TaskType parseStringToTaskType (String taskTypeString) throws InvalidInputException{
        TaskType task;
        switch (taskTypeString){
            case "no": task = TaskType.NORTH; break;
            case "ea": task = TaskType.EAST; break;
            case "so": task = TaskType.SOUTH; break;
            case "we": task = TaskType.WEST; break;
            case "en": task = TaskType.ENTER; break;
            case "tr": task = TaskType.TRANSPORT; break;
            default: throw new InvalidInputException("Unidentified Task in String");
        }
        return task;
    }

    private static boolean validateStringFormatting(String taskString) {
        return taskString.matches("\\[\\w+,[\\w-]+\\]");

    }

    private static Pair<String, String> splitTaskString(String taskString) {
        taskString = taskString.replaceAll("\\[", "").replaceAll("\\]", "");
        String[] taskSplit = taskString.split(",");
        return new Pair(taskSplit[0], taskSplit[1]);
    }


//    Use when enter ready
//    private static TaskStringValueType getTaskStringValueType(TaskType task) {
//        switch (task) {
//            case ENTER:
//            case TRANSPORT: return TaskStringValueType.UUID;
//            default: return TaskStringValueType.INTEGER;
//        }
//    }
//    private enum TaskStringValueType {
//        UUID,
//        INTEGER
//    }

}
