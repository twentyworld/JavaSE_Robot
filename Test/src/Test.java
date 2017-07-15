import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by w on 2017/7/14.
 */
public class Test {
    public static void main(String[] args){
        System.out.println("this----------------");
        List<String> list = new ArrayList<>();
        list.add("qwe");
        list.add("wer");
        list.remove(1);
        System.out.println(Arrays.asList(list.toArray()).toString());
    }
}
