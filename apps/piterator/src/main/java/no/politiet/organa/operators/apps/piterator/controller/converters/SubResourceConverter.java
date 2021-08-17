package no.politiet.organa.operators.apps.piterator.controller.converters;

import io.fabric8.kubernetes.api.model.HasMetadata;
import no.politiet.organa.operators.apps.piterator.controller.v1.schema.PitApp;

import java.util.List;

public interface SubResourceConverter {
    List<? extends HasMetadata> convert(PitApp resource);
}
