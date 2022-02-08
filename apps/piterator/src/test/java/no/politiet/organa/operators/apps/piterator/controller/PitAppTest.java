package no.politiet.organa.operators.apps.piterator.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static no.politiet.organa.operators.apps.piterator.controller.Testdata.minimumValid;

@DisplayName("PitApp")
class PitAppTest extends ControllerTestBase {

    @Test
    @DisplayName("Defaults")
    void scenario1() {
        val resource = minimumValid();

        val result = convert(resource);

        System.out.println("");
    }
}
