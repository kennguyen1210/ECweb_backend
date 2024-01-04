package ra.academy.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.academy.exception.UsernameAndPasswordIncorrectException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String, String> handleNotFound(NoSuchElementException e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return map;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRuntime(RuntimeException e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return map;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleRegister(MethodArgumentNotValidException e){
        Map<String,String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err->map.put(err.getField(),err.getDefaultMessage()));
        return map;
    }
    @ExceptionHandler(UsernameAndPasswordIncorrectException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,String> handleUsernameAndPasswordIncorrect(UsernameAndPasswordIncorrectException e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return map;
    }
}
