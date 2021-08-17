package no.politiet.organa.operators.libs.contract.organa.piterator;

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
