package org.hellotoy.maven.plugins.code.generation.metasource.rmdbs;

import lombok.Builder;
import lombok.Data;
import org.hellotoy.maven.plugins.code.generation.metasource.model.Property;
import org.hellotoy.maven.plugins.code.generation.util.Tools;

@Builder
@Data
public class DBTableColumn extends Property {
	private String name;
	private String type;
	private String length;

	private String numericPrecision;

	private String numericScale;

	private boolean nullable;


	public String getJavaType() {
		return Tools.formatDataType(type, numericPrecision, numericScale);
	}

	public String getJavaName() {
		return Tools.formatField(name);
	}


}
