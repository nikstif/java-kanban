package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int count = 0;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasksByEpic();
            manageEpicStatus(epic);
        }
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Task getEpicById(int id) {
        Task epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Task getSubtaskById(int id) {
        Task subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public int createTask(Task task) {
        if (task == null) {
            return -1;
        }
        task.setId(++count);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int createEpic(Epic epic) {
        if (epic == null) {
            return -1;
        }
        epic.setId(++count);
        manageEpicStatus(epic);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
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

    @Override
    public boolean updateTask(Task task) {
        Task oldTask = tasks.get(task.getId());
        if (oldTask != null) {
            tasks.put(task.getId(), task);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEpic(Epic epic) {
        Epic currentEpic = epics.get(epic.getId());
        if (currentEpic != null) {
            currentEpic.setName(epic.getName());
            currentEpic.setDescription(epic.getDescription());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSubtask(Subtask subtask) {
        Subtask oldSubtask = subtasks.get(subtask.getId());
        if (oldSubtask != null && subtask.getEpicId() == oldSubtask.getEpicId()) {
            subtasks.put(subtask.getId(), subtask);
            manageEpicStatus(epics.get(subtask.getEpicId()));
            return true;
        }
        return false;
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer idSubtask : epic.getSubtasksByEpic()) {
                subtasks.remove(idSubtask);
            }
            epics.remove(id);
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.deleteSubtaskByEpic(subtask.getId());
            manageEpicStatus(epic);
            subtasks.remove(subtask.getId());
        }
    }

    @Override
    public List<Task> getAllSubtasksByEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            List<Task> allSubtasksByEpic = new ArrayList<>();
            for (Integer idSubtask : epic.getSubtasksByEpic()) {
                allSubtasksByEpic.add(subtasks.get(idSubtask));
            }
            return allSubtasksByEpic;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
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
