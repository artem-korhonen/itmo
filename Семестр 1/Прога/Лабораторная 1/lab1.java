public class lab1 {
    // Высчитывание значения для z1 через z[i] и x[j]
    public static double Calc(double z, double x) {
        if (z == 15) {
            return(Math.asin(Math.sin(Math.pow((Math.asin((x - 6) / 16.0)) / (Math.cbrt(x) - 1), ((x+2)/2.0)/3.0))));
        }
        if (z == 6 || z == 8 || z == 11 || z == 13 || z == 16) {
            return(Math.cbrt(Math.atan(1 / Math.pow(Math.E, Math.abs(x)))));
        }
        else {
            return(Math.tan(Math.pow(Math.PI / Math.pow((2 * Math.cbrt(x)), 3), 2)));
        }
    }

    // Вывод двумерного массива
    public static void Printer(double[][] z1, int a1, int a2) {
        System.out.print("[");
        for (int i = 0; i < a1; i++) {
            System.out.print("[");
            for (int j = 0; j < a2; j++) {
                if (Double.isNaN(z1[i][j])) {
                    System.out.print("NaN");
                } else {
                    System.out.printf("%.3f", z1[i][j]);
                }
                if (j != a2 - 1) {
                    System.out.print(", ");
                }
            }
            if (i != a1 - 1) {
                System.out.println("], ");
            } else {
                System.out.print("]");
            }
        }
        System.out.println("]");
    }

    // Функция main
    public static void main(String[] args) {
        short[] z = new short[11];
        double[] x = new double[14];
        double[][] z1 = new double[11][14];
        int k = 0;

        // Заполнение массива z числами от 6 до 16 включительно
        for (short i = 6; i <= 16; i++) {
            z[k] = i;
            k++;
        }

        // Заполнение массива x 14-ю случайными числами от -14 до 2 (17 = abs(-14 - 2) + 1)
        for (int i = 0; i < x.length; i++) {
            x[i] = (Math.random() * 17) - 14;
        }

        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < x.length; j++) {
                z1[i][j] = Calc(z[i], x[j]);
            }
        }

        Printer(z1, z.length, x.length);
    }
}