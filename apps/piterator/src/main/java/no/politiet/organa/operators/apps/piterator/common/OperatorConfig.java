package no.politiet.organa.operators.apps.piterator.common;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.Operator;
import io.javaoperatorsdk.operator.api.ResourceController;
import io.javaoperatorsdk.operator.config.runtime.DefaultConfigurationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("k8s")
@Configuration(proxyBeanMethods = false)
class OperatorConfig {

    @Bean
    KubernetesClient kubernetesClient() {
        return new DefaultKubernetesClient();
    }

    @Bean
    Operator operator(KubernetesClient client, List<ResourceController<?>> controllers) {
        Operator operator = new Operator(client, DefaultConfigurationService.instance());
        controllers.forEach(operator::register);
        return operator;
    }
}
