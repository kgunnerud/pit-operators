package no.politiet.organa.operators.apps.piterator.common;

import io.javaoperatorsdk.operator.Operator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Profile("k8s")
@RequiredArgsConstructor
class OperatorLifecycle implements SmartLifecycle {
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final Operator operator;

    @Override
    public void start() {
        operator.start();
        started.set(true);
    }

    @Override
    public void stop() {
        operator.close();
        started.set(false);
    }

    @Override
    public boolean isRunning() {
        return started.get();
    }
}
