package cn.com.jicongg.message;

/**
 * 与客户端ios/android/html5等交互的消息. 
 *
 * 2017年9月27日
 * 
 * @author jicong.
 */
public class Message {

	private boolean success;
	private Object root;

	private Message(boolean success, Object root) {
		this.success = success;
		this.root = root;
	}

	/**
	 * success
	 * 
	 * @return Message 只含一个成功标志，root域为null。
	 */
	public static Message success() {
		return new Message(true, null);
	}

	/**
	 * info
	 * 
	 * @param root
	 * @return Message 含有一个成功标志，root域为入参。
	 */
	public static Message info(Object root) {
		return new Message(true, root);
	}

	/**
	 * error
	 * 
	 * @param root
	 * @return Message 含有个失败标志，root域为入参。
	 */
	public static Message error(Object root) {
		return new Message(false, root);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getRoot() {
		return root;
	}

	public void setRoot(Object root) {
		this.root = root;
	}

}
