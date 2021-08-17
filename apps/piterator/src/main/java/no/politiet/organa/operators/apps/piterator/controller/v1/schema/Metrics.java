package no.politiet.organa.operators.apps.piterator.controller.v1.schema;

import io.fabric8.kubernetes.api.model.metrics.v1beta1.PodMetrics;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Metrics {
    Boolean enabled;
    List<MetricEndpoint> endpoints;

    @Value
    @Builder
    public static class MetricEndpoint {
        String path;
        String pollInterval;
    }
}
