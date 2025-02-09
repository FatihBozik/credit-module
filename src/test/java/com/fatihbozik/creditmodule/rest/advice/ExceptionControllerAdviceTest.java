package com.fatihbozik.creditmodule.rest.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionControllerAdviceTest {
    @Mock
    private HttpServletRequest request;

    private ExceptionControllerAdvice exceptionControllerAdvice;

    @BeforeEach
    void setUp() {
        exceptionControllerAdvice = new ExceptionControllerAdvice();
    }

    @Test
    void handleGeneralException_shouldReturnInternalServerError() {
        Exception exception = new Exception("Test exception");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/test"));

        ResponseEntity<ProblemDetail> response = exceptionControllerAdvice.handleGeneralException(exception, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test exception", response.getBody().getDetail());
        assertEquals("Exception", response.getBody().getTitle());
        assertEquals(URI.create("http://localhost/test"), response.getBody().getType());
    }

    @Test
    void handleDataIntegrityViolationException_shouldReturnNotFound() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Data integrity violation");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/test"));

        ResponseEntity<ProblemDetail> response = exceptionControllerAdvice.handleDataIntegrityViolationException(exception, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Data integrity violation", response.getBody().getDetail());
        assertEquals("DataIntegrityViolationException", response.getBody().getTitle());
        assertEquals(URI.create("http://localhost/test"), response.getBody().getType());
    }

    @Test
    void handleMethodArgumentNotValidException_shouldReturnBadRequest() throws NoSuchMethodException {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        Method method = this.getClass().getDeclaredMethod("handleMethodArgumentNotValidException_shouldReturnBadRequest");
        MethodParameter methodParameter = new MethodParameter(method, -1); // Use -1 for the method itself

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/test"));

        ResponseEntity<ProblemDetail> response = exceptionControllerAdvice.handleMethodArgumentNotValidException(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("MethodArgumentNotValidException", response.getBody().getTitle());
        assertEquals(URI.create("http://localhost/test"), response.getBody().getType());
    }
}
