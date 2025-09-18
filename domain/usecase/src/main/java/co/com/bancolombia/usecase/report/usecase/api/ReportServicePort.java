package co.com.bancolombia.usecase.report.usecase.api;

import co.com.bancolombia.model.report.model.ReportModel;
import reactor.core.publisher.Mono;

public interface ReportServicePort {
    Mono<ReportModel> findReport(String token);
    Mono<Void> incrementMetric(String metric, int value);
}
