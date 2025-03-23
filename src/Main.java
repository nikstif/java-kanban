import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        taskManager.createTask(new Task("name_task1", "desc_task1", Status.NEW));
        taskManager.createTask(new Task("name_task2", "desc_task2", Status.NEW));
        taskManager.createEpic(new Epic("name_epic1", "desc_epic1"));
        taskManager.createEpic(new Epic("name_epic2", "desc_epic2"));
        taskManager.createSubtask(new Subtask("name_sbt1", "desc_sbt1", Status.NEW, 3));
        taskManager.createSubtask(new Subtask("name_sbt2", "desc_sbt2", Status.NEW, 4));
        taskManager.createSubtask(new Subtask("name_sbt3", "desc_sbt3", Status.NEW, 4));

        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(1);
        taskManager.getEpicById(3);
        taskManager.getEpicById(3);
        taskManager.getEpicById(4);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(7);
        taskManager.getSubtaskById(6);
        taskManager.getSubtaskById(6);
        taskManager.getSubtaskById(7);

        System.out.println("History: " + taskManager.getHistory());
    }
}
