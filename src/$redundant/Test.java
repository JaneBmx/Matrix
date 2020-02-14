package $redundant;

import entity.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(5);

        ExecutorService es = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 5; i++) {
            es.submit(new ThreadBleat(i, cdl));
        }
        es.shutdown();
        System.out.println("Первая точка");
        cdl.await();

        System.out.println("Вторая точка");

        System.out.println("Бля, конец главного потока, ура!");
    }
}

class ThreadBleat implements Runnable {
    CountDownLatch countDownLatch;
    static Random random = new Random();
    static ArrayList<Integer> list;

    //static Matrix matrix;
    static volatile int[][] matrix;
    int num;
    static int place = 0;

    static {
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        //matrix = new Matrix(5);
        matrix = new int[5][5];
    }

    ThreadBleat(int n, CountDownLatch cdl) {
        countDownLatch = cdl;
        num = getPermission();
        Thread.currentThread().setName(Integer.toString(n));
    }

    //    public void run() {
//        System.out.println(Thread.currentThread().getName()+" запустил run");
////        int n = -1;
////        while (n == -1) {//пробуем пока нет свободных "разрешений"
////            n = getPermission();
////        }
//        //как только получили - продолжаем
//        //int n = getPermission();
//
//        this.fillMatrix(num);//нужен ли this?
//        System.out.println(Thread.currentThread().getName()+" фил матрицу");
//        //матриц заполняется
//        //returnPermission(n);
//
//        System.out.println(matrix);
//        System.out.println(Thread.currentThread().getName()+" вывел матрицу");
//    }
    @Override
    public void run() {
        int permission = getPermission();
        Thread.currentThread().setName(String.valueOf(place++));
//        matrix.getMatrix()[permission][permission] = Integer.parseInt(Thread.currentThread().getName());
        matrix[permission][permission] = Integer.parseInt(Thread.currentThread().getName());
        int position = getRandomPlace(permission);
        boolean isColumn = random.nextBoolean();
        if (isColumn) {
            //matrix.setElement(permission, position, Integer.parseInt(Thread.currentThread().getName()));
            matrix[permission][position] = Integer.parseInt(Thread.currentThread().getName());
        } else {
            //matrix.setElement(position, permission, Integer.parseInt(Thread.currentThread().getName()));
            matrix[position][permission] = Integer.parseInt(Thread.currentThread().getName());
        }
        //System.out.println(Arrays.toString(matrix));
        printMatrix();
        countDownLatch.countDown();
    }

    public void printMatrix(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {
                sb = sb.append(matrix[i][k]).append(" ");
            }
            sb = sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public int getPermission() {
        if (list.size() != 0) {
            Collections.shuffle(list);
            int temp = list.get(0);
            list.remove(0);
            return temp;
        }
        return -1;
    }

    public void returnPermission(int number) {
        list.add(number);
    }

//    public void fillMatrix(int n) {
//        matrix.getMatrix()[n][n] = Integer.parseInt(Thread.currentThread().getName());
//        //чекай,чтобы имена были парсящиеся. где-нибудь
//        //заполняет диагональный элемент
//
//        fillRandomNElement(n);
//        //заполняет рандомный недиаганальный элемент в том же столбце или той же строке
//    }

    /*
     * метод заполняет рандомный элемент
     * который не диагональный
     * */
//    public void fillRandomNElement(int n) {
//        int threadName = Integer.parseInt(Thread.currentThread().getName());
//        int randomNumber = getRandomPlace(n); //получает рандомную !=n ячейку
//        boolean isColumn = random.nextBoolean(); //решает строка или столбец
//
//        if (isColumn) {
//            matrix.setElement(n, randomNumber, threadName);
//            //matrix.getMatrix()[n][randomNumber] = threadName;
//        } else {
//            //рандомное заполнение будет в строке
//            matrix.setElement(randomNumber, n, threadName);
//            //matrix.getMatrix()[randomNumber][n] = threadName;
//        }
//    }

    /*
     * получает рандомный элемент не равный n  [0...matrix.length-1]
     * */
    private int getRandomPlace(int n) {
        int randomNumber = n;
        while (randomNumber == n) {
//            randomNumber = (int) (Math.random() * matrix.getMatrix().length - 1);
            randomNumber = (int) (Math.random() * matrix.length - 1);
        }
        return randomNumber;
    }
}