package cn.com.jicongg.util;

import java.util.UUID;

/**
 * id生成器.
 * 
 * 2017年9月20日
 * @author jicong.
 */
public class IDGenerator {
	
    /**
	 * 默认构造器.
	 */
	public IDGenerator() {
	}
	
    /**
     * 生成唯一ID.
     * @return
     */
	public static String getId(){
        return UUID.randomUUID().toString().toUpperCase();
    }
	
}
