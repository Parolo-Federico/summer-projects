public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = {1,2,3,4,5,6,7};
        s.rotate(a,3);
        print(a);
    }
    public static void print(int[] o){
        for (int i = 0; i < o.length; i++) {
            System.out.print(o[i] + " ");
        }
    }

}