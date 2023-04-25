package com.insidetrackdata.test.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesUtil {

	@Value("${exportFile.sheet.size}")
	public Integer sizeSheetExportFile;

}
