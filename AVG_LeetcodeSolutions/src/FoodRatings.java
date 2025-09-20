import java.util.*;

public class FoodRatings {
    HashMap<String, PriorityQueue<Food>> cuisines;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        this.cuisines = new HashMap<>();
        for (int i = 0; i < foods.length; i++) {
            if (!this.cuisines.containsKey(cuisines[i])) {
                PriorityQueue<Food> p = new PriorityQueue<>();
                p.add(new Food(foods[i],ratings[i]));
                this.cuisines.put(cuisines[i],p);
            }else {
                PriorityQueue<Food> p = this.cuisines.get(cuisines[i]);
                p.add(new Food(foods[i],ratings[i]));
                this.cuisines.put(cuisines[i],p);
            }

        }
    }

    public void changeRating(String food, int newRating) {
        System.out.println(this.toString());
    }

    public String highestRated(String cuisine) {
        return Objects.requireNonNull(this.cuisines.get(cuisine).poll()).name;
    }

    @Override
    public String toString() {
        Set<Map.Entry<String,PriorityQueue<Food>>> s = cuisines.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,PriorityQueue<Food>> entry : s) {
            sb.append(entry.getKey());
            for (Food food : entry.getValue()) sb.append(food).append(" | ");
        }
        return sb.toString();
    }
}
