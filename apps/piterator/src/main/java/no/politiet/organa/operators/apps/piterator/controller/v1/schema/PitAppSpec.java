package no.politiet.organa.operators.apps.piterator.controller.v1.schema;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PitAppSpec {
    String image;
    Replicas replicas;
    Resources resources;
    Probes probes;

    Services services;
    Metrics metrics;
}
