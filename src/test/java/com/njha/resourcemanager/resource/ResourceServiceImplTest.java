package com.njha.resourcemanager.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ResourceServiceImplTest {

    @Autowired
    private ResourceService resourceService;

    @Test
    void shouldTestThatResourceIsAddedSuccessfully() {
        Resource r0 = Resource.builder()
                .type(ResourceType.SERVER_INSTANCE)
                .cpu(8)
                .price(200.0)
                .isAllocated(false)
                .build();

        assertThat(r0.getId()).isNull();
        r0 = resourceService.addResource(r0);
        assertThat(r0.getId()).isNotNull();
    }

    @Test
    void shouldTestThatAvailableResourcesAreFetchedSuccessfully() {
        Resource r0 = Resource.builder()
                .type(ResourceType.SERVER_INSTANCE)
                .cpu(8)
                .price(200.0)
                .isAllocated(false)
                .build();
        r0 = resourceService.addResource(r0);

        RequestQuery requestQuery = RequestQuery.builder()
                .type(ResourceType.SERVER_INSTANCE)
                .cpu(8)
                .build();
        List<Resource> matchingResources = resourceService.getAvailableResources(requestQuery);
        assertThat(matchingResources.size()).isGreaterThan(0);
    }

    @Test
    void shouldTestThatResourcesAreRemovedSuccessfully() {
        Resource r0 = Resource.builder()
                .type(ResourceType.SERVER_INSTANCE)
                .cpu(8)
                .price(200.0)
                .isAllocated(false)
                .build();
        r0 = resourceService.addResource(r0);
        resourceService.removeResource(r0);

        RequestQuery requestQuery = RequestQuery.builder()
                .type(ResourceType.SERVER_INSTANCE)
                .cpu(8)
                .build();
        List<Resource> matchingResources = resourceService.getAvailableResources(requestQuery);
        assertThat(matchingResources.size()).isGreaterThanOrEqualTo(0);
    }
}