package com.bwing;

public class TestZooKeeperDistributedLock {
    public static void main(String[] args) {
        ZooKeeperDistributedLock lock = new SimpleZooKeeperDistributedLock();

        for(int i =0;i < 10;i++){
            new Thread( new Runnable() {
                @Override
                public void run() {
                    lock.getLock();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.releaseLock();
                }
            },"线程" + i).start();
        }

    }
}
