package box.chatgpt.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private final String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
        this.message = throwable.getMessage();
    }
}
