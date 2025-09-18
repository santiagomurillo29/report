package co.com.bancolombia.model.report.model;

public class ReportModel {

    private String metric;
    private Integer value;

    private ReportModel(Builder builder) {
        this.metric = builder.metric;
        this.value = builder.value;
    }

    public String getMetric() {
        return metric;
    }

    public Integer getValue() {
        return value;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String metric;
        private Integer value;

        public Builder metric(String metric) {
            this.metric = metric;
            return this;
        }

        public Builder value(Integer value) {
            this.value = value;
            return this;
        }

        public ReportModel build() {
            return new ReportModel(this);
        }
    }
}