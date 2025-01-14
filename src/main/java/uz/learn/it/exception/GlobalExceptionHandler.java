package uz.learn.it.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.learn.it.dto.response.APIResponseDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<APIResponseDTO<String>> handleUsernameNotFound(AlreadyExistException ex) {
        APIResponseDTO<String> apiResponseDto = new APIResponseDTO<>(400, ex.getMessage(),
                null);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponseDTO<String>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        Set<String> processedFields = new HashSet<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if (processedFields.add(error.getField())) {
                errors.add(error.getDefaultMessage());
            }
        }

        APIResponseDTO<String> apiResponseDto = new APIResponseDTO<>(400, errors.toString(), null);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIResponseDTO<String>> handleNotFoundException(NotFoundException ex) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>(404, ex.getMessage(), null);

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BalanceNotValidException.class)
    public ResponseEntity<APIResponseDTO<String>> handleBalanceNotValidException(BalanceNotValidException ex) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>(400, ex.getMessage(), null);

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
