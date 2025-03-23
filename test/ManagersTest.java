import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
