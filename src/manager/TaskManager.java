package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private int count = 0;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasksByEpic().clear();
            manageEpicStatus(epic);
        }
    }

    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        return null;
    }

    public Task getEpicById(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        return null;
    }

    public Task getSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        }
        return null;
    }

    public void createTask(Task task) {
        task.setId(++count);
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(++count);
        manageEpicStatus(epic);
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            subtask.setId(++count);
            subtasks.put(subtask.getId(), subtask);

            epic.getSubtasksByEpic().add(subtask.getId());
        }
    }

    public void updateTask(Task task) {
        Task oldTask = tasks.get(task.getId());
        if (oldTask != null) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateEpic(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        if (oldEpic != null) {
            epic.setSubtasksByEpic(oldEpic.getSubtasksByEpic());
            manageEpicStatus(epic);
            epics.put(epic.getId(), epic);
        }
    }

    public void updateSubtask(Subtask subtask) {
        Subtask oldSubtask = subtasks.get(subtask.getId());
        if (oldSubtask != null) {
            subtask.setEpicId(oldSubtask.getEpicId());
            subtasks.put(subtask.getId(), subtask);
            manageEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            epics.remove(id);
            for (Integer idSubtask : epic.getSubtasksByEpic()) {
                subtasks.remove(idSubtask);
            }
        }
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            subtasks.remove(subtask.getId());
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtasksByEpic().remove((Integer) subtask.getId());
            manageEpicStatus(epic);
        }
    }

    public List<Subtask> getAllSubtasksByEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            List<Subtask> allSubtasksByEpic = new ArrayList<>();
            for (Integer idSubtask : epic.getSubtasksByEpic()) {
                allSubtasksByEpic.add(subtasks.get(idSubtask));
            }
            return  allSubtasksByEpic;
        }
        return null;
    }

    private void manageEpicStatus(Epic epic) {
        if (epic.getSubtasksByEpic().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        List<Subtask> subtasksByEpic = new ArrayList<>();
        for (Integer idSubtask : epic.getSubtasksByEpic()) {
            subtasksByEpic.add(subtasks.get(idSubtask));
        }
        int totalSubtasksByEpic = subtasksByEpic.size();
        int countDone = 0;
        int countInProgress = 0;
        for (Subtask subtask : subtasksByEpic) {
            if (subtask.getStatus() == Status.DONE) {
                countDone++;
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                countInProgress++;
            }
        }
        if (countDone == totalSubtasksByEpic) {
            epic.setStatus(Status.DONE);
        } else if (countDone == 0 && countInProgress == 0) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
