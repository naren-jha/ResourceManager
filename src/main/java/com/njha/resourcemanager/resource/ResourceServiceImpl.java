package com.njha.resourcemanager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource addResource(Resource resource) {
        validateResource(resource);
        return resourceRepository.addResource(resource);
    }

    @Override
    public boolean removeResource(Resource resource) {
        if (resource.getId() == null)
            throw new IllegalStateException("Invalid resource. resource does not exist");
        return resourceRepository.removeResource(resource);
    }

    @Override
    public List<Resource> getAvailableResources(RequestQuery query) {
        return resourceRepository.getAvailableResources(query);
    }

    private void validateResource(Resource resource) {
        if (resource.getType() == null)
            throw new IllegalArgumentException("Invalid resource type!");
        if (resource.getCpu() <= 0)
            throw new IllegalArgumentException("Invalid CPU unit! CPU unit should be >0");
        if (resource.getPrice() <= 0)
            throw new IllegalArgumentException("Invalid price! Price should be >0");
    }
}
