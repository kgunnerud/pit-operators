package no.politiet.organa.operators.apps.piterator.controller.v1.converters;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.IntOrStringBuilder;
import io.fabric8.kubernetes.api.model.Probe;
import io.fabric8.kubernetes.api.model.ProbeBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitApp;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitAppSpec;
import no.politiet.organa.operators.libs.contract.organa.piterator.Probes;
import no.politiet.organa.operators.libs.contract.organa.piterator.Replicas;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@ConditionalOnProperty(value = "app.resources.deployment.enabled", havingValue = "true")
public class PitAppToDeploymentConverter implements SubResourceConverter {

    @Override
    // @formatter:off
    public List<? extends HasMetadata> convert(PitApp resource) {
        val spec = resource.getSpec();

        val deployment = new DeploymentBuilder()
                .withMetadata(resource.getDefaultMeta().build())

                .withNewSpec()
                    .withNewSelector().addToMatchLabels("app", resource.getMetadata().getName()).and()

                    .withReplicas(Optional.ofNullable(spec).map(PitAppSpec::getReplicas).map(Replicas::getMin).orElse(2))
                    .withNewTemplate().withNewSpec().addToContainers(pitAppToContainer(resource))
                .and()
                ;

        return Collections.emptyList();
    }

    private Container pitAppToContainer(PitApp resource) {
        return new ContainerBuilder()
                .withName("app")
                .withImage(resource.getSpec().getImage())
                .build();
    }

    private Probe pitAppProbeToProbe(Probes.Probe probe, Integer initialDelay) {
        return new ProbeBuilder()
                .withFailureThreshold(probe.getFailureThreshold())
                .withInitialDelaySeconds(Optional.ofNullable(probe.getInitialDelay()).orElse(initialDelay))
                .withPeriodSeconds(probe.getPeriodSeconds())
                .withTimeoutSeconds(probe.getTimeout())
                .withNewHttpGet()
                    .withPath(probe.getPath())
                    .withPort(new IntOrStringBuilder().withIntVal(probe.getPort()).build())
                .and()
                .build();
    }
    // @formatter:on

    @AllArgsConstructor
    enum ProbeType {
        STARTUP(60, 5, 1, 1),
        LIVENESS(3, 5, 1, 1),
        READINESS(3, 5, 1, 1);

        int failureTreshold;
        int initialDelay;
        int periodSeconds;
        int timeoutSeconds;
    }
}
