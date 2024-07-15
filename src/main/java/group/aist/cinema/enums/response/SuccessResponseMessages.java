package group.aist.cinema.enums.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum SuccessResponseMessages implements ResponseMessages {
    SUCCESS("success", "Successfully", HttpStatus.OK),
    CREATED("created","Successfully created",HttpStatus.CREATED),
    NO_CONTENT("not_content", "Not content", HttpStatus.NO_CONTENT);

    private final String key;
    private final String message;
    private final HttpStatus status;;


    @Override
    public String key() {
        return key;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public HttpStatus status() {
        return status;
    }
}
