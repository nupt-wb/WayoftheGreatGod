package com.bwing;


import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class SimpleZooKeeperDistributedLock extends ZooKeeperDistributedLock {


    private final static String SIMPLE_LOCK_PATH ="/lock_simple";

    private CountDownLatch countDownLatch;

    @Override
    public void releaseLock() {
        if(zkClient == null){
            return;
        }
        zkClient.delete(SIMPLE_LOCK_PATH);
        //zkClient.close();
        System.out.println(Thread.currentThread().getName()+"释放锁成功");
    }

    @Override
    public boolean tryLock() {
        if(zkClient == null){
            System.out.println("连接为空");
            return false;
        }
        try {
            zkClient.createEphemeral(SIMPLE_LOCK_PATH);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void waitLock() {
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if(countDownLatch != null){
                    countDownLatch.countDown();
                }
            }
        };

        zkClient.subscribeDataChanges(SIMPLE_LOCK_PATH,listener);
        if(zkClient.exists(SIMPLE_LOCK_PATH)){
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName()+"进入等待锁状态");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(SIMPLE_LOCK_PATH,listener);
    }
}
