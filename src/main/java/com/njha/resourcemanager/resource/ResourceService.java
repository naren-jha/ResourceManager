package com.njha.resourcemanager.resource;

import java.util.List;

public interface ResourceService {
    Resource addResource(Resource resource);
    boolean removeResource(Resource resource);
    List<Resource> getAvailableResources(RequestQuery query);
}
