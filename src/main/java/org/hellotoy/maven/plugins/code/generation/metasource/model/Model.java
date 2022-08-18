package org.hellotoy.maven.plugins.code.generation.metasource.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Model {
	/**
	 * 模型名称
	 */
	private String name;

	/**
	 * 所属业务
	 */
	private String businessRef;

	/**
	 * 是否禁用
	 */
	private boolean disabled;

	/**
	 * 描述
	 */
	private String describe;
	/**
	 * 主键唯一标识，1-N 个属性作为唯一标识
	 */
	private Property priKey;
	/**
	 * 属性集
	 */
	private List<Property> properties = new ArrayList<>();

	/**
	 * @param
	 */
	public void addColumn(Property property) {
		properties.add(property);
	}


	public String getHumpName(){

		return this.name.substring(0,1).toLowerCase()+this.getName().substring(1);
	}

	public Property getPriKey() {
		Property property = new Property();
		property.setPriKey(true);
		property.setType(Integer.class.toString());
		property.setName("id");
		return properties.stream().filter(Property::isPriKey).findFirst().orElse(property);
	}
}
