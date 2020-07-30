package com.example.springboot.datafill.excaption;

import org.springframework.stereotype.Component;

/**
 * @author szz
 */

public class UnsupportedDataFillException extends UnsupportedOperationException {

    public UnsupportedDataFillException() { }

    public UnsupportedDataFillException(String errorMsg) {
        super(errorMsg);
    }

}
