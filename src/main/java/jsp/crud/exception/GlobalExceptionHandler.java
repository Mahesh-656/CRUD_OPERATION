package jsp.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jsp.crud.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException exception) {
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage("Failure");
		res.setData(exception.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(res, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handlerNRAE(NoRecordAvailableException exception) {
		ResponseStructure<String> rs = new ResponseStructure<String>();
		rs.setMessage("Failure");
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setData(exception.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.NOT_FOUND);
	}

}
