import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        for (int i = 1; i <= 10 ; i++) {
            System.out.println(s.countAndSay(i));
        }

        //System.out.println(s.binSearch(a,8,0,a.length-1));
        //print(s.searchRange(a,8));
    }
    public static void print(int[] o){
         for (int j : o) {
            System.out.print(j + " ");
        }
    }

}