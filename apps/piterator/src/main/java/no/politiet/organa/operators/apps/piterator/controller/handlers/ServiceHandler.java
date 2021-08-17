package no.politiet.organa.operators.apps.piterator.controller.handlers;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServicePort;
import io.fabric8.kubernetes.api.model.ServicePortBuilder;
import lombok.val;
import no.politiet.organa.operators.apps.piterator.controller.v1.schema.PitApp;
import no.politiet.organa.operators.apps.piterator.controller.v1.schema.Services;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@ConditionalOnProperty(value = "app.resources.services.enabled", havingValue = "true")
public class ServiceHandler implements SubResourceConverter {

    @Override
    public List<? extends HasMetadata> convert(PitApp resource) {
        if (!isEnabled(resource)) {
            return Collections.emptyList();
        }

        val ports = Optional.ofNullable(resource.getSpec().getServices()).map(Services::getPorts).orElse(null);
        return List.of(toK8s(resource, ports));
    }

    private Service toK8s(PitApp resource, List<Services.ServicePort> ports) {
        return new ServiceBuilder()
                .withMetadata(resource.getMetadata())

                .withNewSpec()
                .addToSelector("app", resource.getMetadata().getName())
                .withType("LoadBalancer")

                .addAllToPorts(toPorts(ports))

                .and()

                .build();
    }

    private List<ServicePort> toPorts(List<Services.ServicePort> ports) {
        if (isEmpty(ports)) {
            return List.of(toPort(null));
        }

        return ports.stream()
                .map(this::toPort)
                .toList();
    }

    private ServicePort toPort(Services.ServicePort port) {
        return new ServicePortBuilder()
                .withName(Optional.ofNullable(port).map(Services.ServicePort::getName).orElse("https"))
                .withProtocol(Optional.ofNullable(port).map(Services.ServicePort::getProtocol).orElse("TCP"))
                .withPort(Optional.ofNullable(port).map(Services.ServicePort::getPort).orElse(443))
                .withTargetPort(Optional.ofNullable(port).map(Services.ServicePort::getTargetPort).map(IntOrString::new).orElse(new IntOrString(8443)))
                .build();
    }

    private boolean isEnabled(PitApp resource) {
        return Optional.ofNullable(resource.getSpec().getServices())
                .map(Services::getEnabled)
                .orElse(true);
    }
}
