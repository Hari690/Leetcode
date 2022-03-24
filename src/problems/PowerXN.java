package problems;

/**
 * Implement pow(x, n), which calculates x raised to the power n (i.e., xn).
 * Example 1:
 *
 * Input: x = 2.00000, n = 10
 * Output: 1024.00000
 * Example 2:
 *
 * Input: x = 2.10000, n = 3
 * Output: 9.26100
 * Example 3:
 *
 * Input: x = 2.00000, n = -2
 * Output: 0.25000
 * Explanation: 2-2 = 1/22 = 1/4 = 0.25
 */
public class PowerXN {
    public double myPow(double x, int n) {
        if(x==0)
            return 0.0;
        if(n==0)
            return 1;
        if(n==1)
            return x;
        double ans = myPow(x, Math.abs(n/2));
        double result=(n>=0)?ans*ans:1/(ans*ans);
        if(n%2==1 || n%2==-1){
            result=(n>=0)?result*x:(result*(1/x));
        }
        return result;
    }
}
