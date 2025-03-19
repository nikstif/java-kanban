package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksByEpic = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description) {
        super(name, description);
        this.id = id;
    }

    public List<Integer> getSubtasksByEpic() {
        return subtasksByEpic;
    }

    public void setSubtasksByEpic(List<Integer> subtasksByEpic) {
        this.subtasksByEpic = subtasksByEpic;
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
