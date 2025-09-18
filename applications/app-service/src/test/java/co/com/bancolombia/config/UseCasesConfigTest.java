package co.com.bancolombia.config;

import co.com.bancolombia.usecase.report.usecase.api.ReportServicePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UseCasesConfigTest {

    @Autowired
    private ReportServicePort reportServicePort;

    @Test
    void shouldReportServicePortBean(){
        assertThat(reportServicePort).isNotNull();
    }
}