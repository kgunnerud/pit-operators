package no.politiet.organa.operators.apps.piterator.controller.v1.converters;

import io.fabric8.kubernetes.api.model.HasMetadata;
import lombok.extern.slf4j.Slf4j;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(value = "app.resources.deployment.enabled", havingValue = "true")
public class DeploymentConverter implements SubResourceConverter {

    @Override
    public List<? extends HasMetadata> convert(PitApp resource) {
        return Collections.emptyList();
    }
}
