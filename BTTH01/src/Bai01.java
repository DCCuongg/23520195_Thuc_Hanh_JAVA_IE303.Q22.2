import java.util.Random;
import java.util.Scanner;

public class Bai01 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Nhap ban kinh r: ");
        double r = sc.nextDouble();

        int totalPoints = 1000000; // số điểm random
        int countInside = 0;

        for (int i = 0; i < totalPoints; i++) {

            double x = -r + 2 * r * rand.nextDouble();
            double y = -r + 2 * r * rand.nextDouble();

            if (x * x + y * y <= r * r) {
                countInside++;
            }
        }

        double area = ((double) countInside / totalPoints) * (2 * r) * (2 * r);

        System.out.println("Dien tich xap xi hinh tron: " + area);

        sc.close();
    }
}