package com.njha.resourcemanager.task;

import com.njha.resourcemanager.resource.DataCenter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public List<String> getAllTaskStatus(DataCenter dataCenter) {
        List<String> result = new ArrayList<>();
        for (Task task : dataCenter.getTaskQueue()) {
            result.add(String.format("task-%d :: status: %s :: startTime: %s :: endTime: %s", task.getId(), getTaskStatus(task.isCompleted()), task.getStartTime(), task.getEndTime()));
        }
        for (Task task : dataCenter.getCompletedTask()) {
            result.add(String.format("task-%d :: status: %s :: startTime: %s :: endTime: %s", task.getId(), getTaskStatus(task.isCompleted()), task.getStartTime(), task.getEndTime()));
        }

        return result;
    }

    @Override
    public void submitNewTaskToDatacenter(DataCenter dataCenter, Task task) {
        synchronized (dataCenter.getTaskQueue()) {
            dataCenter.getTaskQueue().offer(task);
        }
    }

    private String getTaskStatus(boolean completed) {
        return completed ? TaskStatusType.COMPLETED.name() : TaskStatusType.PENDING.name();
    }
}
