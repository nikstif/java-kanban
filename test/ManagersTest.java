import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagersTest {
    @Test
    void shouldReturnDefaultTaskManager() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "TaskManager не инициализирован");
    }

    @Test
    void shouldReturnDefaultHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager не инициализирован");
    }

    @Test
    void shouldReturnEmptyHistoryInitially() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertTrue(historyManager.getHistory().isEmpty(), "История должна быть пустой при создании");
    }
}
