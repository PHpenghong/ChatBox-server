package box.chatgpt.ohs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class ResulBody<T> {

    private Integer code;

    private String message;

    public T data;

    private static final Integer SUCCESS_CODE = 200;

    private static final Integer FAILED_CODE = 400;

    private static final String SUCCESS_MESSAGE = "success";

    public static <T> ResulBody<T> success(T data) {
        ResulBody<T> resulBody = new ResulBody<>();
        resulBody.setCode(SUCCESS_CODE);
        resulBody.setMessage(SUCCESS_MESSAGE);
        resulBody.setData(data);
        return resulBody;
    }

    public static <T> ResulBody<T> failed(String message) {
        ResulBody<T> resulBody = new ResulBody<>();
        resulBody.setCode(FAILED_CODE);
        resulBody.setMessage(message);
        return resulBody;
    }
}
