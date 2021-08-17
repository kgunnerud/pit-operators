package no.politiet.organa.operators.apps.piterator.controller.v1.schema;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PitAppSpec {
    String image;
    Integer replicas;
    Resources resources;
    Probes probes;

    Services services;
    Metrics metrics;
}
