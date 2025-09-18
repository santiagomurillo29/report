package co.com.bancolombia.model.report.globalmessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalMessage {

    NOT_FOUND_REPORT(GlobalMessage.STATUS_CODE_404, "No report found"),
    NOT_SAVE_REPORT(GlobalMessage.STATUS_CODE_404, "No save found"),
    METRIC(GlobalMessage.STATUS_CODE_404, "Metric"),
    MICROSERVICE_DOWN(GlobalMessage.STATUS_CODE_500, "Microservice is down"),
    DATABASE_ERROR(GlobalMessage.STATUS_CODE_500, "Database error"),
    INTERNAL_ERROR(GlobalMessage.STATUS_CODE_500, "Internal server error"),
    TIMEOUT(GlobalMessage.STATUS_CODE_500, "time out");

    public static final String STATUS_CODE_400 = "400";
    public static final String STATUS_CODE_404 = "404";
    public static final String STATUS_CODE_500 = "500";

    private final String statusCode;
    private final String message;
}
