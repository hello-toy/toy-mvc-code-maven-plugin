package org.hellotoy.maven.plugins.code.generation.metasource.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Property {
	/**
	 * 属性名称
	 */
	private String name;
	/**
	 * 属性标识
	 */
	private String lable;

	/**
	 * 属性类型 Int、Double
	 */
	private String type;

	/**
	 * 默认值
	 */
	private String defaultVal;

	/**
	 * 是否必填
	 */
	private boolean required;
	/**
	 * 正则表达式校验
	 */
	private List<ValidReg> regList;
	/**
	 * 是否启用
	 */
	private boolean disabled;

	/**
	 * 描述
	 */
	private String describe;

	/**
	* 主键
	 */
	private boolean priKey;


	private class ValidReg {
		/**
		 * 正则表达式
		 */
		private String pattern;
		/**
		 * 提示信息
		 */
		private String message;
	}
}
