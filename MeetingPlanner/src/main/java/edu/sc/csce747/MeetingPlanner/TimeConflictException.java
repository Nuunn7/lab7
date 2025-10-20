package edu.sc.csce747.MeetingPlanner;

/**
 * Custom exception thrown when there is a time conflict in the meeting planner system.
 * This exception is raised when attempting to schedule meetings that overlap or
 * when invalid time parameters are provided.
 */
public class TimeConflictException extends Exception {

	private static final long serialVersionUID = 8147822812157714367L;

	/**
	 * Constructs a TimeConflictException with no detail message.
	 */
	public TimeConflictException() {
		super();
	}

	/**
	 * Constructs a TimeConflictException with the specified detail message.
	 * 
	 * @param message the detail message explaining the conflict
	 */
	public TimeConflictException(String message) {
		super(message);
	}

	/**
	 * Constructs a TimeConflictException with the specified cause.
	 * 
	 * @param cause the cause of the exception
	 */
	public TimeConflictException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a TimeConflictException with the specified detail message and cause.
	 * 
	 * @param message the detail message explaining the conflict
	 * @param cause the cause of the exception
	 */
	public TimeConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a TimeConflictException with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or disabled.
	 * 
	 * @param message the detail message explaining the conflict
	 * @param cause the cause of the exception
	 * @param enableSuppression whether or not suppression is enabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public TimeConflictException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}