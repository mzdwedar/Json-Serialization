 
package jsonserial;
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonSerial {
   
    public static Map<String, Object> getFieldNamesAndValues(final Object obj, boolean publicOnly)
                                     throws IllegalArgumentException,IllegalAccessException
  {
    Class<? extends Object> c1 = obj.getClass();
    Map<String, Object> map = new HashMap<String, Object>();
    Field[] fields = c1.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      String name = fields[i].getName();
      if (publicOnly) {
        if(Modifier.isPublic(fields[i].getModifiers())) {
          Object value = fields[i].get(obj);
          map.put(name, value);
        }
      }
      else {
        fields[i].setAccessible(true);
        Object value = fields[i].get(obj);
        map.put(name, value);
      }
    }
    return map;
  }
    
    public static void toJson(Object object) throws FileNotFoundException, 
                                                IllegalArgumentException, IllegalAccessException, IOException{
        
        Map<String, Object> map = new HashMap<>();
        map = getFieldNamesAndValues(object, false);        
        String s = "{\n";
        FileWriter fileWriter = new FileWriter("object.json");
        PrintWriter fos = new PrintWriter(fileWriter);
        
        for (int i = 0; i < map.size(); i++) {
            
            s += "\"" +map.keySet().toArray()[i] + "\"" + ": " + "\"" + map.values().toArray()[i] + "\"" + ",\n";
        }
        s += "}";
        fos.write(s);
        fos.close();
    }

    

}
