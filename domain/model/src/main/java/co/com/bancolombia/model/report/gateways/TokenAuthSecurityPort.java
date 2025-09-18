package co.com.bancolombia.model.report.gateways;

import reactor.core.publisher.Mono;

public interface TokenAuthSecurityPort {
    Mono<String> getSubject(String token);
}
