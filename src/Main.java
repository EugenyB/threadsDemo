//Thread Demo
public class Main {
    double totalSum = 0;
    int finished = 0;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        long start = System.currentTimeMillis();
//        double s = IntegralCalculator
//                .leftRectangles(0, Math.PI, 1000_000_000, Math::sin);
//        System.out.println(s);
//        System.out.println(finish - start);
        double a = 0;
        double b = Math.PI;
        int n = 1_000_000_000;
        int nThreads = 20;
        double delta = (b - a) / nThreads;
        for (int i = 0; i < nThreads; i++) {
            ThreadCalculator calculator = new ThreadCalculator(a + i * delta, a + (i + 1) * delta, n / nThreads, Math::sin, this);
            new Thread(calculator, "T:"+(i+1)).start();
        }
        synchronized (this) {
            try {
                while (finished < nThreads) {
                    wait();
                }
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println(totalSum);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
    }

    public synchronized void sendResult(double v) {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());
        totalSum += v;
        finished++;
        notify();
    }
}
