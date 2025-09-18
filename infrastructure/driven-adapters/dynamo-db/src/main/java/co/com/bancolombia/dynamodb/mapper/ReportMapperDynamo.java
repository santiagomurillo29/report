package co.com.bancolombia.dynamodb.mapper;

import co.com.bancolombia.dynamodb.entity.ReportEntity;
import co.com.bancolombia.model.report.model.ReportModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapperDynamo {
    ReportEntity toEntity(ReportModel reportApproved);
    ReportModel toModel(ReportEntity reportEntity);
}
