package no.politiet.organa.operators.apps.piterator.controller.v1.converters;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServicePort;
import io.fabric8.kubernetes.api.model.ServicePortBuilder;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitApp;
import no.politiet.organa.operators.libs.contract.organa.piterator.Services;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(value = "app.resources.services.enabled", havingValue = "true")
public class ServiceToServiceConverter implements SubResourceConverter {

    @Override
    public List<? extends HasMetadata> convert(PitApp resource) {
        if (!isEnabled(resource)) {
            return Collections.emptyList();
        }

        return Optional.ofNullable(resource.getSpec().getServices())
                .map(Services::getPorts)
                .map(it -> List.of(toK8s(resource, it)))
                .orElseGet(() -> List.of(toK8s(resource, null)));
    }

    // @formatter:off
    private Service toK8s(PitApp resource, List<Services.ServicePort> ports) {
        return new ServiceBuilder()
                .withMetadata(resource.getDefaultMeta().build())

                .withNewSpec()
                    .addToSelector("app", resource.getMetadata().getName())
                    .withType("LoadBalancer")

                    .addAllToPorts(Optional.ofNullable(ports)
                        .map(it -> it.stream()
                                .map(this::toPort)
                                .toList())
                        .orElse(List.of(toPort(null))))
                .endSpec()
                .build();
    }

    private ServicePort toPort(Services.ServicePort port) {
        return new ServicePortBuilder()
                .withName(Optional.ofNullable(port).map(Services.ServicePort::getName).orElse("https"))
                .withProtocol(Optional.ofNullable(port).map(Services.ServicePort::getProtocol).orElse("TCP"))
                .withPort(Optional.ofNullable(port).map(Services.ServicePort::getPort).orElse(443))
                .withTargetPort(Optional.ofNullable(port).map(Services.ServicePort::getTargetPort).map(IntOrString::new).orElse(new IntOrString(8443)))
                .build();
    }
    // @formatter:on

    private boolean isEnabled(PitApp resource) {
        return Optional.ofNullable(resource.getSpec().getServices())
                .map(Services::getEnabled)
                .orElse(true);
    }
}
