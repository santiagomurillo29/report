package co.com.bancolombia.sqs.listener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportEventDto {
    private String metric;
    private Integer value;
}
