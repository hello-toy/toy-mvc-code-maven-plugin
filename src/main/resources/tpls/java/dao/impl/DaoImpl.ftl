package ${basePackage}.${module}.dao.impl;

import ${basePackage}.${module}.dao.${Model}Dao;
import org.springframework.stereotype.Repository;
import ${basePackage}.${module}.entities.${Model}Entity;
import ${basePackage}.${module}.mappers.${Model}EntityMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.hellotoy.mvc.infr.core.dao.BaseDao;
import ${basePackage}.${module}.model.${Model};
/**
 * 类${Model}
 * 的实现描述：${ModelDescription}
 * 
 * @author ${author} ${createTime}
 */
@Repository
public class ${Model}DaoImpl extends BaseDao<${ID}, ${Model}Entity,${Model}> implements ${Model}Dao{
	@Autowired
	private ${Model}EntityMapper ${HumpModel}EntityMapper;
	@Override
	protected BaseMapper<${Model}Entity> getMapper() {
		return ${HumpModel}EntityMapper;
	}
}
