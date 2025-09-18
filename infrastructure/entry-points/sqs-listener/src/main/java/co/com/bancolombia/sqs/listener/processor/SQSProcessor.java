package co.com.bancolombia.sqs.listener.processor;

import co.com.bancolombia.sqs.listener.dto.ReportEventDto;
import co.com.bancolombia.usecase.report.usecase.api.ReportServicePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.io.IOException;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {

    private final ReportServicePort reportServicePort;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        String body = message.body();
        ReportEventDto event;

        try{
            event = parse(body);
        } catch (IOException e) {
            return Mono.error(new RuntimeException("Error parsing SQS message", e));
        }

        return reportServicePort.incrementMetric(event.getMetric(), event.getValue())
                .doOnSuccess(r -> System.out.println("Reporte actualizado en Dynamo"))
                .then();
    }

    private ReportEventDto parse(String json) throws IOException{
        return objectMapper.readValue(json, ReportEventDto.class);
    }
}
