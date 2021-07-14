package com.bwing;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class CuratorZooKeeperDistributedLock {

    private static String connect = "175.27.230.96:2181,175.27.230.96:2182,175.27.230.96:2183";

    private static int count =100;
    private static String lockNode ="/lock_curator";

    //重试策略
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);



    public static void main(String[] args) {




//        try {
//            if(lock.acquire(5, TimeUnit.SECONDS)){
//                //逻辑处理
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                lock.release();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }




        for(int i =0;i < 100;i++){

            new Thread( new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    CuratorFramework client = CuratorFrameworkFactory.newClient(connect,retryPolicy);

                        client.start();

                    InterProcessLock lock = new InterProcessMutex(client,lockNode);
                    try {
                        if(lock.acquire(100, TimeUnit.SECONDS)){
                            count--;
                            System.out.println(count);
                        }else{

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            },"线程" + i).start();
        }

    }

}
