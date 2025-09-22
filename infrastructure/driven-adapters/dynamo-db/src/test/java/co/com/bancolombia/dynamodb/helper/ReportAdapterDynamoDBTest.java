package co.com.bancolombia.dynamodb.helper;

import co.com.bancolombia.dynamodb.adapter.ReportAdapterDynamoDB;
import co.com.bancolombia.dynamodb.entity.ReportEntity;
import co.com.bancolombia.dynamodb.mapper.ReportMapperDynamo;
import co.com.bancolombia.model.report.model.ReportModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportAdapterDynamoDBTest {

    @Mock
    private DynamoDbAsyncTable<ReportEntity> table;

    @Mock
    private ReportMapperDynamo reportMapperDynamo;

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoClient;

    private ReportAdapterDynamoDB adapter;

    @BeforeEach
    void setUp() {
        when(dynamoClient.table(anyString(), any(TableSchema.class))).thenReturn(table);

        adapter = new ReportAdapterDynamoDB(
                dynamoClient,
                reportMapperDynamo
        );
    }
    @Test
    void saveReport_shouldPersistAndReturnModel() {
        ReportModel inputModel = ReportModel.builder()
                .metric("aprobados")
                .value(100)
                .build();

        ReportEntity entity = new ReportEntity();

        entity.setMetric("aprobados");
        entity.setValue(100);

        when(reportMapperDynamo.toEntity(inputModel)).thenReturn(entity);
        when(reportMapperDynamo.toModel(entity)).thenReturn(inputModel);
        when(table.putItem(any(ReportEntity.class))).thenReturn(CompletableFuture.completedFuture(null));

        StepVerifier.create(adapter.saveReport(inputModel))
                .expectNextMatches(result ->
                        "aprobados".equals(result.getMetric()) &&
                                result.getValue() == 100)
                .verifyComplete();
    }

    @Test
    void findReport_shouldFindAndReturnModel() {
        String metric = "aprobados";
        ReportEntity entity = new ReportEntity();
        entity.setMetric(metric);
        entity.setValue(100);
        ReportModel expectedModel  = ReportModel.builder()
                .metric(metric)
                .value(100)
                .build();

        when(table.getItem(any(Key.class))).thenReturn(CompletableFuture.completedFuture(entity));
        when(reportMapperDynamo.toModel(entity)).thenReturn(expectedModel);

        StepVerifier.create(adapter.findReport(metric))
                .expectNextMatches(result ->
                        metric.equals(result.getMetric()) &&
                                result.getValue() == 100)
                .verifyComplete();
    }
}
