package co.com.bancolombia.model.report.model;

import java.math.BigDecimal;

public class ReportModel {

    private String metric;
    private int value;
    private BigDecimal amount;

    private ReportModel(Builder builder) {
        this.metric = builder.metric;
        this.value = builder.value;
        this.amount = builder.amount;
    }

    public String getMetric() {
        return metric;
    }

    public int getValue() {
        return value;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setValue(int value) {this.value = value;}

    public BigDecimal getAmount() {return amount;}

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String metric;
        private int value;
        private BigDecimal amount;

        public Builder metric(String metric) {
            this.metric = metric;
            return this;
        }

        public Builder value(int value) {
            this.value = value;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public ReportModel build() {
            return new ReportModel(this);
        }
    }
}