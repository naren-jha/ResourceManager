package com.njha.resourcemanager.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resource {
    private Long id;
    private ResourceType type;
    private Integer cpu;
    private double price;
    private boolean isAllocated;
}
