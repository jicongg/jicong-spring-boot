package cn.com.jicongg.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json 工具.
 *
 * 2017年9月27日
 * @author jicong.
 */
public class JsonUtil {

	private static final ObjectMapper OM = new ObjectMapper();

	private JsonUtil() {
	}

	/**
	 * toJson.
	 * 
	 * @param obj
	 * @return String
	 */
	public static String toJson(Object obj) {
		String str = null;
		try {
			str = OM.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * toList.
	 * 
	 * @param str
	 * @param clazz
	 * @return List<?>
	 */
	public static List<?> toList(String str, Class<?> clazz) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		JavaType jy = OM.getTypeFactory().constructParametricType(List.class, clazz);
		try {
			return OM.readValue(str, jy);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * toMap.
	 * 
	 * @param str
	 * @return List<?>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		try {
			return (HashMap<String, Object>) OM.readValue(str, HashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
