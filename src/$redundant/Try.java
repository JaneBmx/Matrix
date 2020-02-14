package $redundant;

import entity.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Try extends Thread {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(5);

        ExecutorService es = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            es.submit(new Try(5, cdl));
        }
        es.shutdown();
        cdl.await();
    }

    CountDownLatch countDownLatch;
    static Random random = new Random();
    static ArrayList<Integer> list;
    static Matrix matrix;
    //int sum;

    Try(int n, CountDownLatch cdl) {
        matrix = new Matrix(n);
        list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        countDownLatch = cdl;
    }

    public void run() {
//        int n = -1;
//        while (n == -1) {//пробуем пока нет свободных "разрешений"
//            n = getPermission();
//        }
        //как только получили - продолжаем
        int n = getPermission();

        this.fillMatrix(n);//нужен ли this?
        //матриц заполняется
        //returnPermission(n);
    }

    public int getPermission() {
        if (list.size() != 0) {
            Collections.shuffle(list);
            int temp = list.get(0);
            list.remove(temp);
            return temp;
        }
        return -1;
    }

    public void returnPermission(int number) {
        list.add(number);
    }

    public void fillMatrix(int n) {
        matrix.getMatrix()[n][n] = Integer.parseInt(Thread.currentThread().getName());
        //чекай,чтобы имена были парсящиеся. где-нибудь
        //заполняет диагональный элемент

        fillRandomNElement(n);
        //заполняет рандомный недиаганальный элемент в том же столбце или той же строке

        System.out.println(matrix);
    }


    /*
     * метод заполняет рандомный элемент
     * который не диагональный
     * */
    public void fillRandomNElement(int n) {
        int randomNumber = getRandomPlace(n); //получает рандомную !=n ячейку
        boolean isColumn = random.nextBoolean(); //решает строка или столбец

        if (isColumn) {
            matrix.getMatrix()[n][randomNumber] = n;
        } else {
            //рандомное заполнение будет в строке
            matrix.getMatrix()[randomNumber][n] = n;
        }
    }

    /*
     * получает рандомный элемент не равный n  [0...matrix.length-1]
     * */
    private int getRandomPlace(int n) {
        int randomNumber = n;

        while (randomNumber == n) {
            randomNumber = (int) (Math.random() * matrix.getMatrix().length - 1);
        }
        return n;
    }
}