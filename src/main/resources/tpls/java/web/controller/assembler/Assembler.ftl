package ${basePackage}.${module}.web.assembler;

import org.springframework.stereotype.Component;
import ${basePackage}.${module}.dto.in.Upsert${Model}Command;
import ${basePackage}.${module}.dto.out.${Model}Info;
import ${basePackage}.${module}.model.${Model};
import org.hellotoy.mvc.infr.core.web.controller.assembler.DefaultAssembler;

/**
 * 类${Model}
 * 的实现描述：${ModelDescription}
 * 
 * @author ${author} ${createTime}
 */
@Component
public class ${Model}Assembler extends DefaultAssembler<Upsert${Model}Command, ${Model}Info, ${Model}>{
}
