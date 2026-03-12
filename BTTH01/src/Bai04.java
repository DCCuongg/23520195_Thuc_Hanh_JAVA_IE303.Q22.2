import java.util.*;

public class Bai04 {

  static List<Integer> best = new ArrayList<>();

  static void dfs(int[] a, int k, int i, int sum, List<Integer> cur) {
    if (sum == k) {
      if (cur.size() > best.size())
        best = new ArrayList<>(cur);
      return;
    }

    if (i == a.length || sum > k)
      return;

    // chọn phần tử a[i]
    cur.add(a[i]);
    dfs(a, k, i + 1, sum + a[i], cur);
    cur.remove(cur.size() - 1);

    // không chọn a[i]
    dfs(a, k, i + 1, sum, cur);
  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int k = sc.nextInt();

    int[] a = new int[n];
    for (int i = 0; i < n; i++)
      a[i] = sc.nextInt();

    dfs(a, k, 0, 0, new ArrayList<>());

    for (int x : best)
      System.out.print(x + " ");
  }
}