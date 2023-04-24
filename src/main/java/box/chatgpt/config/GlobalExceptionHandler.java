package box.chatgpt.config;

import box.chatgpt.exception.BusinessException;
import box.chatgpt.ohs.ResulBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉器
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResulBody businessHandler(BusinessException e) {
        return ResulBody.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResulBody exceptionHandler(Exception e) {
        log.error("系统异常，", e);
        return ResulBody.failed("系统繁忙，请稍后再试哟～");
    }
}
