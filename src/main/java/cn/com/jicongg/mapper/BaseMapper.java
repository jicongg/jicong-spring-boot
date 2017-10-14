package cn.com.jicongg.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 不需要被mybatis扫描,单独package.
 * 2017年9月20日
 * @author jicong.
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>{
	
//	

}
