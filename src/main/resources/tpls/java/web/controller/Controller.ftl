package ${basePackage}.${module}.web.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${basePackage}.${module}.dto.in.Upsert${Model}Command;
import ${basePackage}.${module}.dto.out.${Model}Info;
import ${basePackage}.${module}.service.${Model}Service;
import org.hellotoy.mvc.infr.core.web.controller.BaseController;
import ${basePackage}.${module}.model.${Model};
import org.hellotoy.mvc.infr.core.service.IService;
import org.hellotoy.mvc.infr.core.web.controller.assembler.Assembler;
import ${basePackage}.${module}.web.assembler.${Model}Assembler;
import lombok.extern.slf4j.Slf4j;
/**
 * 类${Model}
 * 的实现描述：${ModelDescription}
 * 
 * @author ${author} ${createTime}
 */
@RestController
@RequestMapping("/${module}/${HumpModel}")
@Slf4j
public class ${Model}Controller extends BaseController<Upsert${Model}Command,${ID}, ${Model}Info,${Model}>{

	@Autowired
	private ${Model}Service ${HumpModel}Service;
	
	@Autowired
	private ${Model}Assembler assembler;
	
	
	@Override
	protected IService<${Model},${ID}> getService() {
		return ${HumpModel}Service;
	}

	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	public Assembler<Upsert${Model}Command, ${Model}Info,${Model}> getAssembler() {
		return assembler;
	}
}
