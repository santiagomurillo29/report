package co.com.bancolombia.usecase.report.usecase;

import co.com.bancolombia.model.report.gateways.ReportPersistencePort;
import co.com.bancolombia.model.report.gateways.TokenAuthSecurityPort;
import co.com.bancolombia.model.report.globalmessage.GlobalMessage;
import co.com.bancolombia.model.report.model.ReportModel;
import co.com.bancolombia.usecase.report.exception.BusinessException;
import co.com.bancolombia.usecase.report.usecase.api.ReportServicePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ReportUseCase implements ReportServicePort {

    private final ReportPersistencePort reportPersistencePort;
    private final TokenAuthSecurityPort tokenAuthSecurityPort;

    @Override
    public Mono<ReportModel> findReport(String token) {
        return tokenAuthSecurityPort.getSubject(token)
                .flatMap(subject ->
                        reportPersistencePort.findReport("APPROVED")
                                .switchIfEmpty(Mono.error(new BusinessException(GlobalMessage.NOT_FOUND_REPORT))));
    }

    @Override
    public Mono<Void> incrementMetric(String metric, int value, BigDecimal amount) {
        return reportPersistencePort.findReport(metric)
                .switchIfEmpty(Mono.just(ReportModel.builder().metric(metric).value(0).amount(BigDecimal.ZERO).build()))
                .flatMap(report -> {
                    report.setValue(report.getValue() + value);
                    report.setAmount(report.getAmount().add(amount));
                    return reportPersistencePort.saveReport(report).then()
                            .onErrorResume(e -> Mono.error(new BusinessException(GlobalMessage.NOT_SAVE_REPORT)));
                });
    }
}
