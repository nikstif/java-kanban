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
            epic.deleteAllSubtasksByEpic();
            manageEpicStatus(epic);
        }
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Task getEpicById(int id) {
        return epics.get(id);
    }

    public Task getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public int createTask(Task task) {
        if (task == null) {
            return -1;
        }
        task.setId(++count);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public int createEpic(Epic epic) {
        if (epic == null) {
            return -1;
        }
        epic.setId(++count);
        manageEpicStatus(epic);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    public int createSubtask(Subtask subtask) {
        if (subtask == null) {
            return -1;
        }
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            return -1;
        }

        subtask.setId(++count);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtaskToEpic(subtask.getId());
        return subtask.getId();
    }

    public boolean updateTask(Task task) {
        Task oldTask = tasks.get(task.getId());
        if (oldTask != null) {
            tasks.put(task.getId(), task);
            return true;
        }
        return false;
    }

    public boolean updateEpic(Epic epic) {
        Epic currentEpic = epics.get(epic.getId());
        if (currentEpic != null) {
            currentEpic.setName(epic.getName());
            currentEpic.setDescription(epic.getDescription());
            return true;
        }
        return false;
    }

    public boolean updateSubtask(Subtask subtask) {
        Subtask oldSubtask = subtasks.get(subtask.getId());
        if (oldSubtask != null) {
            subtasks.put(subtask.getId(), subtask);
            manageEpicStatus(epics.get(subtask.getEpicId()));
            return true;
        }
        return false;
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer idSubtask : epic.getSubtasksByEpic()) {
                subtasks.remove(idSubtask);
            }
            epics.remove(id);
        }
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.deleteSubtaskByEpic(subtask.getId());
            manageEpicStatus(epic);
            subtasks.remove(subtask.getId());
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
        return new ArrayList<>();
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
