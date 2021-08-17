package no.politiet.organa.operators.apps.piterator.controller;

import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import lombok.val;
import no.politiet.organa.operators.apps.piterator.TestBase;
import no.politiet.organa.operators.apps.piterator.controller.handlers.SubResourceConverter;
import no.politiet.organa.operators.apps.piterator.controller.v1.schema.PitApp;
import no.politiet.organa.operators.apps.piterator.controller.v1.schema.PitAppSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@DisplayName("PitApp")
class PitAppTest extends TestBase {

    @Autowired
    private List<SubResourceConverter> subResourceConverters;

    @Test
    @DisplayName("Defaults")
    void scenario1() {
        val resource = new PitApp();
        resource.setMetadata(new ObjectMetaBuilder().withName("derp").withNamespace("dorp").build());
        resource.setSpec(PitAppSpec.builder().build());

        val subResources = subResourceConverters.stream()
                .map(it -> it.convert(resource))
                .flatMap(Collection::stream)
                .toList();

        System.out.printf("");
    }
}
