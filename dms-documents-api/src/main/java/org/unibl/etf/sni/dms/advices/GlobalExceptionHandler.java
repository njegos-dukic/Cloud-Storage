package org.unibl.etf.sni.dms.advices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.unibl.etf.sni.dms.exceptions.HttpException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public final ResponseEntity<Object> handleHttpException(HttpException e, HandlerMethod handlerMethod) {
        Log log = getLog(handlerMethod);
        log.error(e);
        if (e.getStatus() == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(e.getData(), e.getStatus());
    }

    private Log getLog(HandlerMethod handlerMethod) {
        return LogFactory.getLog(handlerMethod.getMethod().getDeclaringClass());
    }
}

