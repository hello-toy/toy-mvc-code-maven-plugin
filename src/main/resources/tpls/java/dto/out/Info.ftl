package ${basePackage}.${module}.dto.out;
import ${basePackage}.${module}.dto.${Model}DTO;
import lombok.Data;

/**
 * 类${Model}
 * 的实现描述：${ModelDescription}
 * 
 * @author ${author} ${createTime}
 */
@Data
public class ${Model}Info extends ${Model}DTO {
	<#list modelProreties as property>
	<#if property.fieldName == IDName>
	/** 
	* ${property.comment} 
	*/
	private ${property.fieldType} ${property.fieldName};
	</#if>
	</#list>
}
