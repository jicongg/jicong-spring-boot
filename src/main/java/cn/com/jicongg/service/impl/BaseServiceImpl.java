package cn.com.jicongg.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.jicongg.entity.BaseEntity;
import cn.com.jicongg.mapper.BaseMapper;
import cn.com.jicongg.service.BaseService;
import cn.com.jicongg.util.IDGenerator;

/**
 * 实现base service. 事务实现为默认处理方式.
 * 
 * 2017年9月20日
 * 
 * @author jicong.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	/**
	 * BaseMapper .
	 * 
	 * @return IBasicDao<T> .
	 */
	protected abstract BaseMapper<T> getDao();

	/**
	 * 插入前进行后台数据库查询, 相关数据赋值准备. 默认情况下，会对不为空的id字段进行赋值.
	 * 
	 * @param record
	 * @throws Exception
	 */
	@Override
	public void prepareInsert(T record) throws Exception {
		// 赋值id
		if (record != null && (record instanceof BaseEntity)) {
			BeanMap map = BeanMap.create(record);
			Iterator<?> it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object o = map.get(key);
				try {
					Field field = record.getClass().getDeclaredField(key);
					Annotation annotation = field.getAnnotation(javax.persistence.Id.class);
					if (annotation != null) {// 给id属性赋值
						if (field.getType() == String.class && StringUtils.isEmpty((String) o)) {
							field.setAccessible(true);
							field.set(record, IDGenerator.getId());
							break;
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					continue; // 找不到field继续处理下一个
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 插入/修改前的业务逻辑验证.
	 * 
	 * @param record
	 */
	@Override
	public void verifyInsertOrUpdate(T record) throws Exception {
	}

	/**
	 * insert.
	 * 
	 * @param record
	 * @return 添加的记录数
	 */
	@Transactional
	public int insert(T record) throws Exception {
		this.prepareInsert(record);
		this.verifyInsertOrUpdate(record);
		return getDao().insert(record);
	}

	/**
	 * update.
	 * 
	 * @param record
	 * @return 更新的记录数
	 */
	@Transactional
	public int updateByPrimaryKey(T record) throws Exception {
		this.verifyInsertOrUpdate(record);
		return getDao().updateByPrimaryKey(record);
	}

	/**
	 * delete.
	 * 
	 * @param id
	 *            待删除主键id
	 * @return 删除的记录数
	 */
	@Transactional
	public int deleteByPrimaryKey(String pk) throws Exception {
		return getDao().deleteByPrimaryKey(pk);
	}

	/**
	 * 获取一个实体.
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<T> select(T record) throws Exception {
		return getDao().select(record);
	}

	
	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号.
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public T selectOne(T record) throws Exception {
		return getDao().selectOne(record);
	}

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号.
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<T> selectByRowBounds(T record, RowBounds rowBounds) throws Exception {
		return getDao().selectByRowBounds(record, rowBounds);
	}
}
