package cn.com.jicongg.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * basr service. 提供默认service操作接口.
 * 
 * 2017年9月19日
 * 
 * @author jicong.
 */
public interface BaseService<T> {

	/**
	 * 插入前进行后台数据库查询, 相关数据赋值准备. 默认情况下，会对不为空的id字段进行赋值.
	 * 
	 * @param record
	 * @throws Exception
	 */
	void prepareInsert(T record) throws Exception;

	/**
	 * 插入/修改前的业务逻辑验证.
	 * 
	 * @param record
	 * @throws Exception
	 */
	void verifyInsertOrUpdate(T record) throws Exception;

	/**
	 * insert.
	 * 
	 * @param record
	 * @return 添加的记录数.
	 */
	int insert(T record) throws Exception;

	/**
	 * update.
	 * 
	 * @param record
	 * @return 更新的记录数.
	 */
	int updateByPrimaryKey(T record) throws Exception;

	/**
	 * delete.
	 * 
	 * @param pk
	 *            待删除主键id
	 * 
	 * @return 删除的记录数
	 */
	int deleteByPrimaryKey(String pk) throws Exception;

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号.
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	List<T> select(T record) throws Exception;

	/**
	 * 获取一个实体.
	 * @param record
	 * @return
	 * @throws Exception
	 */
	T selectOne(T record) throws Exception;

	/**
	 * 根据实体属性和RowBounds进行分页查询.
	 * 
	 * @param record
	 * @param rowBounds
	 * @return
	 */
	List<T> selectByRowBounds(T record, RowBounds rowBounds) throws Exception;
}
