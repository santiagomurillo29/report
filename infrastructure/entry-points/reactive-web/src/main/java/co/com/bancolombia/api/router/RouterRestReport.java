package co.com.bancolombia.api.router;

import co.com.bancolombia.api.dto.response.ReportResponseDto;
import co.com.bancolombia.api.handler.HandlerReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestReport {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/reportes",
                    method = RequestMethod.GET,
                    beanClass = HandlerReport.class,
                    beanMethod = "GetReport",
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
            )
    })
    public RouterFunction<ServerResponse> routerFunction(HandlerReport handlerReport) {
        return route(GET("/api/v1/reportes"), handlerReport::GetReport);
    }
}
