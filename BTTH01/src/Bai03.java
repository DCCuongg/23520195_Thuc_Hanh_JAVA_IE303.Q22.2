
/*
 * soft bằng độ dài (nhân thấy các điểm có độ sai từ gốc bé hơn không bao được
 * các điểm có độ sài vector lớn hơn (không áp dựng ngược lại))
 * lấy 4 điểm có chỉ số giá trị x,y cao nhất
 * duyệt theo độ dài thâm các điểm ngoài vòng vào danh sách
 * sort lại danh sách theo độ dài
 * thuật toán xét có ngoài vòng không dung đường thẳng đồng dấu với tâm 0
 */
/*
 * Cho các thuật toán vector cùng độ dai để xác định hướng,
 * bên phải vector thì có hàm xác định được đây là biến liên tục, tích vô hướng
 * vào pháp tuyền bên phải
 * lần này có tể dung sort lọc độ dài nửa mà để xếp các điển theo chiều xoay
 * Xác định so sánh với cặp vector nào bằng cách thử xem cặp vector liên kề đo khi dương có tạo ra được điểm đó hay không
 * 
 * Có danh sách cover (các điểm ngoài ban đầu: maxX, minX, maxY, minY).
 * Duyệt từng điểm trong copy(được sắp xếp có độ dài giảm dẩn).
 * Xác định điểm đó nằm giữa hai vector nào trong cover (hai điểm kề nhau).
 * Lấy đường thẳng qua 2 điểm cover đó.
 * Kiểm tra điểm cần xét và tâm O(0,0) có cùng phía hay không.
 * Nếu không cùng phía → điểm nằm ngoài → chèn vào giữa 2 điểm cover đó.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Point {
  int x;
  int y;

  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

public class Bai03 {
  static Point centroid(Point[] points) {
    int sumX = 0;
    int sumY = 0;

    for (Point p : points) {
      sumX += p.x;
      sumY += p.y;
    }

    int cx = sumX / points.length;
    int cy = sumY / points.length;

    return new Point(cx, cy);
  }

  static Point[] insertPoint(Point[] cover, Point p, int index) {

    Point[] newCover = new Point[cover.length + 1];

    for (int i = 0; i <= index; i++) {
      newCover[i] = cover[i];
    }

    newCover[index + 1] = p;

    for (int i = index + 1; i < cover.length; i++) {
      newCover[i + 1] = cover[i];
    }

    return newCover;
  }

  static boolean sameSide(Point A, Point B, Point P, Point Q) {

    int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
    int cp2 = (B.x - A.x) * (Q.y - A.y) - (B.y - A.y) * (Q.x - A.x);

    return cp1 * cp2 >= 0;
  }

  static Point[] copyAndSortByDistance(Point[] arr) {

    Point[] copy = arr.clone();
    Point center = centroid(copy);

    Arrays.sort(copy, (a, b) -> compare(a, b, center));
    return copy;
  }

  static List<Point> getExtremePoints(Point[] points) {

    Point maxX = points[0];
    Point minX = points[0];
    Point maxY = points[0];
    Point minY = points[0];

    for (Point p : points) {

      if (p.x > maxX.x)
        maxX = p;
      if (p.x < minX.x)
        minX = p;

      if (p.y > maxY.y)
        maxY = p;
      if (p.y < minY.y)
        minY = p;
    }

    // dùng Set để tránh trùng
    Set<Point> set = new HashSet<>();
    set.add(maxX);
    set.add(minX);
    set.add(maxY);
    set.add(minY);

    return new ArrayList<>(set);
  }

  static boolean isBetween(Point A, Point B, Point C, Point center) {

    int Bx = B.x - center.x;
    int By = B.y - center.y;

    int Cx = C.x - center.x;
    int Cy = C.y - center.y;

    int Ax = A.x - center.x;
    int Ay = A.y - center.y;

    double D = Bx * Cy - By * Cx;
    double Dx = Ax * Cy - Ay * Cx;
    double Dy = Bx * Ay - By * Ax;

    if (D == 0)
      return false; // B và C cùng phương

    double x = Dx / D;
    double y = Dy / D;

    return x >= 0 && y >= 0;
  }

  public static int compare(Point p1, Point p2, Point c) {

    int len1 = (p1.x - c.x) * (p1.x - c.x) + (p1.y - c.y) * (p1.y - c.y);
    int len2 = (p2.x - c.x) * (p2.x - c.x) + (p2.y - c.y) * (p2.y - c.y);

    return Integer.compare(len2, len1); // giảm dần (xa hơn đứng trước)
  }

  // Hàm nhập dữ liệu theo format đề
  public static Point[] nhapInput() {
    Scanner scan = new Scanner(System.in);

    int n = scan.nextInt(); // số trạm
    Point[] dsPoint = new Point[n];

    for (int i = 0; i < n; i++) {
      int x = scan.nextInt();
      int y = scan.nextInt();
      dsPoint[i] = new Point(x, y);
    }
    scan.close();
    return dsPoint;
  }

  public static void main(String[] args) {

    Point[] dsPoint = nhapInput();
    Point[] copy = copyAndSortByDistance(dsPoint);
    Point[] cover = getExtremePoints(copy).toArray(new Point[0]);
    Point origin = centroid(copy);
    for (Point p : copy) {

      boolean existed = false;

      for (Point c : cover) {
        if (c == p) {
          existed = true;
          break;
        }
      }

      if (existed)
        continue;

      for (int i = 0; i < cover.length; i++) {

        Point a = cover[i];
        Point b = cover[(i + 1) % cover.length];

        if (isBetween(p, a, b, origin)) {

          if (!sameSide(a, b, p, origin)) {
            cover = insertPoint(cover, p, i);
          }

          break;
        }
      }
    }
    // test in ra dữ liệu đã nhập
    System.out.println("Output cover:");
    for (Point t : cover) {
      System.out.println(t.x + " " + t.y);
    }
    System.out.println("Output copy:");
    for (Point t : copy) {
      System.out.println(t.x + " " + t.y);
    }
  }
}