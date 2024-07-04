package com.ninza.hrm.api.genericUtility;

import java.io.FileInputStream;
import java.util.Properties;
/**
 * 
 * @author Subham
 *
 */
public class FileUtility {
	/**
	 * used to read the data from propertied file based on key
	 * @param key
	 * @return
	 * @throws Throwable
	 */
	public String getDataFromPropertiesFile(String key) throws Throwable {
		
		FileInputStream fis = new FileInputStream("./config-env-data/configEnvdata.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		String data = pObj.getProperty(key);
		
		return data;
	}

}
