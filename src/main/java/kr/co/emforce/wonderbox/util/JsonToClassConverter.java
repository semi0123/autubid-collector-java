package kr.co.emforce.wonderbox.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonToClassConverter {
	public static <T> List<T> convert(List<Map<String, Object>> jsonList, Class<T> classType) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException{
		Class<T> clz = (Class<T>) Class.forName(classType.getName());
		Method method = null;
		Field[] fields = null;
		fields = clz.getDeclaredFields();
		Map<String, Object> fTypeMap = new HashMap<String, Object>();
		for(Field field : fields){
			fTypeMap.put(field.getName(), field.getType().getName());
		}
		
		List<T> convertedList = new ArrayList<T>();
		
		Iterator<String> keyIter = null;
		T clazz = null;
		String key = null;
		
		for(Map<String, Object> json : jsonList){
			keyIter = json.keySet().iterator();
			clazz = clz.newInstance();
			while( keyIter.hasNext() ){
				key = keyIter.next();
				method = clz.getDeclaredMethod("set" + key.substring(0,1).toUpperCase() + key.substring(1), Class.forName(fTypeMap.get(key).toString()) );
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
		Map<String, Object> fTypeMap = new HashMap<String, Object>();
		for(Field field : fields){
			fTypeMap.put(field.getName(), field.getType().getName());
		}
		
		Iterator<String> keyIter = json.keySet().iterator();
		String key = null;
		
		T convertedObject = clz.newInstance();
		while( keyIter.hasNext() ){
			key = keyIter.next();
			method = clz.getDeclaredMethod("set" + key.substring(0,1).toUpperCase() + key.substring(1), Class.forName(fTypeMap.get(key).toString()) );
			method.invoke(convertedObject, json.get(key));
		}
		
		return convertedObject;
	}
	
	public static <T> Map<Object, T> convertToIdMap(List<Map<String, Object>> jsonList, String idColumnName, Class<T> classType) throws Exception{
		Map<Object, T> returnMap = new HashMap<Object, T>();
		List<T> convertedList = convert(jsonList, classType);
		Class<T> clz = (Class<T>) Class.forName(classType.getName());
		Field field = clz.getDeclaredField(idColumnName);
		field.setAccessible(true);
		for(T t : convertedList){
			returnMap.put(field.get(t), t);
		}
		return returnMap;
	}
}

