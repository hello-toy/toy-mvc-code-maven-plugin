package ${basePackage}.${module}.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

/**
 * 类${Model}
 * 的实现描述：${ModelDescription}
 * 
 * @author ${author} ${createTime}
 */
@Getter
@Setter
public class ${Model}{


	 <#list modelProreties as property>
	/** 
	 * ${property.comment}
	 **/
	private ${property.fieldType} ${property.fieldName};
	 </#list>
}
