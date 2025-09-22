package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.response.ReportResponseDto;
import co.com.bancolombia.model.report.model.ReportModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @Mapping(target = "totalAmount", source = "amount")
    @Mapping(target = "totalApproved", source = "value")
    ReportResponseDto toDto (ReportModel reportModel);
}
