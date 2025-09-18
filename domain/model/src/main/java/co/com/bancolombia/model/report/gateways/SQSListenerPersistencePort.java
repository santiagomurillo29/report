package co.com.bancolombia.model.report.gateways;

import reactor.core.publisher.Mono;

public interface SQSListenerPersistencePort {
    Mono<Void> apply(String message);
}
