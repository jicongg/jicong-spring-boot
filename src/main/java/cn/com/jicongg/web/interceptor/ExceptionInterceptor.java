package cn.com.jicongg.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.UndeclaredThrowableException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.jicongg.message.BusinessException;
import cn.com.jicongg.message.Message;
import cn.com.jicongg.util.JsonUtil;

/**
 * exception interceptor. 2017年9月27日
 * 
 * @author jicong.
 */
public class ExceptionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * afterCompletion 会话结束时 .
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (ex != null) {
			handlerException(response, ex);
		}
	}

	/**
	 * handlerException 处理异常 .
	 * 
	 * @param request
	 *            .
	 * @param ex
	 *            .
	 */
	private void handlerException(HttpServletResponse response, Exception ex) {
		Object result = null;
		if (ex instanceof UndeclaredThrowableException) { // spring mvc 抛出
			Throwable ta = ((UndeclaredThrowableException) ex).getUndeclaredThrowable();
			if (ta instanceof BusinessException) {
				result = ((BusinessException) ta).getErrorMsg();
			} else { // 这种情况何时有？ 而且返回到前台如何处理？
				result = Message.error(ta.getLocalizedMessage());
			}
		} else if (ex instanceof BusinessException) { // 业务代码直接抛出
			result = ((BusinessException) ex).getErrorMsg();
		} else if (ex instanceof ConstraintViolationException) { // hibernate校验抛出
			result = BusinessException.getErrorMsg((ConstraintViolationException) ex);
		} else {
			result = ((BusinessException) ex).getErrorMsg();
		}
		replyResult(response, result);
	}

	/**
	 * replyResult 将处理的信息返回到前端 .
	 * 
	 * @param response
	 *            .
	 * @param result
	 *            .
	 */
	private void replyResult(HttpServletResponse response, Object result) {
		if (response.isCommitted()) {
			return;
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(JsonUtil.toJson(result));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
