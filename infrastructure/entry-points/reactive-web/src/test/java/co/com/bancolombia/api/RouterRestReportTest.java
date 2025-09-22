package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.response.ReportResponseDto;
import co.com.bancolombia.api.handler.HandlerReport;
import co.com.bancolombia.api.mapper.ReportMapper;
import co.com.bancolombia.api.router.RouterRestReport;
import co.com.bancolombia.model.report.model.ReportModel;
import co.com.bancolombia.usecase.report.usecase.api.ReportServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

@ContextConfiguration(classes = {RouterRestReport.class, HandlerReport.class})
@WebFluxTest
@ImportAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration.class })
@Import(HandlerReport.class)
@SuppressWarnings("unused")
class RouterRestReportTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ReportServicePort servicePort;

    @MockitoBean
    private ReportMapper mapper;

    @Test
    void getReport_endpoint() {
        ReportModel model = ReportModel.builder()
                .metric("aprobados")
                .value(100)
                .amount(BigDecimal.ZERO)
                .build();

        ReportResponseDto response = new ReportResponseDto("aprobados", 10, BigDecimal.valueOf(5000.));

        given(servicePort.findReport(any())).willReturn(Mono.just(model));
        given(mapper.toDto(model)).willReturn(response);

        webTestClient.get()
                .uri("/api/v1/reportes")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReportResponseDto.class)
                .isEqualTo(response);
    }

    @Test
    void shouldReturnOkForLiveness() {
        webTestClient.get()
                .uri("/liveness")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(String.valueOf(MediaType.TEXT_PLAIN))
                .expectBody(String.class)
                .isEqualTo("OK");
    }
}
