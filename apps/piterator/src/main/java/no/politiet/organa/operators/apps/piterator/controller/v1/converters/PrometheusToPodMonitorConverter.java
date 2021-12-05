package no.politiet.organa.operators.apps.piterator.controller.v1.converters;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.openshift.api.model.monitoring.v1.PodMonitor;
import io.fabric8.openshift.api.model.monitoring.v1.PodMonitorBuilder;
import no.politiet.organa.operators.libs.contract.organa.piterator.Prometheus;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(value = "app.resources.metrics.enabled", havingValue = "true")
public class PrometheusToPodMonitorConverter implements SubResourceConverter {

    @Override
    public List<? extends HasMetadata> convert(PitApp resource) {
        if (!isEnabled(resource)) {
            return Collections.emptyList();
        }

        return List.of(toK8s(resource, resource.getSpec().getPrometheus()));
    }

    // @formatter:off
    private PodMonitor toK8s(PitApp resource, Prometheus metrics) {
        return new PodMonitorBuilder()
                .withMetadata(resource.getDefaultMeta().build())

                .withNewSpec()
                    .withNewNamespaceSelector().addNewMatchName(resource.getMetadata().getNamespace()).and()
                    .withNewSelector().addToMatchLabels("app", resource.getMetadata().getName()).and()

                    .addNewPodMetricsEndpoint()
                        .withPath(Optional.ofNullable(metrics).map(Prometheus::getPath).orElse("/actuator/prometheus"))
                        .withInterval(Optional.ofNullable(metrics).map(Prometheus::getPollInterval).orElse("60"))
                        .withScheme("HTTPS")

                        .withNewTlsConfig()
                            .withInsecureSkipVerify(true)
                        .and()
                    .endPodMetricsEndpoint()
                .endSpec()
                .build();
    }
    // @formatter:on

    private boolean isEnabled(PitApp resource) {
        return Optional.ofNullable(resource.getSpec().getPrometheus())
                .map(Prometheus::getEnabled)
                .orElse(true);
    }
}
