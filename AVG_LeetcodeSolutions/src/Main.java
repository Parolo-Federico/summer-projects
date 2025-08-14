import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
            print(s.distributeCandies(90,4));
    }
    public static void print(int[] o){
        System.out.print("[ ");
         for (int j : o) {
            System.out.print(j + " ");
        }
        System.out.println("]");
    }

}