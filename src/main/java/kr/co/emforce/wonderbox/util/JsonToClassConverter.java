package kr.co.emforce.wonderbox.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
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
		
		Iterator<String> keyIter = null;
		T clazz = null;
		String key = null;
		
		for(Map<String, Object> json : jsonList){
			keyIter = json.keySet().iterator();
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
	
	public static <T> T convert(Map<String, Object> json, Class<T> classType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		Class<T> clz = (Class<T>) Class.forName(classType.getName());
		Method method = null;
		Field[] fields = null;
		fields = clz.getDeclaredFields();
		Map<String, String> fTypeMap = new HashMap<String, String>();
		for(Field field : fields){
			fTypeMap.put(field.getName(), field.getType().getName());
		}
		
		T convertedObject = null;
		
		Iterator<String> keyIter = json.keySet().iterator();
		String key = null;
		
		while( keyIter.hasNext() ){
			key = keyIter.next();
			convertedObject = clz.newInstance();
			method = clz.getDeclaredMethod("set" + key.substring(0,1).toUpperCase() + key.substring(1), Class.forName(fTypeMap.get(key)) );
			method.invoke(convertedObject, json.get(key));
		}
		
		return convertedObject;
	}
}

