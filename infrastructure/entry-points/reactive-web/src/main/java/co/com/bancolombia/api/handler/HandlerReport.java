package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.mapper.ReportMapper;
import co.com.bancolombia.usecase.report.usecase.api.ReportServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerReport {

    private final ReportServicePort reportServicePort;
    private final ReportMapper reportMapper;

    public Mono<ServerResponse> getReport(ServerRequest serverRequest) {
        String token = serverRequest.exchange().getAttribute("token");
        return reportServicePort.findReport(token)
                .map(reportMapper::toDto)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> getLiveness() {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue("OK");
    }
}
