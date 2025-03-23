package manager;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history;

    public InMemoryHistoryManager() {
        this.history = new ArrayList<>();
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > 10) {
            history.removeFirst();
        }
    }
}
