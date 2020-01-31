import java.math.BigInteger;
import java.util.Arrays;

public class hw {
    public static void main(String[] args) {
        System.out.println("1) Кролики и рекуррентные отношения");
        System.out.println("тест при n=5 и k=3: " + rabbitRec(5, 3));
        System.out.println("Потомство возрастет до: " + rabbitRec(32, 5));
        System.out.println("2) Кролики и рекуррентные отношения, учитывая смертность");
        System.out.println("тест при n=6 и k=3: " + rabbitWithDies(6, 3));
        //System.out.println("zcx: "+ rabbitRecWithDies(6,3));
        //System.out.println("potomstvo" + rabbitRecWithDies(85,19));
        System.out.println("Потомство возрастет до: " + rabbitWithDies(85, 19));
        int[] a1 = new int[]{0, 2, 2};
        int[] a2 = new int[]{1, 3};
        System.out.println("Сортировка двух массивов слиянием: " + Arrays.toString(mergeArrays(a1, a2)));
    }

    // Рекурсивно вызываем функцию "Fn=Fn−1+Fn−2"
    static long rabbitRec(int month, int k) {
        if (month <= 2) {  // потомство растет до взрослых 1 месяц.
            return 1;
        }
        return rabbitRec(month - 1, k) + k * rabbitRec(month - 2, k); // Текущее потомство это сумма двух предыдущих месяцев
    }

    // Хотел сделать второе задание также через рекурсию, но при больших числах метод зависает..
    static long rabbitRecWithDies(int n, int m) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n <= m)
            return rabbitRecWithDies(n - 1, m) + rabbitRecWithDies(n - 2, m);
        else if (n == m + 1)
            return rabbitRecWithDies(n - 1, m) + rabbitRecWithDies(n - 2, m) - 1;
        else
            return rabbitRecWithDies(n - 1, m) + rabbitRecWithDies(n - 2, m) - rabbitRecWithDies(n - (m + 1), m);
    }

    // Попробовал через BigInteger даже(не знаю правильно или нет)
    static BigInteger rabbitRecWithDiesBI(int n, int m) {
        if (n <= 0) return BigInteger.valueOf(0);
        if (n == 1) return BigInteger.valueOf(1);
        if (n <= m)
            return rabbitRecWithDiesBI(n - 1, m).add(rabbitRecWithDiesBI(n - 2, m));
        else if (n == m + 1)
            return rabbitRecWithDiesBI(n - 1, m).add((rabbitRecWithDiesBI(n - 2, m).subtract(BigInteger.valueOf(1))));
        else
            return rabbitRecWithDiesBI(n - 1, m).add(rabbitRecWithDiesBI(n - 2, m).subtract(rabbitRecWithDiesBI(n - (m + 1), m)));
    }

    public static long rabbitWithDies(int n, int m) {
        long[] f = new long[n + 1];
        long[] mass = new long[m];
        mass[0] = 1;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            long[] tmp = new long[m];
            for (int j = 1; j < m; j++) {
                tmp[j] = mass[j - 1];
                tmp[0] += mass[j];
            }
            for (int k = 0; k < m; k++) {
                mass[k] = tmp[k];
                f[i] += mass[k];
            }
        }
        return f[n];
    }

    public static int[] mergeArrays(int[] a1, int[] a2) {
        int i = 0, j = 0, k = 0;
        int[] a3 = new int[a1.length + a2.length];
        while (i < a1.length && j < a2.length) {
            a3[k++] = a1[i] < a2[j] ? a1[i++] : a2[j++]; // Сравнивая разбивается на две части +- одинакового размера и сортируется отдельно.
        }
/*
Два получившихся упорядоченных массива соединяются в один.
Наименьший из первых элементов копируется в результирующий массив, и эта операция повторяется, пока не закончатся элементы в этих двух массивах.
 */
        if (i < a1.length) {
            System.arraycopy(a1, i, a3, k, a1.length - i);
        } else if (j < a2.length) {
            System.arraycopy(a2, j, a3, k, a2.length - j);
        }
        return a3;
    }
}
