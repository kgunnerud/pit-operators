package no.politiet.organa.operators.apps.piterator.dto;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import lombok.Value;

@Version("v1")
@Group("politiet.pitapp")
public class PitApp extends CustomResource<PitApp.PitAppSpec, PitApp.PitAppStatus> {

    @Value
    public static class PitAppSpec {
        String image;
        Probes probes;
    }

    @Value
    public static class Probes {
        Probe startup;
        Probe liveness;
        Probe readiness;
    }

    @Value
    public static class Probe {
        Integer failureThreshold;
        Integer initialDelay;
        String path;
        Integer periodSeconds;
        Integer port;
        Integer timeout;
    }

    public static class PitAppStatus {

    }
}
