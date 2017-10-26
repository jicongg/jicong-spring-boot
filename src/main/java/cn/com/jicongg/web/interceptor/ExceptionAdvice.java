package com.lamezhi.framework.web.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lamezhi.framework.message.BusinessException;
import com.lamezhi.framework.message.Message;
import com.lamezhi.framework.message.MessageUtil;

/**
 * @author cong.ji
 *     <p>Date: 2017-10-26
 */
@ControllerAdvice
public class ExceptionAdvice {
	
	/**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Message errorHandler(Exception ex) {
        return MessageUtil.error(ex.getMessage());
    }
    
    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Message myErrorHandler(BusinessException ex) {
        return ex.getErrorMsg();
    }
}
