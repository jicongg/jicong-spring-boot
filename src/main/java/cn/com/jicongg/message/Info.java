package cn.com.jicongg.message;

import java.util.List;

/**
 * Info 信息类.
 * 
 *
 * @version cmrm-web v1.0
 * @author Wang Shuo, 2013-11-25
 */
public class Info {

	private String fieldName;
	private String msgCode; // 国际化方案后，这个变量保存新的消息，而不是key
	private Display display;
	private List<Object> data; // 保存拼写消息的变量,最好为json
	private Object obj; // 保存非提示数据消息，用于传递其他业务数据

	/**
	 * 
	 *
	 * 2017年9月27日
	 * 
	 * @author jicong.
	 */
	public enum Display {
		NOOPE(0), // 无状态提示
		FLOAT(1), // 浮动弹出提示
		LOCATE(2), // 定位错误字段
		ALERT(3); // 弹出窗口提示
	
		private int display;

		private Display(int display) {
			this.display = display;
		}

		public int getDisplay() {
			return display;
		}

		public void setDisplay(int display) {
			this.display = display;
		}
	}


	/**
	 * InfoObject.
	 * 
	 * @param obj
	 *            数据对象信息，默认不做显示，由前台程序处理
	 */
	public Info(Object obj) {
		this.obj = obj;
		this.display = Display.NOOPE;
	}
	
	/**
     * InfoObject.
     * @param fieldName 
     * @param msgCode 
     * @param obj 
     */
    public Info(String fieldName, String msgCode, Object obj){
        this.fieldName = fieldName;
        this.msgCode = msgCode;
        this.obj = obj;
        this.display = Display.LOCATE;
    }
	
	/**
	 * InfoObject.
	 * 
	 * @param fieldName
	 *            字段名称
	 * @param msgCode
	 *            错误信息
	 * @param display
	 *            提示方式
	 * @param data
	 *            数据
	 * @param obj
	 *            response信息
	 */
	public Info(String fieldName, String msgCode, Display display, List<Object> data, Object obj) {
		this.fieldName = fieldName;
		this.msgCode = msgCode;
		this.display = display;
		this.data = data;
		this.obj = obj;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("MessageEntity [");
		sb.append("name:").append(this.getFieldName()).append(", code:").append(this.getMsgCode()).append(", data:")
				.append(this.getData());
		sb.append("]");
		return sb.toString();
	}
}
