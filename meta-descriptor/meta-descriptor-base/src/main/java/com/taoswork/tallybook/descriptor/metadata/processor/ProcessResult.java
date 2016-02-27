package com.taoswork.tallybook.descriptor.metadata.processor;

public enum ProcessResult {
    /**
     * INAPPLICABLE: the processor doesn't handle the data specified
     */
    INAPPLICABLE(1),
    /**
     * The processor doesn't handle the data, but may do some other stuffs, such as setting value
     */
    PASSING_THROUGH(2),

    /**
     * The processor handles the data successfully
     */
    HANDLED(3),

    /**
     * The processor failed to handle the data
     */
    FAILED(4),;


    private final int code;

    ProcessResult(int code) {
        this.code = code;
    }

}
