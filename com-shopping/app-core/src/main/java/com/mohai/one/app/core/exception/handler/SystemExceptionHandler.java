package com.mohai.one.app.core.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mohai.one.app.core.exception.BadRequestException;
import com.mohai.one.app.core.exception.BusinessException;
import com.mohai.one.app.core.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  异常处理器
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/22 15:12
 */
@RestControllerAdvice
public class SystemExceptionHandler {

    private static final Logger LOG =  LoggerFactory.getLogger(SystemExceptionHandler.class);

    /**
     * 处理自定义异常 多用于登录请求异常
     */
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<BaseError> badRequestException(BadRequestException e) {
        // 打印堆栈信息
        LOG.error(ExceptionUtil.getStackTrace(e));
        return buildResponseEntity(BaseError.error(e.getMessage()));
    }

    /**
     * 处理自定义异常 多用于业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<BaseError> businessException(BusinessException e) {
        // 打印堆栈信息
        LOG.error(ExceptionUtil.getStackTrace(e));
        BaseError baseError = BaseError.error(e.getMessage());
        baseError.setStatus(500);
        return buildResponseEntity(baseError);
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseError> badCredentialsException(BadCredentialsException e){
        // 打印堆栈信息
        String message = "Bad credentials".equals(e.getMessage()) ? "用户名或密码错误" : e.getMessage();
        LOG.error(message);
        return buildResponseEntity(BaseError.error(message));
    }

    /**
     *  全局异常处理类先拦截到 AccessDeniedException
     *  所以自定义的AccessDeniedHandler就失效了
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseError> accessDeniedException(AccessDeniedException e){
        // 打印堆栈信息
        LOG.error(e.getMessage());
        BaseError baseError = BaseError.error("不允许访问！");
        baseError.setStatus(403);
        return buildResponseEntity(baseError);
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 打印堆栈信息
        LOG.error(ExceptionUtil.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if(msg.equals(message)){
            message = str[1] + ":" + message;
        }
        return buildResponseEntity(BaseError.error(message));
    }

    /**
     * 处理所有未知异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseError> handleException(Throwable e){
        // 打印堆栈信息
        LOG.error(ExceptionUtil.getStackTrace(e));
        BaseError baseError = BaseError.error("系统异常，请联系管理员！");
        baseError.setStatus(500);
        return buildResponseEntity(baseError);
    }

    // 构建响应体信息
    private ResponseEntity<BaseError> buildResponseEntity(BaseError baseError) {
        return new ResponseEntity<BaseError>(baseError, HttpStatus.valueOf(baseError.getStatus()));
    }

    static class BaseError {
        private Integer status = 400;
        private String message;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private final LocalDateTime timestamp;
        private BaseError() {
            timestamp = LocalDateTime.now();
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public static BaseError error(String message){
            BaseError baseError = new BaseError();
            baseError.setMessage(message);
            return baseError;
        }
    }

}