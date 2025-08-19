import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //System.out.println(">");
        //String str = scanner.nextLine();
        Solution s = new Solution();
        System.out.println(s.zeroFilledSubarray(new int[]{0,0, 1, 1, 0,0}));

    }
    public static void print(int[] o){
        System.out.print("[ ");
         for (int j : o) {
            System.out.print(j + " ");
        }
        System.out.println("]");
    }
    public static int countC(String s, char c){
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c){
                count++;
            }
        }
        return count;
    }

}