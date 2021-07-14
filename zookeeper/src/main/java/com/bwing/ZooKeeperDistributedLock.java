package com.bwing;

import org.I0Itec.zkclient.ZkClient;

public  abstract class ZooKeeperDistributedLock {

    protected ZkClient zkClient = new ZkClient("175.27.230.96:2181,175.27.230.96:2182,175.27.230.96:2183",500000);

    /**
     * 获取锁
     */
    public  void getLock(){
        String name = Thread.currentThread().getName();
        if(tryLock()){
           // System.out.println("线程"+name+"获取到了锁");
        }else{
          //  System.out.println("线程"+name+"获取锁失败");
            waitLock();
            getLock();
        }
    }

    /**
     * 释放锁
     */
    public abstract void releaseLock();

    /**
     * 尝试获取锁
     */
    public abstract boolean tryLock();

    /**
     * 等待锁
     */
    public abstract void waitLock();

}
