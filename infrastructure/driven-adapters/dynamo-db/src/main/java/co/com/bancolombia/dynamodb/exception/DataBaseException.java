package co.com.bancolombia.dynamodb.exception;


import co.com.bancolombia.model.report.globalmessage.GlobalMessage;
import co.com.bancolombia.usecase.report.exception.CoreException;

public class DataBaseException extends CoreException {
    public DataBaseException(GlobalMessage error){
        super(error);
    }

    public DataBaseException(GlobalMessage error, Throwable cause){
        super(error);
        initCause(cause);
    }
}



