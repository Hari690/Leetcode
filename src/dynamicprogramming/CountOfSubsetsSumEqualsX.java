package dynamicprogramming;

/**
 * Given an array arr[] of length N and an integer X, the task is to find the number of subsets with a sum equal to X.
 *
 * Examples:
 *
 * Input: arr[] = {1, 2, 3, 3}, X = 6
 * Output: 3
 * All the possible subsets are {1, 2, 3},
 * {1, 2, 3} and {3, 3}
 *
 * Input: arr[] = {1, 1, 1, 1}, X = 1
 * Output: 4
 */
public class CountOfSubsetsSumEqualsX {

    // similar to 0/1 knapsack
    static int subsetSum(int a[], int n, int sum)
    {

        int tab[][] = new int[n + 1][sum + 1];
        tab[0][0] = 1;
        for(int i = 1; i <= sum; i++)
            tab[0][i] = 0;

        for(int i = 1; i <= n; i++)
            tab[i][0] = 1;

        for(int i = 1; i <= n; i++)
        {
            for(int j = 1; j <= sum; j++)
            {

                // If the value is greater than the sum
                if (a[i - 1] > j)
                    tab[i][j] = tab[i - 1][j];
                else
                {
                    tab[i][j] = tab[i - 1][j] +
                        tab[i - 1][j - a[i - 1]];
                }
            }
        }
        return tab[n][sum];
    }
}
