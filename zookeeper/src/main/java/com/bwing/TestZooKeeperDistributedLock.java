package com.bwing;

public class TestZooKeeperDistributedLock {
    public static void main(String[] args) {


        for(int i =0;i < 10;i++){
            new Thread( new Runnable() {
                @Override
                public void run() {
                    ZooKeeperDistributedLock lock = new SimpleZooKeeperDistributedLock();
                    lock.getLock();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.releaseLock();
                }
            },"线程" + i).start();
        }

    }
}
