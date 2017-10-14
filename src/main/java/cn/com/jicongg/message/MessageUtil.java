package cn.com.jicongg.message;

import java.util.ArrayList;
import java.util.List;

import cn.com.jicongg.message.Info.Display;

/**
 * 消息工具类.
 *
 * 2017年9月27日
 * 
 * @author jicong.
 */
public class MessageUtil {

	private MessageUtil() {
	};

	/**
	 * 提示信息.
	 * 
	 * @param obj
	 *            非提示数据消息.返回的entity对象
	 * @return Message
	 */
	public static Message info(String msgCode, Object obj) {
		return Message.info(new Info(null, null, Display.NOOPE, null, obj));
	}

	/**
	 * 提示信息.
	 * 
	 * @param obj
	 *            非提示数据消息.返回的entity对象
	 * @return Message
	 */
	public static Message error(String msgCode, Object obj) {
		return Message.error(new Info(null, msgCode, Display.NOOPE, null, obj));
	}

	/********************** web message ***********************/

	/**
	 * 单个字段错误一起抛出前台定位到具体录入框.
	 * 
	 * @param fieldName
	 *            错误字段名称
	 * @param msgCode
	 *            错误码
	 * @return Message
	 */
	public static Message error(String fieldName, String msgCode) {
		msgCode = getMsgCode(msgCode, null);

		Info info = new Info(fieldName, msgCode, Display.LOCATE, null, null);
		List<Info> listObj = new ArrayList<Info>();
		listObj.add(info);
		return Message.error(listObj);
	}

	/**
	 * 单个字段错误一起抛出前台定位到具体录入框.
	 * 
	 * @param fieldName
	 *            错误字段名称
	 * @param msgCode
	 *            错误码
	 * @param data
	 *            数据参数列表
	 * @return Message
	 */
	public static Message error(String fieldName, String msgCode, List<Object> data) {
		// 类型转换
		msgCode = getMsgCode(msgCode, data.toArray());
		Info info = new Info(fieldName, msgCode, Display.LOCATE, data, null);
		List<Info> listObj = new ArrayList<Info>();
		listObj.add(info);
		return Message.error(listObj);
	}

	/**
	 * ALERT方式错误提示.
	 * 
	 * @param msgCode
	 *            错误码
	 * @return Message
	 */
	public static Message error(String msgCode) {
		msgCode = getMsgCode(msgCode, null);
		Info info = new Info("", msgCode, Display.ALERT, null, null);
		List<Info> listObj = new ArrayList<Info>();
		listObj.add(info);
		return Message.error(listObj);
	}

	/**
	 * ALERT方式错误提示.
	 * 
	 * @param msgCode
	 *            错误码
	 * @param data
	 *            数据参数列表
	 * @param obj
	 *            非提示数据消息
	 * @return Message
	 */
	public static Message error(String msgCode, List<Object> data, Object obj) {
		msgCode = getMsgCode(msgCode, data.toArray());
		Info info = new Info("", msgCode, Display.ALERT, data, obj);
		List<Info> listObj = new ArrayList<Info>();
		listObj.add(info);
		return Message.error(listObj);
	}

	/**
	 * 供前台AJAX调用时，返回的成功MESSAGE对象.
	 * 
	 * @param obj
	 *            需要传给前台的对象信息
	 * @return Message
	 */
	public static Message ajaxSuccess(Object obj) {
		Info info = new Info(obj);
		return Message.info(info);
	}

	/**
	 * 供前台AJAX调用时，返回Message的success为false的MESSAGE对象.
	 * InfoObject中obj域为参数对象，display域为NOOPE操作
	 * 
	 * @param obj
	 *            需要传给前台的对象信息
	 * @return Message
	 */
	public static Message ajaxError(Object obj) {
		Info info = new Info(obj);
		return Message.error(info);
	}

	/**
	 * getMsgCode: 检查是否可以获取到资源文件中的内容，如果获取不到，直接返回key. 前台需要自己进行处理.
	 * 
	 * @param code
	 *            .
	 * @return String .
	 */
	public static String getMsgCode(String code) {
		String msg = getText(code, null);
		if (msg != null && msg.length() != 0) {
			code = msg;
		}
		return code;
	}

	/**
	 * . getMsgCode: 检查是否可以获取到资源文件中的内容，如果获取不到，直接返回key. 前台需要自己进行处理。
	 * 
	 * @param code
	 *            .
	 * @param params
	 *            .
	 * @return String .
	 */
	public static String getMsgCode(String code, Object[] params) {
		String msg = getText(code, params);
		if (msg != null && msg.length() != 0) {
			code = msg;
		}
		return code;
	}

	/**
	 * . getText:code.
	 * 
	 * @param code
	 *            String
	 * @param params
	 *            Object[]
	 * @return String
	 */
	private static String getText(final String code, final Object[] params) {
		return code.trim();
	}

}
