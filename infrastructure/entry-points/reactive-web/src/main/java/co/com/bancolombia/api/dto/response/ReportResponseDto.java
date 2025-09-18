package co.com.bancolombia.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(name = "ReportResponse", description = "Model represent a report on database")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportResponseDto {
    @Schema(description = "Unique identifier of the report")
    private String metric;

    @Schema(description = "ReportModel's total approved")
    private Integer totalApproved;

}
