package com.njha.resourcemanager.resource;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ResourceRepositoryImpl implements ResourceRepository {

    private Map<Long, Resource> resources = new HashMap<>();

    @Override
    public Resource addResource(Resource resource) {
        Long resourceId = Long.valueOf(resources.size());
        resource.setId(resourceId);

        resources.put(resourceId, resource);
        return resource;
    }

    @Override
    public boolean removeResource(Resource resource) {
        resources.remove(resource.getId());
        return true;
    }

    @Override
    public List<Resource> getAvailableResources(RequestQuery query) {
        Collection<Resource> allAvailableResources = this.resources.values();
        List<Resource> resultList = new ArrayList<>();

        // filter out unavailable resources first
        List<Resource> tmpList = new ArrayList<>();
        for (Resource r : allAvailableResources) {
            if (!r.isAllocated()) {
                tmpList.add(r);
            }
            allAvailableResources = tmpList;
            resultList = tmpList;
        }

        // filter by resource type needed
        if (query.getType() != null) {
            tmpList = new ArrayList<>();
            for (Resource r : allAvailableResources) {
                if (r.getType().equals(query.getType())) {
                    tmpList.add(r);
                }
            }
            allAvailableResources = tmpList;
            resultList = tmpList;

        }

        // filter by CPU needed
        if (query.getCpu() != null && query.getCpu() > 0) {
            tmpList = new ArrayList<>();
            for (Resource r : allAvailableResources) {
                if (r.getCpu() >= query.getCpu()) {
                    tmpList.add(r);
                }
            }
            allAvailableResources = tmpList;
            resultList = tmpList;
        }

        return resultList;
    }
}
