
// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.util.Random;

public class Bai02 {
  public Bai02() {
  }

  public static void main(String[] var0) {
    Random var1 = new Random();
    int var2 = 1000000;
    int var3 = 0;

    for (int var4 = 0; var4 < var2; ++var4) {
      double var5 = (double) -1.0F + (double) 2.0F * var1.nextDouble();
      double var7 = (double) -1.0F + (double) 2.0F * var1.nextDouble();
      if (var5 * var5 + var7 * var7 <= (double) 1.0F) {
        ++var3;
      }
    }

    double var9 = (double) var3 / (double) var2 * (double) 4.0F;
    System.out.println("PI = " + var9);
  }
}
