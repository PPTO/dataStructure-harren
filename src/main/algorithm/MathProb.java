package algorithm;

import java.util.Arrays;
import java.util.Scanner;

public class MathProb {
    Scanner in = new Scanner(System.in);

    /**
     * HJ7 取近似值
     */
    public void hj7(){
        double ans = in.nextDouble();
        System.out.print((int)(ans + 0.5));
    }

    public void hj46(){
        int n = in.nextInt();
        Integer[] ints = new Integer[n];
        for (int i = 0; i < n; i++) {
            ints[i] = in.nextInt();
        }
        int k = in.nextInt();
        Arrays.sort(ints, k == 0 ? (o1, o2)-> o1 - o2 : (o1, o2)-> o2 - o1);


    }
}
