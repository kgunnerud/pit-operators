package no.politiet.organa.operators.libs.contract.organa.piterator;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Replicas {
    Integer min;
    Integer max;
    Integer cpuThresholdPercentage;
}
