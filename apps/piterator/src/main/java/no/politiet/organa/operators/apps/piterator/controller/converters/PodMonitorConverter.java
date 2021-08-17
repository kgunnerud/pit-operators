package no.politiet.organa.operators.apps.piterator.controller.converters;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.openshift.api.model.monitoring.v1.PodMonitor;
import io.fabric8.openshift.api.model.monitoring.v1.PodMonitorBuilder;
import lombok.val;
import no.politiet.organa.operators.apps.piterator.controller.v1.schema.Metrics;
import no.politiet.organa.operators.apps.piterator.controller.v1.schema.PitApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(value = "app.resources.metrics.enabled", havingValue = "true")
public class PodMonitorConverter implements SubResourceConverter {

    @Override
    public List<? extends HasMetadata> convert(PitApp resource) {
        if (!isEnabled(resource)) {
            return Collections.emptyList();
        }

        val metrics = resource.getSpec().getMetrics();
        if (Optional.ofNullable(metrics)
                .map(Metrics::getEndpoints)
                .filter(it -> !it.isEmpty())
                .isEmpty()) {

            return List.of(toK8s(resource, null));
        }

        return metrics.getEndpoints().stream()
                .map(it -> toK8s(resource, it))
                .toList();
    }

    private PodMonitor toK8s(PitApp resource, Metrics.MetricEndpoint metrics) {
        return new PodMonitorBuilder()
                .withMetadata(resource.getDefaultMeta().build())

                .withNewSpec()

                .withNewNamespaceSelector()
                .addNewMatchName(resource.getMetadata().getNamespace())
                .and()

                .withNewSelector()
                .addToMatchLabels("app", resource.getMetadata().getName())
                .and()

                .addNewPodMetricsEndpoint()
                .withPath(Optional.ofNullable(metrics).map(Metrics.MetricEndpoint::getPath).orElse("/actuator/prometheus"))
                .withInterval(Optional.ofNullable(metrics).map(Metrics.MetricEndpoint::getPollInterval).orElse("60"))
                .withScheme("HTTPS")
                .and()

                .and()
                .build();
    }

    private boolean isEnabled(PitApp resource) {
        return Optional.ofNullable(resource.getSpec().getMetrics())
                .map(Metrics::getEnabled)
                .orElse(true);
    }
}
