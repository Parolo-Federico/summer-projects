import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.divide(-2147483648, -1));
    }
    public static void print(Object[] o){
        for (int i = 0; i < o.length; i++) {
            System.out.println(o[i]);
        }
    }
}