package co.com.bancolombia.usecase.report.exception;


import co.com.bancolombia.model.report.globalmessage.GlobalMessage;

public class BusinessException extends CoreException{
    public BusinessException(GlobalMessage error) {
        super(error);
    }
}
