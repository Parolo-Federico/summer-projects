import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] test = {1,2,2};
        String[] testS = {"iiii","leetcode","zbax"};
        for (int i = 0; i < test.length; i++) {
            System.out.println(i + ": " +s.getLucky(testS[i],test[i]));
        }
    }
    public static void print(int[] o){
        System.out.print("[ ");
         for (int j : o) {
            System.out.print(j + " ");
        }
        System.out.println("]");
    }

}