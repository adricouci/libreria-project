package edu.upc.eetac.dsa.dsesto.libreria.api;

public class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }
}
