package co.com.bancolombia.sqs.listener.mapper;

import co.com.bancolombia.model.report.model.ReportModel;
import co.com.bancolombia.sqs.listener.dto.ReportEventDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportEventMapper {
    ReportEventDto toDto (ReportModel reportModel);
    ReportModel toModel (ReportEventDto reportEventDto);
}
