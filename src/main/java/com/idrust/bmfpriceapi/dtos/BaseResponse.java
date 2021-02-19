package com.idrust.bmfpriceapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    private Metadata metadata;
    private Object data;

    public BaseResponse() {
    }

    public BaseResponse(Object data) {
        this.data = data;
    }

    public BaseResponse(Metadata metadata, Object data) {
        this.metadata = metadata;
        this.data = data;
    }

    /**
     * Simples implementação de metadata para carregar erros sobre a requisição
     *
     * @see <a href="https://www.baeldung.com/global-error-handler-in-a-spring-rest-api">
     * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api</a>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Metadata {

        private HttpStatus status;
        private String message;
        private List<String> errors;

        public Metadata(HttpStatus status, String message) {
            super();
            this.status = status;
            this.message = message;
        }

        public Metadata(HttpStatus status, String message, List<String> errors) {
            super();
            this.status = status;
            this.message = message;
            this.errors = errors;
        }

        public Metadata(HttpStatus status, String message, String error) {
            super();
            this.status = status;
            this.message = message;
            errors = Collections.singletonList(error);
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }

    public static BaseResponse ok(final Object data) {
        return of(null, data);
    }

    public static BaseResponse error(HttpStatus status, String message) {
        return error(status, message, null);
    }

    public static BaseResponse error(HttpStatus status, String message, List<String> errors) {
        return of(new Metadata(status, message, errors), null);
    }

    public static BaseResponse of(final Metadata metadata, final Object data) {
        final BaseResponse BaseResponse = new BaseResponse();
        BaseResponse.metadata = metadata;
        BaseResponse.data = data;
        return BaseResponse;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}