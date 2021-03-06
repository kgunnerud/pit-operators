package no.politiet.organa.operators.libs.contract.organa.piterator;

import io.fabric8.kubernetes.api.model.Probe;
import io.fabric8.kubernetes.api.model.ProbeBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Probes {
    Probe startup;
    Probe liveness;
    Probe readiness;

    @Value
    @Builder
    public static class Probe {
        Boolean enabled;
        Integer failureThreshold;
        Integer initialDelay;
        String path;
        Integer periodSeconds;
        Integer port;
        Integer timeout;
    }
}
