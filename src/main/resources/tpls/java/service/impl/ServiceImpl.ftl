package ${basePackage}.${module}.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.hellotoy.mvc.infr.core.service.BaseService;
import ${basePackage}.${module}.service.${Model}Service;
import org.hellotoy.mvc.infr.core.dao.Dao;
import ${basePackage}.${module}.model.${Model};
import ${basePackage}.${module}.dao.${Model}Dao;



/**
 * 类${Model}
 * 的实现描述：${ModelDescription}
 * 
 * @author ${author} ${createTime}
 */
@Service
public class ${Model}ServiceImpl extends BaseService<${Model},${ID}> implements ${Model}Service{
	@Autowired
	private ${Model}Dao ${HumpModel}Dao;
	@Override
	protected Dao<${Model}, ${ID}> getDao() {
		return ${HumpModel}Dao;
	}
}
