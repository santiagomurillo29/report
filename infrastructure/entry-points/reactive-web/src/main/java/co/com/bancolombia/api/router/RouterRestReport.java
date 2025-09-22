package co.com.bancolombia.api.router;

import co.com.bancolombia.api.dto.response.ReportResponseDto;
import co.com.bancolombia.api.handler.HandlerReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestReport {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/reportes",
                    method = RequestMethod.GET,
                    beanClass = HandlerReport.class,
                    beanMethod = "getReport",
                    operation = @Operation(
                            operationId = "getReport",
                            summary = "Get all report",
                            description = "Return all reports",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "ReportModel of values of loan application",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = ReportResponseDto.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "ReportModel not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/liveness",
                    method = RequestMethod.GET,
                    beanClass = HandlerReport.class,
                    beanMethod = "getLiveness",
                    produces = "text/plain",
                    operation = @Operation(
                            operationId = "getLiveness",
                            summary = "Check liveness",
                            description = "Always returns OK if the service is alive.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Service is alive",
                                            content = @Content(
                                                    mediaType = "text/plain",
                                                    schema = @Schema(example = "OK")
                                            )
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(HandlerReport handlerReport) {
        return route(GET("/api/v1/reportes"), handlerReport::getReport)
                .andRoute(GET("/liveness"), request -> handlerReport.getLiveness());
    }
}
