package no.politiet.organa.operators.apps.piterator.common;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.javaoperatorsdk.operator.Operator;
import io.javaoperatorsdk.operator.config.runtime.DefaultConfigurationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class OperatorConfig {

    @Bean
    Operator operator() {
        return new Operator(new DefaultKubernetesClient(), DefaultConfigurationService.instance());
    }
}
