package com.bwing;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HighZooKeeperDistributedLock extends ZooKeeperDistributedLock{



    private String currentPath;

    private String prePath;

    private String PATH = "/lock_high";

    private static String SYMBOL ="/";

    private CountDownLatch countDownLatch;

    public HighZooKeeperDistributedLock(){
        if(!zkClient.exists(PATH)){
           zkClient.createPersistent(PATH);
        }
    }


    @Override
    public void releaseLock() {
        if (zkClient != null){
            zkClient.delete(currentPath);
            zkClient.close();
        }
    }

    @Override
    public boolean tryLock() {
        if(currentPath == null |"".equals(currentPath)){
            currentPath = zkClient.createEphemeralSequential(PATH+SYMBOL+currentPath,"lock");
        }

        //获取子节点时，会有并发问题么？
        List<String> children = zkClient.getChildren(PATH);
        Collections.sort(children);
        if(currentPath.equals(PATH+SYMBOL+children.get(0))){
            return true;
        }
        //找到前一个临时结点
        int index = Collections.binarySearch(children, currentPath.substring(PATH.length() + 1));
        prePath = PATH+SYMBOL+children.get(index-1);

        return false;

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
        zkClient.subscribeDataChanges(prePath,listener);
        if(zkClient.exists(prePath)){
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(prePath,listener);

    }
}



