package co.com.bancolombia.config;

import co.com.bancolombia.model.report.gateways.ReportPersistencePort;
import co.com.bancolombia.model.report.gateways.TokenAuthSecurityPort;
import co.com.bancolombia.usecase.report.usecase.ReportUseCase;
import co.com.bancolombia.usecase.report.usecase.api.ReportServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

        private final ReportPersistencePort reportPersistencePort;
        private final TokenAuthSecurityPort tokenAuthSecurityPort;

        @Bean
        public ReportServicePort reportServicePort() {
                return new ReportUseCase(
                        reportPersistencePort,
                        tokenAuthSecurityPort
                );
        }
}
