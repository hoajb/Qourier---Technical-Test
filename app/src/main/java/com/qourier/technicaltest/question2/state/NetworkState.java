package com.qourier.technicaltest.question2.state;

/**
 * Created by Hoa Nguyen on Jul 21 2019.
 */
public class NetworkState {
    public static NetworkState LOADED = new NetworkState(Status.SUCCESS);
    public static NetworkState LOADING = new NetworkState(Status.RUNNING);

    private Status status;
    private String message;

    public NetworkState(Status status) {
        this.status = status;
    }

    public NetworkState(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static NetworkState error(String mess) {
        return new NetworkState(Status.FAILED, mess);
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
