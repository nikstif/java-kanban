import manager.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.createTask(new Task("name_task1", "desc_task1", Status.NEW));
        taskManager.createTask(new Task("name_task2", "desc_task2", Status.NEW));
        System.out.println("All tasks: " + taskManager.getAllTasks());

        taskManager.updateTask(new Task(1, "name_TASK1", "desc_TASK1", Status.IN_PROGRESS));
        System.out.println("Update task (1)...");
        System.out.println("Get task by id (1): " + taskManager.getTaskById(1));

        taskManager.deleteTaskById(2);
        System.out.println("Delete task by id (2)...");
        System.out.println("Get task by id (2): " + taskManager.getTaskById(2));
        System.out.println("-".repeat(30));


        taskManager.createEpic(new Epic("name_epic1", "desc_epic1"));
        taskManager.createEpic(new Epic("name_epic2", "desc_epic2"));
        taskManager.createSubtask(new Subtask("name_sbt1", "desc_sbt1", Status.NEW, 3));
        taskManager.createSubtask(new Subtask("name_sbt2", "desc_sbt2", Status.NEW, 4));
        taskManager.createSubtask(new Subtask("name_sbt3", "desc_sbt3", Status.NEW, 4));
        System.out.println("All epics: " + taskManager.getAllEpics());
        System.out.println("All subtasks: " + taskManager.getAllSubtasks());

        taskManager.updateEpic(new Epic(4, "name_EPIC2", "desc_EPIC2"));
        System.out.println("Update epic (4)...");
        System.out.println("Get epic by id (4): " + taskManager.getEpicById(4));

        taskManager.updateSubtask(new Subtask(6, "name_SBT2", "desc_SBT2", Status.IN_PROGRESS));
        System.out.println("Update subtask (6)...");
        System.out.println("Get subtask by id (6): " + taskManager.getSubtaskById(6));
        System.out.println("Status epic after update subtask: " + taskManager.getEpicById(4));

        taskManager.updateSubtask(new Subtask(7, "name_SBT3", "desc_SBT3", Status.DONE));
        System.out.println("Update subtask (7)...");
        System.out.println("Get subtask by id (7): " + taskManager.getSubtaskById(7));
        System.out.println("Status epic after update subtask: " + taskManager.getEpicById(4));

        taskManager.deleteSubtaskById(6);
        System.out.println("Delete subtask (6)...");
        System.out.println("Get subtask by id (6): " + taskManager.getSubtaskById(6));
        System.out.println("Status epic after delete subtask: " + taskManager.getEpicById(4));

        System.out.println("All subtasks by epic (3): " + taskManager.getAllSubtasksByEpic(3));
        System.out.println("Delete epic (3)...");
        taskManager.deleteEpicById(3);
        System.out.println("Get epic by id (3): " + taskManager.getEpicById(3));
        System.out.println("All subtasks by epic (3): " + taskManager.getAllSubtasksByEpic(3));

        System.out.println("-".repeat(30));
        System.out.println("Delete all tasks, epics, subtasks...");
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpics();
        taskManager.deleteAllSubtasks();
        System.out.println("All tasks: " + taskManager.getAllTasks());
        System.out.println("All epics: " + taskManager.getAllEpics());
        System.out.println("All subtasks: " + taskManager.getAllSubtasks());
    }
}
