package no.politiet.organa.operators.apps.piterator.controller.v1.schema;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Resources {
    Resource requests;
    Resource limits;

    @Value
    @Builder
    public static class Resource {
        String cpu;
        String memory;
    }
}
