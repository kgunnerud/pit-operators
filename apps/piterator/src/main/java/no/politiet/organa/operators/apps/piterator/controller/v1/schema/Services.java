package no.politiet.organa.operators.apps.piterator.controller.v1.schema;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Services {
    Boolean enabled;
    List<ServicePort> ports;

    @Value
    @Builder
    public static class ServicePort {
        String name;
        Integer port;
        Integer targetPort;
        String protocol;
    }
}
