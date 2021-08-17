package no.politiet.organa.operators.apps.piterator.controller.v1.converters;

import io.fabric8.kubernetes.api.model.HasMetadata;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitApp;

import java.util.List;

public interface SubResourceConverter {
    List<? extends HasMetadata> convert(PitApp resource);
}
