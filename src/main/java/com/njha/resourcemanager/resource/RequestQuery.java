package com.njha.resourcemanager.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestQuery {
    private ResourceType type;
    private Integer cpu;
}
