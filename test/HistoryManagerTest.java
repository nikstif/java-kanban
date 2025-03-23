import manager.Managers;
import manager.TaskManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {
    private TaskManager manager;

    @BeforeEach
    void setup() {
        manager = Managers.getDefault();
    }

    @Test
    void shouldAddTasksToHistory() {
        int taskId = manager.createTask(new Task("task1", "desc1", Status.NEW));
        manager.getTaskById(taskId);

        List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "История должна содержать 1 задачу");
    }

    @Test
    void historyShouldBeLimitedTo10Tasks() {
        for (int i = 0; i < 15; i++) {
            int id = manager.createTask(new Task("task " + i, "desc", Status.NEW));
            manager.getTaskById(id);
        }

        List<Task> history = manager.getHistory();
        assertEquals(10, history.size(), "История должна содержать максимум 10 задач");
    }

    @Test
    void taskDataInHistoryShouldMatchOriginal() {
        int taskId = manager.createTask(new Task("task1", "desc1", Status.NEW));
        Task task = manager.getTaskById(taskId);

        List<Task> history = manager.getHistory();
        Task historyTask = history.get(0);

        assertEquals(task.getId(), historyTask.getId());
        assertEquals(task.getName(), historyTask.getName());
        assertEquals(task.getDescription(), historyTask.getDescription());
        assertEquals(task.getStatus(), historyTask.getStatus());
    }
}
