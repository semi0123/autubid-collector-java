package kr.co.emforce.wonderbox.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonToClassConverter {
	
	public static <T> List<T> convert(List<Map<String, Object>> jsonList, Class<T> classType) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException{
		Class<T> clz = (Class<T>) Class.forName(classType.getName());
		Method method = null;
		Field[] fields = null;
		fields = clz.getDeclaredFields();
		Map<String, String> fTypeMap = new HashMap<String, String>();
		for(Field field : fields){
			fTypeMap.put(field.getName(), field.getType().getName());
		}
		
		List<T> convertedList = new ArrayList<T>();
		T clazz = null;
		
		for(Map<String, Object> json : jsonList){
			Set<String> kies = json.keySet();
			Iterator<String> keyIter = kies.iterator();
			String key = null;
			while( keyIter.hasNext() ){
				key = keyIter.next();
				clazz = clz.newInstance();
				method = clz.getDeclaredMethod("set" + key.substring(0,1).toUpperCase() + key.substring(1), Class.forName(fTypeMap.get(key)) );
				method.invoke(clazz, json.get(key));
			}
			convertedList.add(clazz);
		}
		
		return convertedList;
	}
}

