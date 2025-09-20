import com.sun.source.tree.Tree;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Solution {
    public static void print(String[] o){
        for (String j : o) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
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
    public int uniquePaths(int m, int n, HashMap<String, Integer> hashMap) {
        String key = m + "," + n;
        if (hashMap.containsKey(key)) return hashMap.get(key);
        if (m == 0 || n == 0) return 0;
        if (m == 1 || n == 1) return 1;

        int paths = uniquePaths(m-1,n,hashMap) + uniquePaths(m,n-1,hashMap);
        hashMap.put(key,paths);
        return paths;
    }

    public int uniquePaths(int m,int n){
        return uniquePaths(m,n,new HashMap<>());
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
    public int fibonacci(int n, HashMap<Integer,Integer> map){
        if(map.get(n) != null){
            return map.get(n);
        }
        int f = fibonacci(n-1,map) + fibonacci(n-2,map);
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
        return fibonacci(n,m);
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
        if(k % nums.length == 0) return;
        k = k % nums.length;
        int[] arr = new int[nums.length];
        int index = 0;
        for (int i = nums.length-k; i < nums.length; i++) {
            arr[index++] = nums[i];
            System.out.println(nums[i]);
        }
        for (int i = 0; i < k; i++) {
            arr[index++] = nums[i];
            System.out.println(nums[i]);
        }
        nums = arr;
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
//            if(hs.containsKey(s.charAt(i)) && hs.get(s.charAt(i)) != t.charAt(i) || hs.containsValue(t.charAt(i)) && hs.get()){
//                return false;
//            }
            hs.put(s.charAt(i),t.charAt(i));
        }
        return true;
    }
    /*34. Find First and Last Position of Element in Sorted Array*/
    public int[] searchRange(int[] nums, int target) {
        int l = nums.length;
        if (l == 0){
            return new int[] {-1,-1};
        }else if(l == 1 && nums[0] == target){
            return new int[] {0,0};
        }else if(nums[0] == nums[l-1] && nums[0] == target){
            return new int[] {0,l-1};
        }
        int pos = binSearch(nums,target,0,l-1);
        if(pos == -1){
            return new int[] {-1,-1};
        }
        int start = pos;
        int end = pos;
        while (start >=0 && nums[start] == target){
            start--;
        }
        while (end < l && nums[end] == target){
            end++;
        }

        return new int[] {++start,--end};
    }

    public int binSearch(int[] a, int key,int start,int end){
        int mid = (end+start) / 2;
        if (start > end){
            return -1;
        }
        if(a[mid] == key){
            return mid;
        }else if(a[mid] < key){
            return binSearch(a,key,mid+1,end);
        }else{
            return binSearch(a,key,start,mid-1);
        }
    }
    /*38. Count and Say*/
    public String countAndSay(int n) {
        if (n == 1){
            return "1";
        }
        String str = "1";
        for (int i = 0; i < n-1; i++) {
            String res = "";
            for (int j = 0; j < str.length(); j++) {
                 char c = str.charAt(j);
                 int count = 1;
                 while(j + count < str.length() &&  c == str.charAt(j + count)){
                     count++;
                 }
                 res +=  "" + count + c;
                 j+= count-1;
            }
            str = res;
        }
        return str;
    }

    public String countAndSay2(int n){
        if (n == 1){
            return "1";
        }
        String str = "1";
        int i = 0;
        while(i < str.length()){
            String res = "";
            char c = str.charAt(i);
            int appearence = 1;
            while (i + appearence < str.length() && c == str.charAt(i + appearence)){
                appearence++;
            }
        }
    return null;
    }

    /*3477. Fruits Into Baskets II*/
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        int n = fruits.length;
        int unplaced = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(fruits[i] <= baskets[j]){
                    baskets[j] = 0;
                    break;
                }
                if(j == n-1){
                    unplaced++;
                }

            }
        }
        return unplaced;
    }

    /*231. Power of Two*/
    public boolean isPowerOfTwo(int n) {
        if (n == 1){
            return true;
        }else if(n <= 0){
            return false;
        }
        while (n % 2 == 0 || n != 0){
            n /= 2;
        }
        if(n == 0){
            return true;
        }
        return false;
    }

    /*3136. Valid Word*/
    public boolean isValid(String word) {
        if (word.length() < 3 || word.matches(".*[@#$].*")){
            return false;
        }
        return word.matches("[AaEeIiOoUu0-9]*") || word.matches("[^AaEeIiOoUu]*") ? false : true;
    }

    /*1859. Sorting the Sentence*/
    public String sortSentence(String s) {
        String[] words = s.split(" ");
        s = "";
        HashMap<Integer,String> hM = new HashMap<>();
        int j = words.length;
        for (int i = 0; i < j; i++) {
            int l = words[i].length();
            hM.put(words[i].charAt(words[i].length()-1)-48,words[i].substring(0,l-1));
        }
        for (int i = 1; i <= j; i++) {
            s += hM.get(i);
            s = i == j ? s : s + " ";
        }
        return s;
    }

    /*118. Pascal's Triangle*/
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> l = new ArrayList<>();
        List<Integer> r = new ArrayList<>();
        r.add(1);
        l.add(r);
        if (numRows == 1){
            return l;
        }
        for (int i = 1; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            for (int j = 0; j+1 < i ; j++) {
                row.add(l.get(i-1).get(j)+l.get(i-1).get(j+1));
            }
            row.add(1);
            l.add(row);
        }

        return l;
    }

    /*326. Power of Three*/
    public boolean isPowerOfThree(int n) {
        if (n <= 0){
            return false;
        }
        while ( n % 3 == 0 || n != 0) {
            n /= 3;
        }
        if (n == 0){
            return true;
        }
        return false;
    }

    /*2. Add Two Numbers*/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        if (l1.val == 0 && l1.next == null){
            return l2;
        }else if (l2.val == 0 && l2.next == null){
            return l1;
        }
        ListNode result = new ListNode();
        ListNode resIterator = result;
       while (l1 != null && l2 != null) {
           int n = l1.val + l2.val + carry;
           if (n > 9) {
               carry = 1;
               resIterator.val = n - 10;
           }else {
               resIterator.val = n;
               carry = 0;
           }
           l1 = l1.next;
           l2 = l2.next;
           if (l1 != null || l2 != null) {
               resIterator.next = new ListNode();
               resIterator = resIterator.next;
           }

       }
       while (l1 != null) {
           if (l1.val + carry > 9) {
               resIterator.val = l1.val + carry - 10; // + carry - 10 = -9
           }else {
               resIterator.val = l1.val + carry;
               carry = 0;
           }
           l1 = l1.next;
           if (l1 != null) {
               resIterator.next = new ListNode();
               resIterator = resIterator.next;
           }
       }
       while (l2 != null) {
           if (l2.val + carry > 9) {
               resIterator.val = l2.val + carry - 10;
           }else {
               resIterator.val = l2.val + carry;
               carry = 0;
           }
           l2 = l2.next;
           if (l2 != null) {
               resIterator.next = new ListNode();
               resIterator = resIterator.next;
           }
       }
       if (carry == 1) {
           resIterator.next = new ListNode();
           resIterator.next.val = 1;
       }

       return result;
    }

    /*2264. Largest 3-Same-Digit Number in String*/
    public String largestGoodInteger(String num) {
        int largest = -1;
        for (int i = 0; i < num.length() - 2; i++) {
            String s = num.substring(i,i+3);
            int n = s.charAt(0) - 48;
            if(s.equals(""+n+n+n)){
                if(n > largest){
                    largest = n;
                }
            }
        }
        return largest == -1 ? "" : ""+largest+largest+largest;
    }

    /*1903. Largest Odd Number in String*/
    public String largestOddNumber(String num) {
        for (int i = num.length()-1; i >= 0; i--) {
            if (num.charAt(i) % 2 != 0){
                return num.substring(0,i+1);
            }
        }
        return "";
    }

    /*2591. Distribute Money to Maximum Children*/
    public int distMoney(int money, int children) {
        int count = 0;
        if (money < children){ // se ho meno soldi rispetto al numero dei bambini + 7 (tutti con almeno 1 e uno che ha 8)
            return -1;
        }
        money -= children; //uno a tutti
        while (children-- > 0 && money > 0){
            money -= 7;
            if(money == 3 && children < 2 || money < 0){
                return count;
            }else{
                count++;
            }
        }
        return money == 0 ? count : count-1;
    }

    /*1103. Distribute Candies to People*/
    public int[] distributeCandies(int candies, int num_people) {
        int i = 0;
        int[] dist = new int[num_people];
        while (candies > 0){
            dist[i % (num_people)] += candies - (i+1) >= 0 ? ++i : candies;
            candies -= i;
        }
        return dist;
    }

    /*1323. Maximum 69 Number*/
    public int maximum69Number (int num) {
        String s = "" + num;
        int j = s.length();
        for(int i = 0; i < j; i++){
            if (s.charAt(i) == '6'){
                s = s.substring(0,i) + 9 + s.substring(i+1);
                break;
            }
        }
        return Integer.valueOf(s);
    }

    /*258. Add Digits*/
    public int addDigits(int num) {
        String s = "" + num;
        while (s.length() != 1) {
            num = 0;
            int len = s.length();
            for (int i = 0; i < len; i++) {
                num += s.charAt(i) - 48;
            }
            s = "" + num;
        }
        return Integer.valueOf(s);
    }

    /*1945. Sum of Digits of String After Convert*/
    public int getLucky(String s, int k) {
        int n = 0;
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            str += s.charAt(i) - 96;
        }
        for (int i = 0; i < k; i++) {
            n = 0;
            for (int j = 0; j < str.length(); j++) {
                n += str.charAt(j) - 48;
            }
            str = "" + n;
        }
        return n;
    }

    /*2348. Number of Zero-Filled Subarrays*/
    public long zeroFilledSubarray(int[] nums) {
        long sub = 0;
        long run;
        int i = 0;
        while (i < nums.length) {
            if (nums[i] == 0){
                run = 1;
                i++;
                while (i < nums.length && nums[i] == 0){
                    i++;
                    run++;
                }
                sub += run * (run+1) / 2;
            }
            i++;
        }
        return sub;
    }

    /*392. Is Subsequence*/
    public boolean isSubsequence(String s, String t) {
        int indexS = 0;
        int indexT = 0;
        while (indexS < s.length() && indexT < t.length()) {
            char c = s.charAt(indexS);
            while (t.charAt(indexT) != c) {
                indexT++;
                if (indexT >= t.length()) {
                    return false;
                }
            }
            indexS++;
        }
        return true;
    }

    /*1277. Count Square Submatrices with All Ones*/ // TODO fix and understand
    public int countSquares(int[][] A) {
        if (A == null || A.length == 0 || A[0].length == 0) {
            return 0;
        }

        int m = A.length;
        int n = A[0].length;
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][j] == 1 && i > 0 && j > 0) {
                    A[i][j] = Math.min(
                            A[i - 1][j - 1],
                            Math.min(A[i - 1][j], A[i][j - 1])
                    ) + 1;
                }
                res += A[i][j];
            }
        }

        return res;
    }

    /*3072. Distribute Elements Into Two Arrays II*/
    public int[] resultArray(int[] nums) {
        ArrayList<Integer> arr1 = new ArrayList<>();
        ArrayList<Integer> arr2 = new ArrayList<>();
        arr1.add(nums[0]);
        arr2.add(nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int count1 = greaterCount(arr1, nums[i]);
            int count2 = greaterCount(arr2, nums[i]);
            if (count1 > count2) {
                arr1.add(nums[i]);
            }else if (count1 < count2){
                arr2.add(nums[i]);
            }else {
                if (arr1.size() <= arr2.size()) {
                    arr1.add(nums[i]);
                }else{
                    arr2.add(nums[i]);
                }
            }
        }
        int len1 = arr1.size();
        int len2 = arr2.size();
        int[] res = new int[len1+len2];

        for (int i = 0; i < len1; i++) {
            res[i] = arr1.get(i);
        }
        for (int i = 0; i < len2; i++) {
            res[i+len1] = arr2.get(i);
        }
        System.out.println(arr1);
        System.out.println(arr2);
        return res;
    }

    public int greaterCount(ArrayList arr, int val){
        int c = 0;
        int l = arr.size();
        for (int i = 0; i < l; i++) {
            if((int) arr.get(i) > val){
                c++;
            }
        }
        return c;
    }

    /*1920. Build Array from Permutation*/

    /* [1, 2, 3, 4, 5, 6]
    */
    public int[] buildArray(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int n = nums[nums[i]];

        }
        return nums;
    }

    /*3195. Find the Minimum Area to Cover All Ones I*/
    public int minimumArea(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0){
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        if (rows == 1 && cols == 1){
            return 1;
        }
        int[] max = null; // max[0] = max row; max[1] = max col
        int[] min = null; // min[0] = min row; max[1] = min col
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1){
                    if (min == null && max == null){
                        min = new int[2];
                        min[0] = i;
                        min[1] = j;
                        max = new int[2];
                        max[0] = i;
                        max[1] = j;
                    }else{
                        if (max[0] < i) max[0] = i;
                        if (max[1] < j) max[1] = j;
                        if (min[0] > i) min[0] = i;
                        if (min[1] > j) min[1] = j;
                    }
                }
            }
        }
        return (max[0] - min[0] + 1) * (max[1] - min[1] + 1);
    }

    /*509. Fibonacci Number*/
    public int fib(int n, HashMap<Integer,Integer> hashMap) {
        if (hashMap.containsKey(n)) return hashMap.get(n);
        if (n <= 1) return n;
        int nthFib = fib(n-1,hashMap) + fib(n-2,hashMap);
        hashMap.put(n,nthFib);
        return nthFib;
    }

    /*5. Longest Palindromic Substring*/
    public String longestPalindrome(String s) {
        return longestPalindrome(s,new HashSet<>());
    }
    public String longestPalindrome(String s,  HashSet<String> list) {
        if (list.contains(s)) return s;
        if (s.length() == 1 || isPalindrome(s)){
            return s;
        }

        String longLeft = longestPalindrome(s.substring(0,s.length()-1),list);
        list.add(longLeft);
        String longRight = longestPalindrome(s.substring(1),list);
        list.add(longRight);

        return longLeft.length() > longRight.length() ? longLeft : longRight;
    }


    public boolean isPalindrome(String s) {
        if (s.length() == 1) return true;
        int l = s.length();
        for (int i = 0; i < l/2; i++) {
            if (s.charAt(i) != s.charAt(l - i - 1)) return false;
        }
        return true;
    }

    /*1137. N-th Tribonacci Number*/
    public int tribonacci(int n){
        return tribonacci(n,new HashMap<>());
    }

    public int tribonacci(int n, HashMap<Integer,Integer> hashMap) {
        if (hashMap.containsKey(n)) return hashMap.get(n);
        if (n <= 0) return 0;
        if (n <= 2) return 1;
        int trib = tribonacci(n-1,hashMap) + tribonacci(n-2,hashMap) + tribonacci(n-3,hashMap);
        hashMap.put(n,trib);
        return trib;
    }

    /*1668. Maximum Repeating Substring*/
    public int maxRepeating(String sequence, String word) {

        int start = contains(sequence,word);
        if (start == -1) return 0;
        int count = 1;
        int wLength = word.length();
        int sLength = sequence.length();
        start += wLength - 1;
        while ( start + wLength < sLength && word.equals(sequence.substring(start,start + wLength))){
            start+= wLength - 1;
            count++;
        }
        return count;
    }
    public int contains(String s, String key){
        int l = key.length();
        int sL = s.length();
        int i = 0;
        while (i + l < sL){
            if (key.equals(s.substring(i,i+l))) return i;
            i++;
        }

        return -1;
    }

    /*1493. Longest Subarray of 1's After Deleting One Element*/
    public int longestSubarray(int[] nums) {
        if (nums.length == 1) return 0;
        int max = 0;
        int start = 0;
        while (start < nums.length && nums[start] == 0) start++;

        while (start < nums.length){
            int firstZero = 0;
            int i = start;
            while (i < nums.length && nums[i] != 0) i++;
            if (start == 0 && i == nums.length) return i-1;
            firstZero = i++;
            while (i < nums.length && nums[i] != 0) i++;
            if (i-start-1 > max) max = i-start-1;
            start = ++firstZero;
        }
        return max;
    }
    public int longestSubarrayTLE(int[] nums) {
        if (nums.length == 1) return 0;
        int range = nums.length;
        while (range != 0) {
            int i = 0;
            while (i + range <= nums.length) {
                int numZeros = zeroCount(nums,i,i+range);
                if (numZeros <= 1) return range - 1;
                i++;
            }
            range--;
        }
        return 0;
    }

    public int zeroCount(int[]nums,int start, int end){
        int c = 0;
        for (int i = start; i < end; i++) {
            if (nums[i] == 0) c++;
        }
        return c;
    }

    /*498. Diagonal Traverse*/
    /* [[1,2,3],
        [4,5,6],
        [7,8,9]]
    */
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] arr = new int[n*m];
        int currentRow = 0;
        int currentCol = 0;
        String way = "up";
        for (int i = 0; i < n*m; i++) {
            arr[i] = mat[currentRow][currentCol];
            if ((currentRow == 0 || currentCol == n-1) && way.equals("up")) {
                if (currentCol == n-1) {
                    currentRow++;
                }else if (currentCol != n-1){
                    currentCol++;
                }
                way = "down";
            }else if ((currentCol == 0 || currentRow == m-1) && way.equals("down")) {
                if (currentRow == m-1) {
                    currentCol++;
                }else {
                    currentRow++;
                }
                way = "up";
            }else {
                if (way.equals("up")) {
                    currentRow--;
                    currentCol++;
                }else if (way.equals("down")) {
                    currentCol--;
                    currentRow++;
                }
            }

        }
        return arr;
    }

    /*3000. Maximum Area of Longest Diagonal Rectangle*/
    public int areaOfMaxDiagonal(int[][] dimensions) {
        double maxDiagonal = 0;
        int maxArea = 0;
        for (int i = 0; i < dimensions.length; i++) {
            int l = dimensions[i][0];
            int w = dimensions[i][1];
            double diagonal = Math.sqrt(l*l + w*w);
            if (maxDiagonal < diagonal) {
                maxDiagonal = diagonal;
                maxArea = l*w;
            }else if (maxDiagonal == diagonal) {
                if (maxArea < l*w) maxArea = l*w;
            }
        }
        return maxArea;
    }

    /*3459. Length of Longest V-Shaped Diagonal Segment*/
    public int[][] dirs = {{1,1},{1,-1},{-1,-1},{-1,1}};
    public int lenOfVDiagonal(int[][] grid) {
        int maxLength = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    if (maxLength == 0) maxLength = 1;
                    for (int k = 0; k < 4; k++) {
                        int nextRow = i + dirs[k][0];
                        int nextCol = j + dirs[k][1];
                        if (nextRow >= 0 && nextRow < grid.length &&
                            nextCol >= 0 && nextCol < grid[0].length &&
                            grid[nextRow][nextCol] == 2 ) {
                            int segment = 2 + searchValidSegment(grid,nextRow,nextCol,k,false);
                            if (maxLength < segment) maxLength = segment;
                        }
                    }
                }
            }
        }
        return maxLength;
    }

    public int searchValidSegment(int[][] grid, int row,int col,int k,boolean hasTurn) {
        int noTurn;
        int turn;
        int nextRow = row + dirs[k][0];
        int nextCol = col + dirs[k][1];
        if (nextRow < 0 || nextCol < 0 || nextRow >= grid.length || nextCol >= grid[0].length ||
            grid[nextRow][nextCol] == 1 || grid[row][col] == grid[nextRow][nextCol]) {
            noTurn = 0;
        }else {
            noTurn = 1 + searchValidSegment(grid,nextRow,nextCol,k,hasTurn);
        }
        k = (k+1) % 4;
        int turnRow = row + dirs[k][0];
        int turnCol = col + dirs[k][1];
        if (hasTurn || turnRow < 0 || turnCol < 0 || turnRow >= grid.length || turnCol >= grid[0].length ||
            grid[turnRow][turnCol] == 1 || grid[row][col] == grid[turnRow][turnCol]) {
            turn = 0;
        }else {
            turn =  1 + searchValidSegment(grid,turnRow,turnCol,k,true);
        }

        int mostLongOption = Math.max(noTurn,turn);
        return mostLongOption;
    }

    /*3446. Sort Matrix by Diagonals*/
    /*
    bottom-left = decreasing
    top-right = increasing
    */
    public int[][] sortMatrix(int[][] grid) {
        sortDiagonalDecreasing(grid,0);
        for (int i = 1; i < grid.length - 1; i++) {
            System.out.println("i:" + i);
            sortDiagonalDecreasing(grid,i);
            sortDiagonalIncreasing(grid,i);
        }
        return grid;
    }

    public void sortDiagonalIncreasing(int[][] grid, int startCol) {
        System.out.println("startCol: " + startCol);
        for (int i = 0; i < grid.length; i++) {
            boolean swap = true;
            int row = 0;
            for (int j = startCol; j < grid.length - 1; j++) {
                if (grid[row][j] > grid[row + 1][j + 1]) {
                    int t = grid[row][j];
                    grid[row][j] = grid[row + 1][j + 1];
                    grid[row + 1][j + 1] = t;
                    swap = false;
                }
                row++;
            }
            if (swap) return;
        }
    }

    public void sortDiagonalDecreasing(int[][] grid, int startRow) {
        System.out.println("srartRow: " + startRow);
        for (int i = 0; i < grid.length; i++) {
            boolean swap = true;
            int col = 0;
            for (int j = startRow; j < grid.length - 1; j++) {
                if (grid[j][col] < grid[j + 1][col + 1]) {
                    int t = grid[j][col];
                    grid[j][col] = grid[j + 1][col + 1];
                    grid[j + 1][col + 1] = t;
                    swap = false;
                }
                col++;
            }
            if (swap) return;
        }

    }

    public void bubbleSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - 1; j++) {
                if (a[j] > a[j+1]) {
                    int t = a[j];
                    a[j] = a[j+1];
                    a[j+1] = t;
                }
            }
        }
    }

    /*3021. Alice and Bob Playing Flower Game*/
    public long flowerGameBad(int n, int m) {
        long out = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if ((j + i) % 2 != 0) {
                    out++;
                    //System.out.println(out + ": " + i + ", " + j);
                }
            }
        }
        return out;
    }

    public long flowerGame(int n,int m) {
        return 2L * n/2 * m/2;
    }

    /*36. Valid Sudoku*/
    public boolean isValidSudoku(char[][] board) {
        HashSet<Character> rowSet;
        HashSet<Character> colSet;
        ArrayList<HashSet<Character>> arr = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            arr.add(i,new HashSet<>());
        }
        for (int i = 0; i < board.length; i++) {
            rowSet = new HashSet<>();
            colSet = new HashSet<>();
            for (int j = 0; j < board[0].length; j++) {
                double subBoxIndex = Math.floor(i / 3) * 3 + Math.floor(j / 3);
                System.out.println("box: " + subBoxIndex);
                System.out.println("char row: " + board[i][j]);
                System.out.println("char col: " + board[j][i]);
                System.out.println();
                if (board[i][j] != '.' &&
                    (rowSet.contains(board[i][j]) ||
                     arr.get((int) subBoxIndex).contains(board[i][j])) || colSet.contains(board[j][i])) {
                    System.out.println(arr);
                    System.out.println(rowSet);
                    System.out.println(colSet);
                    return false;
                }
                if (board[i][j] != '.') {
                    rowSet.add(board[i][j]);
                    arr.get((int) subBoxIndex).add(board[i][j]);
                }
                if (board[j][i] != '.') colSet.add(board[j][i]);

            }
        }
        return true;
    }


    /*1792. Maximum Average Pass Ratio*/
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        double maxRatio = 0;
        PriorityQueue<double[]> priorityQueue = new PriorityQueue<>(new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                double ret = o1[0] - o2[0];
                if (ret == 0) return 0;
                return ret > 0 ? 1 : -1;
            }
        });
        for (int i = 0; i < classes.length; i++) {
            priorityQueue.add(new double[]{(classes[i][0] + 1.0) / (classes[i][1] + 1) - (double) classes[i][0] / classes[i][1], i});
        }
        System.out.println(priorityQueue.toString());
        while (extraStudents > 0) {
            extraStudents--;
            double[] bestCase = priorityQueue.poll();
            classes[(int) bestCase[1]][0] += 1;
            classes[(int) bestCase[1]][1] += 1;
            bestCase[0] = (classes[(int) bestCase[1]][0] + 1.0) / (classes[(int) bestCase[1]][0] + 1) - (double) classes[(int) bestCase[1]][0] / classes[(int) bestCase[1]][1];
            priorityQueue.add(bestCase);
        }
        for (int i = 0; i < classes.length; i++) {
            maxRatio += (double) classes[i][0] / classes[i][1];
            //System.out.println("0: " + classes[i][0] + ",  1: " + classes[i][1]);
        }

        return maxRatio / classes.length;
    }

    /*3025. Find the Number of Ways to Place People I*/
    public int numberOfPairs(int[][] points) {
        int pairs = 0;
        for (int i = 0; i < points.length; i++) {
            int x = points[i][0];
            int y = points[i][1];
            for (int j = 0; j < points.length; j++) {
                int x2 = points[j][0];
                int y2 = points[j][1];
                if (x <= x2 && y >= y2 && i != j) {
                    boolean pair = true;
                    for (int k = 0; k < points.length; k++) {
                        if (k != i && k != j &&
                                (points[k][0] >= x && points[k][0] <= x2) &&
                                (points[k][1] <= y && points[k][1] >= y2) ) {
                            pair = false;
                        }
                    }
                    if (pair) pairs++;
                }
            }
        }
        return pairs;
    }

    /*Q1. Restore Finishing Order*/
    public int[] recoverOrder(int[] order, int[] friends) {
        int[] friendsOrder = new int[friends.length];
        int frIndex = 0;
        for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < friends.length; j++) {
                if (order[i] == friends[j]) {
                    friendsOrder[frIndex] = friends[j];
                    frIndex++;
                }
            }
        }
        return friendsOrder;
    }

    /*7. Reverse Integer*/
    public int reverse(int x) {
        String s = "" + x;
        String rev = "";
        boolean neg = s.charAt(0) == '-';
        for (int i = s.length() - 1; i > 0 ; i--) {
            rev += s.charAt(i);
        }
        if (!neg) rev+= s.charAt(0);
        if (rev.length() == 10 && !inbound(rev,neg)) return 0;


        return neg ? -Integer.parseInt(rev) : Integer.parseInt(rev);
    }

    public boolean inbound (String s, boolean neg){
        String max = neg ? "2147483648" : "2147483647";
        boolean certain= false;
        int i = 0;
        while(!certain) {
            if (s.charAt(i) < max.charAt(i)) return true;
            else if (s.charAt(i) > max.charAt(i)) return false;
            else if (s.charAt(i) == max.charAt(i));
        }
        return false;
    }

    /*104. Maximum Depth of Binary Tree*/
    public int maxDepth(TreeNode root) {
        return furtherSearch(root);
    }

    public int furtherSearch(TreeNode node) {
        if (node == null) return 0;
        TreeNode left = node.left;
        TreeNode right = node.right;
        return 1 + Math.max(furtherSearch(left),furtherSearch(right));
    }

    /*100. Same Tree*/
    public boolean isSameTree(TreeNode p, TreeNode q) {
        return compare(p,q);
    }

    public boolean compare(TreeNode n1,TreeNode n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null || n1.val != n2.val) return false;
        return compare(n1.left,n2.left) && compare(n1.right,n2.right);

    }

    /*101. Symmetric Tree*/
    public boolean isSymmetric(TreeNode root) {
        return compareMirrored(root.left,root.right);
    }

    public boolean compareMirrored(TreeNode n1,TreeNode n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null || n1.val != n2.val) return false;
        return compareMirrored(n1.left,n2.right) && compareMirrored(n1.right,n2.left);

    }

    /*111. Minimum Depth of Binary Tree*/
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        return searchMinDepth(root);
    }

    public int searchMinDepth(TreeNode node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        int leftDepth = node.left == null ? 0 : searchMinDepth(node.left);
        int rightDepth = node.right == null ? 0 : searchMinDepth(node.right);
        return Math.min(leftDepth,rightDepth);
    }

    /*2327. Number of People Aware of a Secret*/
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        return 0;
    }

    /*383. Ransom Note*/
    public boolean canConstruct(String ransomNote, String magazine) {
        HashMap<Character,Integer> hashMap = new HashMap<>();
        int l = magazine.length();
        for (int i = 0; i < l; i++) {
            char c = magazine.charAt(i);
            if (hashMap.containsKey(c)) hashMap.put(c,hashMap.get(c)+1);
            else hashMap.put(c,1);
        }
        return true;
    }

    /*4. Median of Two Sorted Arrays*/
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merged = new int[nums1.length+nums2.length];
        int index = 0;
        int i1 = 0;
        int i2 = 0;
        while (i1 < nums1.length && i2 < nums2.length) {
            if (nums1[i1] < nums2[i2]) {
                merged[index++] = nums1[i1++];
            }else {
                merged[index++] = nums2[i2++];
            }
        }
        while (i1 < nums1.length) {
            merged[index++] = nums1[i1++];
        }
        while (i2 < nums2.length) {
            merged[index++] = nums2[i2++];
        }
        if (merged.length % 2 == 0) return (merged[merged.length/2] + merged[merged.length/2 - 1]) / 2.0;
        else return (double) merged[merged.length/2];
    }

    /*2960. Count Tested Devices After Test Operations*/
    public int countTestedDevices(int[] batteryPercentages) {
        int tested = 0;
        for (int i = 0; i < batteryPercentages.length; i++) {
            if (batteryPercentages[i] > 0) {
                tested++;
                for (int j = i+1; j < batteryPercentages.length; j++) {
                    if (batteryPercentages[j] > 0) batteryPercentages[j]--;
                }
            }
        }
        return tested;
    }

    /*3081. Replace Question Marks in String to Minimize Its Value*/
    public String minimizeStringValue(String s) {
        int[] freq = new int[26];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        ArrayList<Integer> qMIndexes = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '?') {
                qMIndexes.add(i);
            }else freq[c - 97]++;
        }
        minCost(freq,qMIndexes.size(),priorityQueue);
        int prev = 0;
        for (Integer i : qMIndexes) {
            s = s.substring(prev,i) + (char) (0 + priorityQueue.poll()) + s.substring(i+1);
            if (prev != 0) prev = i;
        }
        return s;
    }

    public void minCost(int[] freq,int qmCount,PriorityQueue<Integer> pQ) {
        for (int j = 0; j < qmCount; j++) {
            int minIndex = 0;
            int minValue = freq[0];
            for (int i = 1; i < freq.length; i++) {
                if (freq[i] < minValue) {
                    minValue = freq[i];
                    minIndex = i;
                }
            }
            freq[minIndex]++;
            pQ.add(minIndex + 97);
        }
    }

    /*2785. Sort Vowels in a String*/
    public String sortVowels(String s) {
        int[] freq = new int[10]; // a = 0; u = 4
        String vowels = "AEIOUaeiou";
        PriorityQueue<Character> vowelQueue= new PriorityQueue<>();
        int l = s.length();
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
            if (vowels.contains("" + c )) vowelQueue.add(c);
        }

        StringBuilder sorted = new StringBuilder();
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
            if (vowels.contains("" + c)) sorted.append(vowelQueue.poll());
            else sorted.append(c);
        }

        return sorted.toString();
    }
    /*3541. Find Most Frequent Vowel and Consonant*/
    public int maxFreqSum(String s) {
        int[] freq = new int[26];
        int l = s.length();
        for (int i = 0; i < l; i++) {
            freq[s.charAt(i) - 97]++;
        }
        int maxVowel = 0;
        int maxConsonant = 0;
        for (int i = 0; i < 26; i++) {
            switch (i) {
                case 0:
                case 4:
                case 8:
                case 14:
                case 20:
                    if (maxVowel < freq[i]) maxVowel = freq[i];
                    break;
                default:
                    if (maxConsonant < freq[i]) maxConsonant = freq[i];

            }
        }
        return maxVowel + maxConsonant;
    }

    /*1935. Maximum Number of Words You Can Type*/
    public int canBeTypedWords(String text, String brokenLetters) {
        if (brokenLetters.length() == 26) return 0;
        int canType = 0;
        int l = text.length();
        int i = 0;
        //boolean skip = false;
        while (i < l) {
            while (i < l && text.charAt(i) == ' ') i++;
            while (i < l && text.charAt(i) != ' ') {
                if (brokenLetters.contains("" + text.charAt(i))) {
                    break;
                }else i++;
            }
            if (i == l || text.charAt(i) == ' ') canType++;
        }
        return canType;
    }



    /*2197. Replace Non-Coprime Numbers in Array*/
    public List<Integer> replaceNonCoprimes(int[] nums) {
        List<Integer> list = new ArrayList<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }

        for (int i = 0; i < list.size() - 1; i++) {
            int gcd = gcd(list.get(i),list.get(i+1));
            if (gcd > 1) {
                int a = list.remove(i);
                int b = list.remove(i);
                if (a == gcd && b == gcd) list.add(i,a);
                else list.add(i,(a*b) / gcd);
                i -= 2;
                if (i < 0) i = -1;
            }
        }
        return list;
    }

    public int gcd(int a, int b) {
        if (a == b) return a;
        if (a == 0) return b;
        if (b == 0) return a;
        if (a % 2 == 0 && b % 2 == 0) return 2 * gcd(a/2,b/2);
        if (a % 2 == 0) return gcd(a/2,b);
        if (b % 2 == 0) return gcd(a,b/2);
        if (a < b) return gcd(b-a,a);
        else return gcd(a-b,b);
    }




}