package no.politiet.organa.operators.libs.contract.organa.piterator;

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
    Prometheus prometheus;
}
