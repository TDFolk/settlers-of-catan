package exception;

/**
 * Created by jihoon on 9/27/2016.
 */
public class ConnectException extends Exception {


//
//    /**
//     * Constructs a new exception with the specified detail message,
//     * cause, suppression enabled or disabled, and writable stack
//     * trace enabled or disabled.
//     *
//     * @param message            the detail message.
//     * @param cause              the cause.  (A {@code null} value is permitted,
//     *                           and indicates that the cause is nonexistent or unknown.)
//     * @param enableSuppression  whether or not suppression is enabled
//     *                           or disabled
//     * @param writableStackTrace whether or not the stack trace should
//     *                           be writable
//     * @since 1.7
//     */
//    protected ConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }

    public ConnectException(){

    }

    public ConnectException(String message){
        super(message);
    }

    public ConnectException(Throwable throwable){
        super(throwable);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
