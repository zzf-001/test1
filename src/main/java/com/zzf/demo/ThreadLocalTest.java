package com.zzf.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalTest {
    private static final AtomicInteger nextId = new AtomicInteger(0);

    private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>()
    {
      protected Integer initialValue(){
          return nextId.getAndIncrement();
      }
    };

    public static int get(){
        return threadId.get();
    }

    public static void remove(){
        threadId.remove();
    }
    public static void incrementSameThreadId(){
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread()+"_" + threadId + ThreadLocalTest.get());

            }
        }finally {
            ThreadLocalTest.remove();
        }
    }

    public static void main(String[] args) {
        incrementSameThreadId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                incrementSameThreadId();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                incrementSameThreadId();
            }
        }).start();
    }

}
