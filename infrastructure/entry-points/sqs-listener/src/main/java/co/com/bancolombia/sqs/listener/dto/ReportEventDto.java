package co.com.bancolombia.sqs.listener.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportEventDto {
    private String metric;
    private int value;

    @JsonDeserialize(using = com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BigDecimalDeserializer.class)
    private BigDecimal amount;
}
