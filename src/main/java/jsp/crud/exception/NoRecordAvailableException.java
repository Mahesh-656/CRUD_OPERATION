package jsp.crud.exception;

public class NoRecordAvailableException extends RuntimeException {
	public NoRecordAvailableException(String message) {
		super(message);
	}
}