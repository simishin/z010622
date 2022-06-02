package qwr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static qwr.Main.prnq;
import static qwr.Main.prnt;

/*
1)Напишите программу, в которой создаются два потока, которые выводят на консоль свое имя по очереди
2)Два потока разделяют общий буфер данных, размер которого ограничен N. Если буфер пуст, потребитель ждет
пока производитель заполняет буфер полностью. Когда буфер заполнен полностью производитель ждет,
пока потребитель заберет данные и место освободится.
 */
public class Main {
    private static Thread t1f;
    private static Thread t1s;
    public  static boolean  prnq(String s){System.out.println(s); return true;}
    public  static boolean  prnt(String s){System.out.print(s); return true;}
    public static void main(String[] args) {
        Work1 z1 = new Work1();
        z1.task1();
        TaskV.task2();
	// write your code here
    }//main
}//class Main -----------------------------------------------------------------------
class Work1{

    public void task1() {
        prnq("Задание 1");
        Store u = new Store();
        Pr x = new Pr(u);
        Cr y = new Cr(u);
        new Thread(x).start();
        new Thread(y).start();
        prnq("\nЗадание 1$");
    }//task1

    Thread Pw = new Thread(){
//        Store u;
        public void run(){
//            for (int i = 0; i < 12; i++) { u.printB(); }
        }
    };
//
//    Thread t1f = new Thread(()->{
//        prnq("t1f");
//        QrSynxr x = new QrSynxr();
//        while (x.test()) {
//            x.printA();
//        }
//    });
//    Thread t1s = new Thread(()->{
//        prnq("t1s");
//        QrSynxr x = new QrSynxr();
//        while (x.test()) {
//            x.printB();
//        }
//    });

}//Work1-----------------------------------------------------------------------------
class Pr implements Runnable{
    Store u;
    Pr(Store x){ u=x; }
    public void run(){
        for (int i = 0; i < 12; i++) { u.printB(); }
    }
}//Pr
class Cr implements Runnable{
    Store u;
    Cr(Store x){ u=x; }
    public void run(){
        for (int i = 0; i < 12; i++) { u.printA(); }
    }
}//Cr

class Store{
    static int j=0;
    public synchronized void printA(){
        while (j<=0) { try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); } }
        j--;
        prnt("#");
        this.notify();
    }//printA
    public synchronized void printB(){
        while (j>0) { try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); } }
        j++;
        prnt("%");
        this.notify();
    }//printA
}//class QrSynxr ---------------------------------------------------------------------

/**
 * Два потока разделяют общий буфер данных, размер которого ограничен N. Если буфер пуст, потребитель ждет
 * пока производитель заполняет буфер полностью. Когда буфер заполнен полностью производитель ждет,
 * пока потребитель заберет данные и место освободится.
 */
class TaskV{
    protected static List<Integer> arr;

    protected static void task2() {
        prnq("Задание 2");
        final int v=3; //объем буфера
        final int z=22;
        arr = new ArrayList<>(v);
        AtomicBoolean q = new AtomicBoolean(true);

        Thread Tinc = new Thread(()->{
            synchronized (arr) {
                for (int i = 0; i < z; i++) {
                    prnt("\n" + i);

                    while (arr.size() > v) {
                        try {
                            arr.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    arr.add(i);
                    prnt(" +");
                    if (arr.size() > v ) arr.notifyAll();

                }//for
                q.set(false);
                prnq("=");
                arr.notifyAll();
            }
        });

        Thread Tdec = new Thread(()->{
            while (true){
                synchronized (arr){
                    if (q.get()) try { arr.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                    else { prnq(" $"); break; }
                    while (!arr.isEmpty()) {
                        arr.remove(0);
                        prnt(" -");
                    }
                    arr.notifyAll();
//                    if (!q.get()) { prnq(" @"); break; }
                }
            }//while
        });
        Tdec.start();
        Tinc.start();
    }//task2
}//TaskV
