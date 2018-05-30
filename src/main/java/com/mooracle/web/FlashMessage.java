package com.mooracle.web;
/** Entry 35: FlashMessage Class
 *  1.  This class as usual provides the Flash message specification to be used in the app
 *  2.  In this class the flash message will consist of a message of what is going on and status of the process.
 *  3.  The main difference here the status is not only SUCCESS, FAILURE, but also INFO
 *  4.  To define the status we will use internal enum to list those three available status
 * */

public class FlashMessage {
    // field declaration
    private String message;
    private Status status;

    // constructor

    public FlashMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    /** Note:
     *  This enum is used to provide what status is available to be used in this web app
     *  each status is a constant thus written in ALL CAPS
     * */
    public static enum Status{
        SUCCESS,
        INFO,
        FAILURE
    }

    //getters and setters for all fields

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
