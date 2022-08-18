package ${basePackage}.${module}.dto;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;

/**
 * 类${Model}
 * 的实现描述：${ModelDescription}
 * 
 * @author ${author} ${createTime}
 */
@Data
public class ${Model}DTO  {

	<#list modelProreties as property>
	<#if property.fieldName != IDName>
	/** 
	* ${property.comment} 
	*/
	@ApiModelProperty(value = "${property.comment}")
	<#if property.nullable??>
	<#else>
	@NotBlank(message="${property.comment},不能为空")
	</#if>
	private ${property.fieldType} ${property.fieldName};
	</#if>
	</#list>
}
