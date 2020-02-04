import java.util.Arrays;
import java.util.List;

public class MultipleTest {
    public static void main(String[] args) {
        List<String> casos = Arrays.asList("30","32","34","36","38","40","42","44","46","48","50","60");

        for (String caso: casos){
            Thread thread = new Thread(){
                public void run(){
                    System.out.println("Thread Running");
                    Mapa m = new Mapa(caso);
                }
            };

            thread.start();
        }
    }
}
