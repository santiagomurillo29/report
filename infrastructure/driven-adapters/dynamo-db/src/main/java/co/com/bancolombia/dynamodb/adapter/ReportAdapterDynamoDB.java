package co.com.bancolombia.dynamodb.adapter;

import co.com.bancolombia.dynamodb.entity.ReportEntity;
import co.com.bancolombia.dynamodb.mapper.ReportMapperDynamo;
import co.com.bancolombia.model.report.gateways.ReportPersistencePort;
import co.com.bancolombia.model.report.model.ReportModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Slf4j
@Repository
public class ReportAdapterDynamoDB implements ReportPersistencePort {

    private final DynamoDbAsyncTable<ReportEntity> table;
    private final ReportMapperDynamo reportMapperDynamo;

    public ReportAdapterDynamoDB(DynamoDbEnhancedAsyncClient dynamoClient,
                                 ReportMapperDynamo reportMapperDynamo)
    {
        this.table = dynamoClient.table("reporte_aprobados",
                TableSchema.fromBean(ReportEntity.class));
        this.reportMapperDynamo = reportMapperDynamo;
    }

    @Override
    public Mono<ReportModel> saveReport(ReportModel reportModel) {
        log.info("Starting save operation for metric: {}", reportModel.getMetric());
        return Mono.fromFuture(table.putItem(reportMapperDynamo.toEntity(reportModel)))
                .thenReturn(reportMapperDynamo.toModel(reportMapperDynamo.toEntity(reportModel)))
                .doOnSuccess(savedReport -> log.info("Report for metric {} saved successfully.", savedReport.getMetric()))
                .doOnError(e -> log.error("Error saving report for metric {}: {}", reportModel.getMetric(), e.getMessage()));

    }

    @Override
    public Mono<ReportModel> findReport(String metric) {
        log.info("Searching report by metric: {}", metric);
        return Mono.fromFuture(table.getItem(Key.builder().partitionValue(metric).build()))
                .map(reportMapperDynamo::toModel)
                .doOnSuccess(foundReport -> log.info("Report found for metric: {}. Value: {}. Amount: {}", foundReport.getMetric(), foundReport.getValue(), foundReport.getAmount()))
                .doOnError(e -> log.error("Error searching report by metric {}: {}", metric, e.getMessage()));
    }
}
