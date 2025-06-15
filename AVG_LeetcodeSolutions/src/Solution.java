class Solution {
    /*21. Merge Two Sorted Lists*/
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null){
            return list2;
        }else if(list2 == null){
            return list1;
        }
        int[] vals = new int[100];
        int nVals = 0;
        while(list1 != null && list2 != null){
            if(list1.val < list2.val){
                vals[nVals++] = list1.val;
                list1 = list1.next;
            }else {
                vals[nVals++] = list2.val;
                list2 = list2.next;
            }
        }
        if(list1 == null){
            while(list2 != null){
                vals[nVals++] = list2.val;
                list2 = list2.next;
            }
        }else{
            while(list1 != null){
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
            if(nums[i] != nums[i-1]){
                nums[x++] = nums[i];
            }
        }
        return x;
    }

    /*27. Remove Element*/
    public int removeElement(int[] nums, int val) {
        int x = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != val){
                nums[x++] = nums[i];
            }
        }
        return x;
    }

    /*28. Find the Index of the First Occurrence in a String*/
    public int strStr(String haystack, String needle) {
        int x = needle.length();
        if(haystack.equals(needle)){
            return 0;
        }
        for (int i = 0; i <= haystack.length()-needle.length(); i++) {
            if(haystack.substring(i,i + x).equals(needle)){
                return i;
            }
        }
        return -1;
    }

    /*35. Search Insert Position*/
    //TODO fix runtime complexity to O(log n)
    public int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length -1;
        while(start != end){
            if(nums[(end-start)/2] == target){
                return (end-start)/2;
            }else if(nums[(end-start)/2] > target){
                end = (end-start)/2 -1;
            }else{
                start = (end-start)/2 +1;
            }
        }
        return -1;
    }

    /*58. Length of Last Word*/
    public int lengthOfLastWord(String s) {
        int i = s.length()-1;
        while(s.charAt(i) == ' '){i--;}
        int len = 0;
        while(i >= 0 && s.charAt(i) != ' '){
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
        if(a.equals("0")){
            return b;
        }
        if(b.equals("0")){
            return a;
        }
        int i = 0;
        int lenA = a.length()-1;
        int lenB = b.length()-1;
        boolean riporto = false;
        while(lenA-i >= 0 && lenB-i >= 0){
            char cA = a.charAt(lenA-i);
            char cB = b.charAt(lenB-i);
            if(cA == '1' && cB == '1'){
                if(riporto){
                    sum = '1' + sum;
                }else{
                    riporto = true;
                    sum = '0' + sum;
                }
            } else if (cA == '0' && cB == '0') {
                if(riporto){
                    sum = '1' + sum;
                    riporto = false;
                }else{
                    sum = '0' + sum;
                }
            }else{
                if(riporto){
                    sum = '0' + sum;

                }else{
                    sum = '1' + sum;
                }
            }
            i++;
        }
        if(lenA-i >= 0){
            while(lenA-i >= 0){
                char cA = a.charAt(lenA-i);
                if(riporto && cA == '1'){
                    sum = '0' + sum;
                }else if(riporto || cA == '1'){
                    riporto = false;
                    sum = '1' + sum;
                }else{
                    sum = '0' + sum;
                }

                i++;
            }
        }else{
            while(lenB-i >= 0){
                char cB = b.charAt(lenB-i);
                if(riporto && cB == '1'){
                    sum = '0' + sum;
                }else if(riporto || cB == '1'){
                    riporto = false;
                    sum = '1' + sum;
                }else{
                    sum = '0' + sum;
                }

                i++;
            }
        }
        if(riporto){
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
        double pow = x;
        if(x == 0 || x == 1){
            return x;
        }
        if(n == 0){
            return 1;
        }
        if(n > 0){
            for (int i = 1; i < n; i++) {
                pow *= x;
            }
            if(n%2 == 0){
                return Math.abs(pow);
            }else{
                return pow;
            }
        }else{
            n = Math.abs(n);
            for (int i = 1; i < n; i++) {
                pow *= x;
            }
            return 1/pow;
        }
    }

    /*83. Remove Duplicates from Sorted List*/
    public ListNode deleteDuplicates(ListNode head) {
        ListNode nHead;
        ListNode last;
        if(head == null){
            return null;
        }else{
            nHead = new ListNode(head.val);
            last = nHead;
        }
        while(head != null){
            if(head.val != last.val){
                nHead = addNode(nHead, head.val);
                last = last.next;
            }
            head = head.next;
        }
        return nHead;
    }
    public ListNode addNode(ListNode node, int val){
        ListNode n = node;
        while(node.next != null){
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
        while(i < m && j < n){
            if(nums1C[i] <= nums2[j]){
                nums1[k++] = nums1C[i++];
            }else{
                nums1[k++] = nums2[j++];
            }

        }
        if(i >= m){
            while(j < n){
                nums1[k++] = nums2[j++];
            }
        }else{
            while(i < m){
                nums1[k++] = nums1C[i++];
            }
        }
    }
}