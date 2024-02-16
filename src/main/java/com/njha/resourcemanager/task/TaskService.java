package com.njha.resourcemanager.task;

import com.njha.resourcemanager.resource.DataCenter;

import java.util.List;

public interface TaskService {
    List<String> getAllTaskStatus(DataCenter dataCenter);
    void submitNewTaskToDatacenter(DataCenter dataCenter, Task task);
}
