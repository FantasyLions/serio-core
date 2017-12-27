package com.serio.core.utils;

import java.lang.reflect.Field;
import org.apache.commons.lang.StringUtils;

public class Object2Json {
  public static String obj2json(Object o,String[] props){
    StringBuilder json = new StringBuilder();
    for(String s : props){
        Field field = getClassField(o.getClass(),s);
        if(field!=null){
          String type = field.getType().toString();
          if(type.equals("class java.lang.String")){
              String value = (String)getFieldValue(o,s); 
              if(StringUtils.isNotBlank(value))
               json.append("\""+s+"\"").append(":").append("\""+value+"\"").append(",");
          }else{
            //TODO:string之外的类型安需求处理
//              Object value = getFieldValue(o,s);
//              if(value!=null){
//                  json.append("\""+s+"\"").append(":").append("\""+String.valueOf(value)+"\"").append(",");
//              }   
          }
        }else{
          continue;
        }
    }
    if(json.length()>0)
        json.deleteCharAt(json.length()-1);
    return json.toString();
  }
  
  private static Field getClassField(Class clazz, String propName){
      Field[] fields = clazz.getDeclaredFields();
      for(Field f : fields){
          if(f.getName().equals(propName))
              return f;
      }
      Class superClz = clazz.getSuperclass();
      if(superClz!=Object.class)
          return getClassField(superClz,propName);
      return null;
  }
  
  private static Object getFieldValue(Object o, String propName){
      Field field = getClassField(o.getClass(),propName);
      if(field!=null)
        field.setAccessible(true);
        try {
            return field.get(o);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      return null;
  }
}
