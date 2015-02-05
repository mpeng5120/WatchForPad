package com.isea.list2excel;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
/**
 * ��ȡxml�ļ�
 * @author liuzh
 *
 */
public class XmlConfig {

	/**
	 * ��ȡxml����
	 * @param xmlPath
	 * @return
	 */
	public DlExcel getXmlConfig(String xmlPath){
		XStream xstream = new XStream();
		xstream.alias("excel", DlExcel.class);
		xstream.autodetectAnnotations(true);
		DlExcel dlExcel = (DlExcel)xstream.fromXML(new File(xmlPath));
		return dlExcel;
	}
	
	/**
	 * ��ȡ����ָ���ֶ�ֵ
	 * @param source
	 * @param fildnames
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String[] getObjValues(Object source,String... fildnames) throws Exception {
		Class[] classes = null;
		Object[] objects = null;
		String[] results = new String[fildnames.length];
		String value = null;
		Method method = null;
		Class type = null;
		Object obj = null;
        for(int i=0;i<fildnames.length;i++){
        	method = source.getClass().getDeclaredMethod("get"+firstLetterToStringUpperCase(fildnames[i]), classes);
        	type = method.getReturnType();
        	obj = method.invoke(source, objects);
        	if(obj==null||obj.equals("")){
        		value = "";
        	}
        	else if(type == Date.class){
        		value = DateUtilStrict.dateToString((Date)obj, DateUtilStrict.DATE_FORMAT);
        	}
        	else {
				value = String.valueOf(obj);
			}
        	results[i] = value;
        }
		return results;
	}
	
	/**
	 * ��һ����ĸ��д
	 * @param string
	 * @return
	 */
	public String firstLetterToStringUpperCase(String string){
		return string.substring(0,1).toUpperCase()+string.substring(1);
	}
	
	

}
