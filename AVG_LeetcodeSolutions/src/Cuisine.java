import java.util.Comparator;
import java.util.PriorityQueue;

public class Cuisine {
    String name;
    PriorityQueue<Food> plates;

    public Cuisine(String name){
        this.name = name;
        plates = new PriorityQueue<>(new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                int diff = o1.rating - o2.rating;
                if (diff != 0) return diff;
                return o1.name.compareTo(o2.name);
            }
        });
    }
}
