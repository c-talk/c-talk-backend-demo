package me.a632079.ctalk.exception;

import lombok.Data;

/**
 * @className: CTalkException
 * @description: CTalkException - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class CTalkException extends RuntimeException{

    private IErrorCode errorCode;

    public CTalkException(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
