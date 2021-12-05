package no.politiet.organa.operators.apps.piterator.controller;

import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import lombok.val;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitApp;
import no.politiet.organa.operators.libs.contract.organa.piterator.PitAppSpec;

class Testdata {

    static PitApp minimumValid() {
        val resource = new PitApp();
        resource.setMetadata(new ObjectMetaBuilder()
                .withName("Api")
                .withNamespace("NTP")
                .withUid("d73jdlske213")
                .build());

        resource.setSpec(PitAppSpec.builder()
                        .image("hub.test.no/app:123")
                .build());
        return resource;
    }
}
