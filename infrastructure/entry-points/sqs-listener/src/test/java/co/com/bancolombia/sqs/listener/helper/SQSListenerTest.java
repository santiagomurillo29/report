package co.com.bancolombia.sqs.listener.helper;

import co.com.bancolombia.sqs.listener.dto.ReportEventDto;
import co.com.bancolombia.sqs.listener.processor.SQSProcessor;
import co.com.bancolombia.usecase.report.usecase.api.ReportServicePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.services.sqs.model.Message;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SQSProcessorTest {

    @InjectMocks
    private SQSProcessor sqsProcessor;

    @Mock
    private ReportServicePort reportServicePort;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void apply_should_process_message_successfully() throws Exception {
        String metric = "aprobado";
        int value = 1;
        BigDecimal totalAmount = BigDecimal.valueOf(5000);

        String jsonBody = "{\"metric\":\"" + metric + "\", \"value\":" + value + "}";

        Message message = Message.builder().body(jsonBody).build();
        ReportEventDto event = new ReportEventDto(metric, value, totalAmount);

        when(objectMapper.readValue(anyString(), any(Class.class))).thenReturn(event);
        when(reportServicePort.incrementMetric(anyString(), anyInt(), any(BigDecimal.class)))
                .thenReturn(Mono.empty());

        Mono<Void> result = sqsProcessor.apply(message);

        StepVerifier.create(result)
                .verifyComplete();
    }
}