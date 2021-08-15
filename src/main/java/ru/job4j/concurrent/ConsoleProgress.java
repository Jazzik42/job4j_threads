package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] process = new String[]{"-", "\\", "|", "/"};
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
              e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            if (count >= process.length) {
                count = 0;
            }
            System.out.print("\r Loading: " + process[count]);
            count++;
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(
                new ConsoleProgress());
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        thread.interrupt();
    }
}
