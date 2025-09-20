import javax.swing.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print(">");
//        String str = scanner.next();
//        System.out.println(strToArray(str));
        Solution s = new Solution();
        int[] nums = new int[]{8303,361,8303,361,437,361,8303,8303,8303,6859,19,19,361,70121,70121,70121,70121,70121,70121,70121,70121,70121,70121,70121,70121,70121,70121,70121,70121,1271,31,961,31,7,2009,7,2009,2009,49,7,7,8897,1519,31,1519,217};
        System.out.println(s.gcd(70121,70121));
        System.out.println(s.replaceNonCoprimes(nums));

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

    public static ListNode createListOfNodes(int[] vals) {
        ListNode node = new ListNode(vals[0]); // first val inside constructor call
        ListNode iterator = node;
        for (int i = 1; i < vals.length; i++) {
            iterator.next = new ListNode();
            iterator = iterator.next;
            iterator.val = vals[i];
        }
        return node;
    }

}