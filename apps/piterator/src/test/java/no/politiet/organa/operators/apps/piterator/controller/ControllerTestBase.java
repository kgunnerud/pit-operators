package no.politiet.organa.operators.apps.piterator.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import io.fabric8.kubernetes.api.model.HasMetadata;
import lombok.SneakyThrows;
import lombok.val;
import no.politiet.organa.operators.apps.piterator.TestBase;
import no.politiet.organa.operators.apps.piterator.controller.v1.converters.SubResourceConverter;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitApp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class ControllerTestBase extends TestBase {

    @Autowired
    private List<SubResourceConverter> subResourceConverters;

    protected List<? extends HasMetadata> convert(PitApp resource) {
        return subResourceConverters.stream()
                .map(it -> it.convert(resource))
                .flatMap(Collection::stream)
                .toList();
    }

    protected String toYaml(List<? extends HasMetadata> resources) {
        return resources.stream()
                .map(this::toYaml)
                .collect(joining("---\n"));
    }

    @SneakyThrows
    protected String toYaml(Object v) {
        val mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)).setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(v);
    }
}
