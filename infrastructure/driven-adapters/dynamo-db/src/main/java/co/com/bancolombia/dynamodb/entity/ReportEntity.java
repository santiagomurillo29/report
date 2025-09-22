package co.com.bancolombia.dynamodb.entity;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;

@Setter
@DynamoDbBean
public class ReportEntity {

    private String metric;
    private int value;
    private BigDecimal amount;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("metric")
    public String getMetric() {
        return metric;
    }

    @DynamoDbAttribute("valor")
    public int getValue() {
        return value;
    }

    @DynamoDbAttribute("amount")
    public BigDecimal getAmount() {
        return amount;
    }
}
