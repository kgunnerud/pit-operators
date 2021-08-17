package no.politiet.organa.operators.apps.piterator.controller.v1;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.Context;
import io.javaoperatorsdk.operator.api.Controller;
import io.javaoperatorsdk.operator.api.DeleteControl;
import io.javaoperatorsdk.operator.api.ResourceController;
import io.javaoperatorsdk.operator.api.UpdateControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import no.politiet.organa.operators.apps.piterator.controller.handlers.SubResourceConverter;
import no.politiet.organa.operators.apps.piterator.controller.v1.schema.PitApp;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@Controller
@RequiredArgsConstructor
public class PitAppController implements ResourceController<PitApp> {
    private final List<SubResourceConverter> subResourceConverters;
    private final KubernetesClient k8sClient;

    @Override
    public DeleteControl deleteResource(PitApp resource, Context<PitApp> context) {
        addMdc(resource);
        log.info("Deleting resource");
        MDC.clear();
        return DeleteControl.DEFAULT_DELETE;
    }

    @Override
    public UpdateControl<PitApp> createOrUpdateResource(PitApp resource, Context<PitApp> context) {
        try {
            addMdc(resource);
            log.info("CreateOrUpdate");

            val subResources = subResourceConverters.stream()
                    .map(it -> it.convert(resource))
                    .flatMap(Collection::stream)
                    .peek(it -> log.info("Resource of type: {}:{} created with namespace and name: {}:{}", it.getKind(), it.getApiVersion(), resource.getMetadata().getNamespace(), it.getMetadata().getName()))
                    .map(it -> (HasMetadata) it)
                    .toList();

            k8sClient.resourceList(subResources).inNamespace(resource.getMetadata().getNamespace()).createOrReplace();

            return UpdateControl.updateCustomResource(resource);
        } finally {
            MDC.clear();
        }
    }

    private void addMdc(PitApp resource) {
        MDC.put("name", resource.getMetadata().getName());
        MDC.put("namespace", resource.getMetadata().getNamespace());
        MDC.put("generation", String.valueOf(resource.getMetadata().getGeneration()));
    }
}
