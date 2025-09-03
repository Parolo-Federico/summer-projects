import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print(">");
//        String str = scanner.next();
//        System.out.println(strToArray(str));
        Solution s = new Solution();
        System.out.println(s.reverse(-2147483412));
        Integer.parseInt("-2147483648");
        Integer.parseInt("2147483647");
    }



    public static void print(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
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

    public static String strToArray(String badArray) {
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

    public static String strToCharArray(String bad){
        String good = "";
        for (int i = 0; i < bad.length(); i++) {
            switch (bad.charAt(i)) {
                case '[' :
                    good += '{';
                    break;
                case ']' :
                    good += '}';
                    break;
                case '\"' :
                    good += '\'';
                    break;
                default:
                    good += bad.charAt(i);
            }
        }
        return good;
    }

}