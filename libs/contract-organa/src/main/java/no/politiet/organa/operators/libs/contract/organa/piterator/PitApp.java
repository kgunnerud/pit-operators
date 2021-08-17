package no.politiet.organa.operators.libs.contract.organa.piterator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.api.model.OwnerReferenceBuilder;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;

@Version(PitApp.VERSION)
@ShortNames("pa")
@Group(PitApp.GROUP)
@Kind(PitApp.KIND)
public class PitApp extends CustomResource<PitAppSpec, PitAppStatus> implements Namespaced {
    public static final String VERSION = "v1";
    public static final String GROUP = "pit";
    public static final String KIND = "PitApp";

    @JsonIgnore
    public ObjectMetaBuilder getDefaultMeta() {
        return new ObjectMetaBuilder()
                .withName(getMetadata().getName())
                .withNamespace(getMetadata().getNamespace())
                .withOwnerReferences(getOwnerReference())
                .addToLabels("app", getMetadata().getName());
    }

    @JsonIgnore
    public OwnerReference getOwnerReference() {
        return new OwnerReferenceBuilder()
                .withController(true)
                .withKind(KIND)
                .withApiVersion(VERSION)
                .withName(getMetadata().getName())
                .withUid(getMetadata().getUid())
                .build();
    }
}
