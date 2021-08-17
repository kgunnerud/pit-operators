package no.politiet.organa.operators.apps.piterator;

import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.mock;

@Import(PiteratorStarter.class)
@SpringBootApplication(proxyBeanMethods = false)
class TestConfig {

    @Bean
    KubernetesClient kubernetesClient() {
        return mock(KubernetesClient.class);
    }
}
