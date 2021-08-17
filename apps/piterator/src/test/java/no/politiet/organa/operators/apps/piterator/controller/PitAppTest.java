package no.politiet.organa.operators.apps.piterator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import lombok.SneakyThrows;
import lombok.val;
import no.politiet.organa.operators.apps.piterator.TestBase;
import no.politiet.organa.operators.apps.piterator.controller.v1.converters.SubResourceConverter;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitApp;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitAppSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@DisplayName("PitApp")
class PitAppTest extends TestBase {

    @Autowired
    private List<SubResourceConverter> subResourceConverters;

    @Test
    @SneakyThrows
    @DisplayName("Defaults")
    void scenario1() {
        val resource = new PitApp();
        resource.setMetadata(new ObjectMetaBuilder().withName("Api").withNamespace("NTP").withUid("d73jdlske213").build());
        resource.setSpec(PitAppSpec.builder().build());

        val subResources = subResourceConverters.stream()
                .map(it -> it.convert(resource))
                .flatMap(Collection::stream)
                .toList();

        val mapper = new ObjectMapper(new YAMLFactory());
        val result = new LinkedList<String>();
        for (val res : subResources) {
            result.add(mapper.writeValueAsString(res));
        }

        val pitAppYaml = mapper.writeValueAsString(resource);

        val derp = result.stream()
                        .collect(Collectors.joining(""));


        System.out.printf("");
    }
}
