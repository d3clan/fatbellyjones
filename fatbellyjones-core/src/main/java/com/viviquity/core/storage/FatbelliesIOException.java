package com.viviquity.core.storage;

import java.io.IOException;

public class FatbelliesIOException extends IOException {

    private static final long serialVersionUID = 2970554896519296409L;

    public FatbelliesIOException() {
	super();
    }

    public FatbelliesIOException(String message) {
	super(message);
    }

    public FatbelliesIOException(Throwable cause) {
	super(cause);
    }

    public FatbelliesIOException(String message, Throwable cause) {
	super(message, cause);
    }

}
