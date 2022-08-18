package ${basePackage}.${module}.entities;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.hellotoy.mvc.infr.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
/**
 * 类${Model}
 * 的实现描述：${ModelDescription}
 * 
 * @author ${author} ${createTime}
 */
@TableName(value="${TableName}")
@Getter
@Setter
public class ${Model}Entity extends BaseEntity{


	<#list modelProreties as property>
	/** 
	* ${property.comment}
	**/
	<#if property.fieldName == IDName>
	@TableId(value = "id",type = IdType.AUTO)
	</#if>
	private ${property.fieldType} ${property.fieldName};
	</#list>
}
