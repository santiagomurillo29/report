package co.com.bancolombia.usecase.report;

import co.com.bancolombia.model.report.gateways.ReportPersistencePort;
import co.com.bancolombia.model.report.gateways.TokenAuthSecurityPort;
import co.com.bancolombia.model.report.model.ReportModel;
import co.com.bancolombia.usecase.report.usecase.ReportUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReportUseCaseTest {

    @Mock
    ReportPersistencePort reportPersistencePort;

    @Mock
    TokenAuthSecurityPort tokenAuthSecurityPort;

    @InjectMocks
    ReportUseCase useCase;

    private final String token = "anyToken";
    private final String metric = "metricTest";
    private final String subject = "testSubject";

    @Test
    void findReport_shouldReturnReport_whenFound() {
        ReportModel report = ReportModel.builder()
                .metric(metric)
                .value(100)
                .build();

        report.setMetric(metric);
        report.setValue(100);

        given(tokenAuthSecurityPort.getSubject(token)).willReturn(Mono.just(subject));
        given(reportPersistencePort.findReport("APPROVED")).willReturn(Mono.just(report));

        StepVerifier.create(useCase.findReport(token))
                .expectNext(report)
                .verifyComplete();

        verify(reportPersistencePort).findReport("APPROVED");
    }

    @Test
    void incrementMetric_shouldIncrementValue_whenReportExists() {
        ReportModel existingReport = ReportModel.builder()
                .metric(metric)
                .value(10)
                .amount(BigDecimal.ZERO)
                .build();

        int valueToAdd = 5;

        given(reportPersistencePort.findReport(metric)).willReturn(Mono.just(existingReport));
        given(reportPersistencePort.saveReport(any(ReportModel.class))).willReturn(Mono.just(existingReport));

        StepVerifier.create(useCase.incrementMetric(metric, valueToAdd, BigDecimal.valueOf(5000.00)))
                .verifyComplete();

        verify(reportPersistencePort).saveReport(any(ReportModel.class));
    }
}
