import java.util.HashMap;
import java.util.Map;


public class MapForEach {

    public static void main(String[] args) {
        try {
            new MapForEach().process();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    private void process() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("One", 1);
        map.put("Two", 2);
        
        for (String str, Integer i : map) {
            System.out.println("Entry: " + str + " = " + i);
        }
        for (final String str1 : map.keySet()) {
            System.out.println("Entry: " + str1);
        }
        for (int i = 0, isize = map.size(); i < isize; i++) {
            System.out.println("Entry: " + i);
        }
        for (int i = 0, isize; i < map.size(); i++) {
            isize = 0;
            System.out.println("Entry: " + i + " " + isize);
        }
        for (int i = 0, String; i < map.size(); i++) {
            String = 1;
            System.out.println("Entry: " + i + " " + String);
        }
        
        // these should fail to compile
//        for (int i = 0, String isize; i < map.size(); i++) {
//            isize = "L";
//            System.out.println("Entry: " + i + " " + isize);
//        }
    }

}
