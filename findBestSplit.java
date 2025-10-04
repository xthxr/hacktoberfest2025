// User function Template for Java
class Solution {
    long minDifference(int N, int A[]) {
        // Step 1: prefix sums
        long[] prefix = new long[N + 1];
        for (int i = 0; i < N; i++) {
            prefix[i + 1] = prefix[i] + A[i];
        }
        long ans = Long.MAX_VALUE;

        for (int j = 2; j <= N - 2; j++) {
            // Find best i for left part [0..j-1]
            int left_i = findBestSplit(prefix, 0, j);

            for (int i : new int[]{left_i - 1, left_i, left_i + 1}) {
                if (i <= 0 || i >= j) continue;

                long W = prefix[i];
                long X = prefix[j] - prefix[i];

                // Find best k for right part [j..N-1]
                int right_k = findBestSplit(prefix, j, N);

                for (int k : new int[]{right_k - 1, right_k, right_k + 1}) {
                    if (k <= j || k >= N) continue;

                    long Y = prefix[k] - prefix[j];
                    long Z = prefix[N] - prefix[k];

                    long maxVal = Math.max(Math.max(W, X), Math.max(Y, Z));
                    long minVal = Math.min(Math.min(W, X), Math.min(Y, Z));
                    ans = Math.min(ans, maxVal - minVal);
                }
            }
        }
        return ans;
    }

    // Helper: Finds the split point which minimizes |sum(left) - sum(right)|
    private int findBestSplit(long[] prefix, int l, int r) {
        int low = l + 1, high = r - 1, best = low;
        long minDiff = Long.MAX_VALUE;
        while (low <= high) {
            int mid = (low + high) / 2;
            long s1 = prefix[mid] - prefix[l];
            long s2 = prefix[r] - prefix[mid];
            long diff = Math.abs(s1 - s2);
            if (diff < minDiff) {
                minDiff = diff;
                best = mid;
            }
            if (s1 < s2)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return best;
    }
}
