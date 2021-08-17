package no.politiet.organa.operators.apps.piterator.controller.v1.schema;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Replicas {
    Integer min;
    Integer max;
    Integer cpuThresholdPercentage;
}
