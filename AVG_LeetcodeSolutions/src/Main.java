import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print(">");
//        String str = scanner.next();
//        System.out.println(formatArrayString(str));
        Solution s = new Solution();
        int[][] grid = {{2,0,2,0,2,0},
                        {0,1,2,2,2,0},
                        {2,0,1,0,2,0},
                        {2,0,0,0,1,0},
                        {0,0,2,0,2,0},
                        {2,0,2,0,2,0},
                        {2,0,2,0,2,0},
                        {2,0,0,0,2,0}};
        System.out.println(s.lenOfVDiagonal(grid));

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

    public static String formatArrayString(String badArray) {
        String goodArray = "";
        for (int i = 0; i < badArray.length(); i++) {
            switch (badArray.charAt(i)) {
                case '[' :
                    goodArray += '{';
                    break;
                case ']' :
                    goodArray += '}';
                    break;
                default:
                    goodArray += badArray.charAt(i);
            }
        }
        return goodArray;
    }

}