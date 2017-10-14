package cn.com.jicongg.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.validation.ObjectError;

/**
 * 业务类exception.
 *
 * 2017年9月27日
 * @author jicong.
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6857894984294367213L;
	
	private Message errorMsg;
    
    /**
     * BusinessException.
     * @param es 
     */
    public BusinessException(List<ObjectError> es){
        super();
        List<Info> list = new ArrayList<Info>();
        for(ObjectError e : es){
                list.add(new Info(e.getObjectName(), MessageUtil.getMsgCode(e.getDefaultMessage()), null));
        }
        this.errorMsg = Message.error(list);
    }
    
    /**
     * BusinessException.
     * @param root 
     */
    public BusinessException(Object root){
        super();
        this.errorMsg = Message.error(root);
    }
    
    /**
     * BusinessException.
     * @param me 
     */
    public BusinessException(Info...me){
        super();
        this.errorMsg = Message.error(me);
    }
    
    /**
     * BusinessException.
     * @param msg 
     */
    public BusinessException(Message msg){
        super();
        this.errorMsg = msg;
    }

    /**
     * 违反唯一约束条件异常.
     * getErrorMsg.
     * @param cve 
     * @return Message
     */
    public static Message getErrorMsg(ConstraintViolationException cve){
        List<Info> list = new ArrayList<Info>();
        
        Set<ConstraintViolation<?>> s = cve.getConstraintViolations();
        Iterator<ConstraintViolation<?>> it = s.iterator();
        while(it.hasNext()){
            ConstraintViolation<?> cv = (ConstraintViolation<?>) it.next();
            list.add(new Info(cv.getPropertyPath().toString(), cv.getMessage(), cv.getInvalidValue()));
        }
        return Message.error(list);
    }
    
    /**
     * getErrorMsg.
     * @param info 
     * @return Message
     */
    public static Message getErrorMsg(Info info){
        List<Info> list = new ArrayList<Info>();
        list.add(info);
        return Message.error(list);
    }
    
    public Message getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Message errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    @Override
    public String getMessage(){
        return this.getErrorMsg().getRoot().toString();
    }

}
