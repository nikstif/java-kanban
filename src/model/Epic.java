package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasksByEpic = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description) {
        super(name, description);
        this.id = id;
    }

    public List<Integer> getSubtasksByEpic() {
        return new ArrayList<>(subtasksByEpic);
    }

    public void deleteSubtaskByEpic(Integer id) {
        subtasksByEpic.remove(id);
    }

    public void deleteAllSubtasksByEpic() {
        subtasksByEpic.clear();
    }

    public void addSubtaskToEpic(Integer id) {
        if (this.id != id) {
            subtasksByEpic.add(id);
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtasksByEpic=" + subtasksByEpic +
                '}';
    }
}
