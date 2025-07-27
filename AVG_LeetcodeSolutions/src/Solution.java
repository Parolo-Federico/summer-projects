import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;

class Solution {
    /*21. Merge Two Sorted Lists*/

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        }
        int[] vals = new int[100];
        int nVals = 0;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                vals[nVals++] = list1.val;
                list1 = list1.next;
            } else {
                vals[nVals++] = list2.val;
                list2 = list2.next;
            }
        }
        if (list1 == null) {
            while (list2 != null) {
                vals[nVals++] = list2.val;
                list2 = list2.next;
            }
        } else {
            while (list1 != null) {
                vals[nVals++] = list1.val;
                list1 = list1.next;
            }
        }
        ListNode merge = null;
        ListNode end = new ListNode(vals[0]);
        merge = end;
        for (int i = 1; i < nVals; i++) {
            end.next = new ListNode(vals[i]);
            end = end.next;
        }
        return merge;
    }

    /*26. Remove Duplicates from Sorted Array*/
    public int removeDuplicates(int[] nums) {
        int x = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[x++] = nums[i];
            }
        }
        return x;
    }

    /*27. Remove Element*/
    public int removeElement(int[] nums, int val) {
        int x = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[x++] = nums[i];
            }
        }
        return x;
    }

    /*28. Find the Index of the First Occurrence in a String*/
    public int strStr(String haystack, String needle) {
        int x = needle.length();
        if (haystack.equals(needle)) {
            return 0;
        }
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            if (haystack.substring(i, i + x).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    /*35. Search Insert Position*/
    //TODO fix runtime complexity to O(log n)
    public int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start != end) {
            if (nums[(end - start) / 2] == target) {
                return (end - start) / 2;
            } else if (nums[(end - start) / 2] > target) {
                end = (end - start) / 2 - 1;
            } else {
                start = (end - start) / 2 + 1;
            }
        }
        return -1;
    }

    /*58. Length of Last Word*/
    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        while (s.charAt(i) == ' ') {
            i--;
        }
        int len = 0;
        while (i >= 0 && s.charAt(i) != ' ') {
            i--;
            len++;
        }
        return len;
    }

    /*66. Plus One*/
    public int[] plusOne(int[] digits) {
        digits[digits.length-1] += 1;
        for (int i = digits.length-1; i >= 0 ; i--) {
            if(digits[i] == 10 && i > 0){
                digits[i-1] += 1;
                digits[i] = 0;
            }
        }
        if(digits[0] == 0 || digits[0] == 10){
            digits[0] = 0;
            int[] digits1 = new int[digits.length+1];
            digits1[0] = 1;
            System.arraycopy(digits, 0, digits1, 1, digits1.length - 1);
            digits = digits1;
        }
        return digits;
    }

    /*67. Add Binary*/
    public String addBinary(String a, String b) {
        String sum = "";
        if (a.equals("0")) {
            return b;
        }
        if (b.equals("0")) {
            return a;
        }
        int i = 0;
        int lenA = a.length() - 1;
        int lenB = b.length() - 1;
        boolean riporto = false;
        while (lenA - i >= 0 && lenB - i >= 0) {
            char cA = a.charAt(lenA - i);
            char cB = b.charAt(lenB - i);
            if (cA == '1' && cB == '1') {
                if (riporto) {
                    sum = '1' + sum;
                } else {
                    riporto = true;
                    sum = '0' + sum;
                }
            } else if (cA == '0' && cB == '0') {
                if (riporto) {
                    sum = '1' + sum;
                    riporto = false;
                } else {
                    sum = '0' + sum;
                }
            } else {
                if (riporto) {
                    sum = '0' + sum;

                } else {
                    sum = '1' + sum;
                }
            }
            i++;
        }
        if (lenA - i >= 0) {
            while (lenA - i >= 0) {
                char cA = a.charAt(lenA - i);
                if (riporto && cA == '1') {
                    sum = '0' + sum;
                } else if (riporto || cA == '1') {
                    riporto = false;
                    sum = '1' + sum;
                } else {
                    sum = '0' + sum;
                }

                i++;
            }
        } else {
            while (lenB - i >= 0) {
                char cB = b.charAt(lenB - i);
                if (riporto && cB == '1') {
                    sum = '0' + sum;
                } else if (riporto || cB == '1') {
                    riporto = false;
                    sum = '1' + sum;
                } else {
                    sum = '0' + sum;
                }

                i++;
            }
        }
        if (riporto) {
            sum = '1' + sum;
        }
        return sum;
    }

    /*69. Sqrt(x)*/
    //TODO revise
    public int mySqrt(int x) {
        // For special cases when x is 0 or 1, return x.
        if (x == 0 || x == 1)
            return x;

        // Initialize the search range for the square root.
        int start = 1;
        int end = x;
        int mid = -1;

        // Perform binary search to find the square root of x.
        while (start <= end) {
            // Calculate the middle point using "start + (end - start) / 2" to avoid integer overflow.
            mid = start + (end - start) / 2;

            // If the square of the middle value is greater than x, move the "end" to the left (mid - 1).
            if ((long) mid * mid > (long) x)
                end = mid - 1;
            else if (mid * mid == x)
                // If the square of the middle value is equal to x, we found the square root.
                return mid;
            else
                // If the square of the middle value is less than x, move the "start" to the right (mid + 1).
                start = mid + 1;
        }

        // The loop ends when "start" becomes greater than "end", and "end" is the integer value of the square root.
        // However, since we might have been using integer division in the calculations,
        // we round down the value of "end" to the nearest integer to get the correct square root.
        return Math.round(end);
    }

    /*50. Pow(x, n)*/
    public double myPow(double x, int n) {

        // x^n

        if (x == 0 || x == 1) {
            return x;
        }
        if(x == -1){
            if (n % 2 == 0) {
                return Math.abs(x);
            } else {
                return x;
            }
        }
        if (n == 0) {
            return 1;
        }
        if((n == Integer.MAX_VALUE || n == Integer.MIN_VALUE) && x != 1){
            return 0;
        }
        double pow = x;
        if (n > 0) {
            for (int i = 1; i < n; i++) {
                pow *= x;
            }
            if (n % 2 == 0) {
                return Math.abs(pow);
            } else {
                return pow;
            }
        } else {
            n = Math.abs(n);
            for (int i = 1; i < n; i++) {
                pow *= x;
            }
            return 1 / pow;
        }
    }

    /*83. Remove Duplicates from Sorted List*/
    public ListNode deleteDuplicates(ListNode head) {
        ListNode nHead;
        ListNode last;
        if (head == null) {
            return null;
        } else {
            nHead = new ListNode(head.val);
            last = nHead;
        }
        while (head != null) {
            if (head.val != last.val) {
                nHead = addNode(nHead, head.val);
                last = last.next;
            }
            head = head.next;
        }
        return nHead;
    }

    public ListNode addNode(ListNode node, int val) {
        ListNode n = node;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new ListNode(val);
        return n;
    }

    /*88. Merge Sorted Array*/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = 0;
        int j = 0;
        int k = 0;
        int[] nums1C = nums1.clone();
        while (i < m && j < n) {
            if (nums1C[i] <= nums2[j]) {
                nums1[k++] = nums1C[i++];
            } else {
                nums1[k++] = nums2[j++];
            }

        }
        if (i >= m) {
            while (j < n) {
                nums1[k++] = nums2[j++];
            }
        } else {
            while (i < m) {
                nums1[k++] = nums1C[i++];
            }
        }
    }

    /**/
    public boolean isPalindrome(String s) {
        return true;
    }

    /*2138. Divide a String Into Groups of Size k*/
    public String[] divideString(String s, int k, char fill) {
        int len = 0;
        if (s.length() % k == 0) {
            len = s.length() / k;
        } else {
            len = (s.length() / k) + 1;
        }
        String[] out = new String[len];
        int count=0;
        for (int i = 0; i < s.length(); i += k) {
            if(i+k >= s.length()){
                out[count++] = s.substring(i);
            }else{
                out[count++] = s.substring(i,i+k);
            }

        }
        while (out[len-1].length() != k){
            out[len-1] = out[len-1] + fill;
        }
        return out;
    }

    /*2016. Maximum Difference Between Increasing Elements*/
    public int maximumDifference(int[] nums) {
        int n = nums.length;
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n ; j++) {
                if(nums[j] - nums[i] > max){
                    max = nums[j]-nums[i];
                }
            }
        }
        if(max==0){
            return -1;
        }
        return max;
    }

    /*2566. Maximum Difference by Remapping a Digit*/
    public int minMaxDifference(int num) {
        String sMax = Integer.toString(num);
        String sMin = Integer.toString(num);
        char digit = ' ';
        for (int i = 0; i < sMax.length(); i++) {
            if(sMax.charAt(i) != '9'){
                digit = sMax.charAt(i);
                break;
            }
        }
        if(digit == ' '){
            return num;
        }
        char digitMin = sMin.charAt(0);
        for (int i = 0; i < sMax.length(); i++) {
            if(sMax.charAt(i) == digit){
                sMax = sMax.substring(0,i) + '9' + sMax.substring(i+1);
            }
            if(sMin.charAt(i) == digitMin){
                sMin = sMin.substring(0,i) + '0' + sMin.substring(i+1);
            }
        }
        return Integer.parseInt(sMax) - Integer.parseInt(sMin);
    }

    /*2894. Divisible and Non-divisible Sums Difference*/
    public int differenceOfSums(int n, int m) {
        int num1=0;
        int num2=0;
        for (int i = 0; i <= n; i++) {
            if(i % m == 0){
                num2+=i;
            }else{
                num1+=i;
            }
        }
        return num1-num2;
    }

    /*2942. Find Words Containing Character*/
    public List<Integer> findWordsContaining(String[] words, char x) {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            if(words[i].contains("" + x)){
                l.add(i);
            }
        }
        return l;
    }
    /*136. Single Number*/
    public int singleNumber(int[] nums) {
        if(nums.length == 1){
            return nums[0];
        }
        boolean b;
        for (int i = 0; i < nums.length ; i++) {
            b= false;
            for (int j = 0; j < nums.length ; j++) {
                if(nums[i] == nums[j] && i != j){
                    b = true;
                    break;
                }
            }
            if(!b){
                return nums[i];
            }
        }
        return 0;
    }
    /*169. Majority Element*/
    public int majorityElement(int[] nums) {
        ArrayList<Integer> a = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if(!a.contains(nums[i])){
                a.add(nums[i]);
                int c = 0;
                for (int j = 0; j < n; j++) {
                    if(nums[i] == nums[j]){
                        c++;
                    }
                }
                if (c > n /2) {
                    return nums[i];
                }
            }

        }
        return 0;
    }
    /*171. Excel Sheet Column Number*/
    public int titleToNumber(String columnTitle) {
        Dictionary<String, Integer> d = new Hashtable<>();
        int col = 0;
        int len = columnTitle.length()-1;
        for (int i = 65; i < 91; i++) {
            d.put("" + (char)i, i-64);
            int a = d.get("" + (char)i);
        }
        for (int i = 0; i < columnTitle.length(); i++) {
            col += d.get("" + columnTitle.charAt(len-i)) * (int)Math.pow(26,i);
        }
        return col;
    }

    /*172. Factorial Trailing Zeroes*/
    public int trailingZeroes(int n) {
        int count = 0;
        int i = 5;
        while( n / i >= 1){
            count += n/i;
            i *= 5;
        }
        return count;
    }


    /*187. Repeated DNA Sequences*/
    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> seen = new HashSet<>();
        Set<String> repeated = new HashSet<>();
        int len = s.length();

        for (int i = 0; i+10 <= len ; i++) {
            if(!seen.add(s.substring(i,i+10))){
                seen.add(s.substring(i,i+10));
            }
            int c = 0;
            for (int j = i; j+10 <= len; j++) {
                if(seen.contains(s.substring(j,j+10))){
                    if(!repeated.add(s.substring(j,j+10))){
                        repeated.add(s.substring(j,j+10));
                    }
                }
            }
        }
        return new ArrayList<>(repeated);
    }
    /*188. Best Time to Buy and Sell Stock IV*/
    public int maxProfit1(int k, int[] prices) {

        int profit = 0;
        for (int i = 0; i < prices.length-1 && k>0; i++) {
            int[] ind = new int[2];
            for (int j = 0; j < prices.length-1; j++) {
                if(prices[ind[0]] - prices [ind[1]] < prices[j+1] - prices[j] && prices[j] != -1 ){
                    ind[0] = j+1;
                    ind[1] = j;
                }
            }
            if(prices[ind[0]] - prices[ind[1]] == 0){
                return profit;
            }
            profit += prices[ind[0]] - prices[ind[1]];
            prices[ind[0]] = -1;
        }
        return profit;
    }
    public int maxProfit(int k, int[] prices) {
        int profit = 0;
        for (int i = prices.length-1; i > 0 && k>0; i--) {
            int[] ind = new int[2];
            for (int j = prices.length-1; j > 0 ; j--) {
                if(prices[ind[0]] - prices[ind[1]] < prices[j] - prices[j-1] && prices[j-1] != -1){
                    ind[0] = j;
                    ind[1] = j-1;
                }
            }
            if(prices[ind[0]] - prices[ind[1]] == 0){
                return profit;
            }
            profit += prices[ind[0]] - prices[ind[1]];
            prices[ind[0]] = -1;
            k--;
        }
        return profit;
    }
    /*8. String to Integer (atoi)*/
    public int myAtoi(String s) {
        int i = 0;
        long sum = 0;
        boolean neg =false;
        if(s.length() == 0){
            return 0;
        }
        while (i < s.length() && s.charAt(i) == ' ') {
            i++;
        }
        if (i < s.length() && s.charAt(i) == '-') {
            neg = true;
            i++;
        }else if( i < s.length() && s.charAt(i) == '+'){
            i++;
        }
        while (i < s.length() && s.charAt(i) == '0') {
            i++;
        }
        int end = i;
        while (end < s.length() && ("" + s.charAt(end)).matches("\\d")) {
            end++;
        }
        if(end-i > 10){
            return neg ? (int) -Math.pow(2,31) : (int) Math.pow(2,31);
        }
        if(end - i == 0){
            return 0;
        }
        sum = (neg) ? -Long.parseLong(s.substring(i, end)) : Long.parseLong(s.substring(i, end));
        System.out.println(sum);
        if (sum > Math.pow(2, 31)-1) {
            return (int) Math.pow(2, 31);
        } else if (sum < (int) -Math.pow(2, 31)) {
            return (int) -Math.pow(2, 31);
        }

        return (int) sum;

    }
    /*11. Container With Most Water*/
    public int maxArea(int[] height) {
        if(height.length == 2){
            return Math.min(height[0],height[1]);
        }
        int max = 0;
        int l = 0;
        int r = height.length-1;
        while(l < r){
            if(max < Math.min(height[l],height[r]) * (r-l)){
                max = Math.min(height[l],height[r]) * (r-l);
            }
            if(Math.max(height[l],height[r]) == height[r]){
                l++;
            }else{
                r--;
            }
        }
        return max;
    }
    /*17. Letter Combinations of a Phone Number*///TODO fix
    public List<String> letterCombinations(String digits) {
        HashMap<Character,String[]> h = new HashMap<>();
        List<String> l = new ArrayList<>();
        h.put('2', new String[]{"a", "b", "c"});
        h.put('3', new String[]{"d", "e", "f"});
        h.put('4', new String[]{"g", "h", "i"});
        h.put('5', new String[]{"j", "k", "l"});
        h.put('6', new String[]{"m", "n", "o"});
        h.put('7', new String[]{"p", "q", "r","s"});
        h.put('8', new String[]{"t", "u", "v"});
        h.put('9', new String[]{"w", "x", "y","z"});
        for (int i = 0; i < digits.length(); i++) {
            dotP(h.get(digits.charAt(i)),h.get(digits.charAt(i+1)),l);
        }
        return l;
    }
    public void dotP(String[] a,String[] a1, List<String> list){
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a1.length; j++) {
                list.add(a[i] + a1[j]);
            }
        }
    }
    /*22. Generate Parentheses*/
    /*parentesi da usare ()
    crea prima la struttura con tutte le parentesi inglobate
         0 1 2 3 4 5
    es 3 ( ( ( ) ) )
    poi sposta la parentesi centrale di uno a destra
    0 1 4 2 3 5
    ( ( ) ( ) )
    ripeti:
    0 1 4 5 2 3
    ( ( ) ) ( )
    0 5 1 4 2 3
    ( ) ( ) ( )

    */
    public List<String> generateParenthesis(int n) {
        List<String> l = new ArrayList<>();
        if(n == 0){
            return l;
        }
        return null;
    }

    /*29. Divide Two Integers*/
    public int divide(int dividend, int divisor) {
        int q = 0;
        boolean neg = false;
        if(dividend < 0 ^ divisor < 0){
            neg = true;
        }
        System.out.println(neg);
        if(dividend == Integer.MAX_VALUE && divisor == -1){
            return Integer.MIN_VALUE+1;
        }else if(dividend == Integer.MIN_VALUE && divisor == -1){
            return Integer.MAX_VALUE;
        }
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        while(dividend - divisor >= 0){
            dividend -= divisor;
            q++;
        }
        return neg ? -q : q;
    }
    /*62. Unique Paths*/
    public int uniquePaths(int m, int n) {
        //(m+n-2)!/((n-1)!(m-1))!
        if(m == 1 || n == 1){
            return 1;
        }
        return (fact(m+n-2).divide(fact(n-1).multiply(fact(m-1)))).intValue();
    }
    public BigInteger fact(int n){
        if(n == 0 || n == 1){
            return new BigInteger("" + n);
        }
        return new BigInteger("" + n).multiply(fact(n-1));
    }
    /*70. Climbing Stairs*/
    /*
    possibilità: 1 or 2
    non distinte per cui 1 + 2 e 2 + 1 sono coppie diverse
    inizio da tutti 1 prima combinazione
    poi un uno lo faccio diventare un 2
    fattiriale della singhezza della sequenza
    */
    public int climbStairs(int n) {
        return nThFib(n+1);
    }
    public int fib(int n, HashMap<Integer,Integer> map){
        if(map.get(n) != null){
            return map.get(n);
        }
        int f = fib(n-1,map) + fib(n-2,map);
        map.put(n,f);
        return f;
    }
    public int nThFib(int n){
        if(n < 2){
            return n;
        }
        HashMap<Integer,Integer> m = new HashMap<>();
        m.put(0,0);
        m.put(1,1);
        return fib(n,m);
    }
    /*89. Gray Code*/
    public List<Integer> grayCode(int n) {
        List<Integer> rangeList = IntStream.range(0,(1 << n))
                .boxed()
                .collect(Collectors.toList());
        int end = rangeList.size()-1;
        for (int i = 1; i < n; i++) {
            int start = (1 << i);
            while(start < end){
                flip(rangeList,start,start+(1 << i)-1);
                start+=1 << i+1;

            }
            System.out.println(rangeList);
        }
        return rangeList;
    }
    public void flip(List<Integer> list, int start, int end){
        for (int i = 0; i <= (end-start)/2; i++) {
            Integer t = list.get(start+i);
            list.set(start+i,list.get(end-i));
            list.set(end-i,t);
        }
    }
    /*public void flip(int[] a, int start, int end){
        for (int i = 0; i < (end-start)/2; i++) {
            int temp = a[start+i];
            a[start+i] = a[end-i];
            a[end-i] = temp;
        }
    }*/

    /*150. Evaluate Reverse Polish Notation*/
    // first accepted not best
    public int evalRPN(String[] tokens) {
        Stack<Integer> s = new Stack<>();
        int a; int b;
        for (int i = 0; i < tokens.length; i++) {
            switch (tokens[i]){
                case "+":
                    s.push(s.pop() + s.pop());
                    break;
                case "-":
                    a = s.pop();
                    b = s.pop();
                    s.push(b-a);
                    break;
                case "*":
                    s.push(s.pop() * s.pop());
                    break;
                case "/":
                    a = s.pop();
                    b = s.pop();
                    s.push(b/a);
                    break;
                default:
                    s.push(Integer.parseInt(tokens[i]));
            }
        }
        return s.pop();
    }
    // more efficent
    public int evalRPN1(String[] tokens) {
        List<Integer> s = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            switch (tokens[i]){
                case "+":
                    int a = pop(s);
                    int b = pop(s);
                    push(s,a+b);
                    break;
                case "-":
                    a = pop(s);
                    b = pop(s);
                    push(s,b-a);
                    break;
                case "*":
                    a = pop(s);
                    b = pop(s);
                    push(s,a*b);
                    break;
                case "/":
                    a = pop(s);
                    b = pop(s);
                    push(s,b/a);
                    break;
                default:
                    push(s,Integer.parseInt(tokens[i]));
            }
        }
        return pop(s);
    }
    public void push(List<Integer> l, int a){
        l.add(a);
    }
    public int pop(List<Integer> l){
        return l.remove(l.size()-1);
    }
    /*166. Fraction to Recurring Decimal*/
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0){
            return "0";
        }
        if(numerator % denominator == 0){
            return "" + numerator/denominator;
        }
        int n = numerator;

        while(n/5 > 0){
            n /= 5;
        }
        while(n/2 > 0){
            n /= 2;
        }
        if(n == 0){
            return "" +(double)numerator/denominator;
        }
        List<Integer> remainers = new ArrayList<>();
        n = numerator/denominator;
        int resto = numerator % denominator;
        String period = "";
        int q = (resto * 10) / denominator;
        remainers.add(q);
        while (!remainers.contains(q)){
            q = (resto * 10) / denominator;
            resto = resto*10 % denominator;
            remainers.add(q);
        }
        for (int i = 0; i < remainers.size(); i++) {
            period += remainers.get(i);
        }
        return "" + n + ".(" + period + ")" ;
    }
    /*168. Excel Sheet Column Title*/
    public String convertToTitle(int columnNumber) {
        String s = "";
        while(columnNumber / 27 >= 1){
            int rem = columnNumber % 26 == 0 ? 26 : columnNumber % 26;
             s = (char)(rem + 64) + s;
             columnNumber /= 26;
             if(rem == 26){
                 columnNumber--;
             }
        }
        return ((char)(columnNumber + 64) + s);
    }
    // slightly better
    public String convertToTitle1(int columnNumber) {
        String s = "";
        while(columnNumber > 0){
            columnNumber--;
            s = (char)((columnNumber % 26) + 'A') + s;
            columnNumber /= 26;
        }
        return s;
    }
    /*189. Rotate Array*/
    public void rotate(int[] nums, int k) {
        if(k % nums.length == 0){
            return;
        }
        k = (k % nums.length)+1;
        int t = nums[k];
        int l = nums.length;
        int i = k;
        while(i % l != 0){
            nums[(i % l)] = nums[((i+k) % l)];
            i+= k;
        }
        nums[0] = t;
    }
    public void rotate1(int[] nums, int k) {
        if(k % nums.length == 0){
            return;
        }
        for (int i = 0; i < k; i++) {
            oneRight(nums);
        }
    }
    // funziona ma non e veloce
    public void oneRight(int[] nums){
        int count = 0;
        int temp = nums[nums.length-1];
        for (int i = nums.length-1; i > 0 ; i--) {
            nums[i] = nums[i-1];
        }
        nums[0] = temp;
    }

    /*3487. Maximum Unique Subarray Sum After Deletion*/
    public int maxSum(int[] nums) {
        if(nums.length == 1){
            return nums[0];
        }
        int sum = 0;int max = nums[0];
        List<Integer> pos = new ArrayList<>();
        pos.add(0);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0 && !pos.contains(nums[i])){
                pos.add(nums[i]);
            }
            max = max < nums[i] ? nums[i] : max;
        }
        if(pos.size() == 0){
            return max;
        }else{
            max = 0;
            for (int i = 0; i < pos.size(); i++) {
                max+= pos.get(i);
            }
        }
        return max;
    }
    /*191. Number of 1 Bits*/
    public int hammingWeight(int n) {
        return Integer.bitCount(n);
    }

    /*202. Happy Number*/
    public boolean isHappy(int n) {
        for (int i = 0; i < 10; i++) {
            if(n == 1){
                return true;
            }
            n = powerOfDigit(n);
        }
        return false;
    }

    public int powerOfDigit(int n){
        String str = "" + n;
        n=0;
        for (int i = 0; i < str.length(); i++) {
            n += Math.pow(str.charAt(i) - 48,2);
        }
        return n;
    }

    /*205. Isomorphic Strings*/
    public boolean isIsomorphic(String s, String t) {
        if(s.equals(t)){
            return true;
        }
        HashMap<Character,Character> hs = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if(hs.containsKey(s.charAt(i)) && hs.get(s.charAt(i)) != t.charAt(i) || hs.containsValue(t.charAt(i)) && hs.get){
                return false;
            }
            hs.put(s.charAt(i),t.charAt(i));
        }
        return true;
    }
}