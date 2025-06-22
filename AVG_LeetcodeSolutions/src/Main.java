public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        s.myPow(2.0,10);
        String str = "abcdefghi";
        String[] s1 = s.divideString(str,3,'x');

    }
    public static void print(Object[] o){
        for (int i = 0; i < o.length; i++) {
            System.out.println(o[i]);
        }
    }
}