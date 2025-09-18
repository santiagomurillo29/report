package co.com.bancolombia.model.report.gateways;

import co.com.bancolombia.model.report.model.ReportModel;
import reactor.core.publisher.Mono;

public interface ReportPersistencePort {
    Mono<ReportModel> saveReport (ReportModel reportModel);
    Mono<ReportModel> findReport (String metric);
}
