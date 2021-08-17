package no.politiet.organa.operators.apps.piterator.controller.v1.schema;

public class DefaultPitApp {
/*
    private static final PitAppSpec defaults = PitAppSpec.builder()
            .resources(PitApp.Resources.builder()
                    .requests(PitApp.Resource.builder()
                            .cpu("100m")
                            .memory("512Mi")
                            .build())
                    .limits(PitApp.Resource.builder()
                            .cpu("1000m")
                            .memory("1024Mi")
                            .build())
                    .build())
            .probes(Probes.builder()
                    .startup(PitApp.Probe.builder()
                            .enabled(true)
                            .failureThreshold(120)
                            .initialDelay(10)
                            .path("/actuator/health/liveness")
                            .periodSeconds(1)
                            .timeout(1)
                            .build())
                    .liveness(PitApp.Probe.builder()
                            .enabled(true)
                            .failureThreshold(3)
                            .path("/actuator/health/liveness")
                            .periodSeconds(1)
                            .timeout(1)
                            .build())
                    .readiness(PitApp.Probe.builder()
                            .enabled(true)
                            .failureThreshold(3)
                            .path("/actuator/health/readiness")
                            .periodSeconds(1)
                            .timeout(1)
                            .build())
                    .build())
            .metrics(Metrics.builder()
                    .enabled(true)
                    .path("/actuator/prometheus")
                    .pollInterval(60)
                    .build())
            .build();

    public static PitAppSpec defaultConfig() {
        return defaults;
    }

 */
}
