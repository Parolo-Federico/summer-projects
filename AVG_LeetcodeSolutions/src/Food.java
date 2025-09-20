public class Food implements Comparable<Food> {
    String name;
    int rating;

    public Food(String name,int rating) {
        this.name = name;
        this.rating = rating;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Food)) return false;
        return this.name.equals(((Food) obj).name);
    }

    @Override
    public String toString() {
        return name + ", " + rating;
    }

    @Override
    public int compareTo(Food o) {
        int diff = this.rating - o.rating;
        if (diff != 0) return diff;
        return this.name.compareTo(o.name);
    }
}
