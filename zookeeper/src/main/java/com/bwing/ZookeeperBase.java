package com.bwing;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * zookeeper原生接口api
 */

public class ZookeeperBase {


    Logger logger = Logger.getLogger(ZookeeperBase.class);
    private volatile  ZooKeeper zooKeeper;


    public ZooKeeper getZookeeper() throws IOException {

      return zooKeeper;

    }

    public ZookeeperBase(String connect,int sessionTimeout) throws IOException {
        zooKeeper = new ZooKeeper(connect, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                Event.KeeperState state = watchedEvent.getState();
                String path = watchedEvent.getPath();
                Event.EventType type = watchedEvent.getType();
                if(Event.KeeperState.SyncConnected.equals(state)){
                    if(Event.EventType.None.equals(type)){
                        logger.info("zk建立链接成功");
                    }
                }
            }
        });
    }

    public void closeZookeeper() throws InterruptedException {
        if(zooKeeper != null){
            zooKeeper.close();
            logger.info("zk连接已关闭");
        }
    }


    public String createNode(String path,String data) throws KeeperException, InterruptedException {
        return zooKeeper.create(path,data.getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public String getData(String path) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(path, false, null);
        return new String(data);
    }

    public List<String> getChild(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getChildren(path, false);
    }

    public Stat exists(String path) throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists(path, false);
        return exists;
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String connect = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        ZookeeperBase zookeeperBase = new ZookeeperBase(connect, 5000);
        String path ="/base";
        String node ;
        if(zookeeperBase.exists(path) == null){
            node = zookeeperBase.createNode(path, "emm");
        }else {
            node = path;
        }
//        String childPath1= "/base/child1";
//        String node1 = zookeeperBase.createNode(childPath1,"boy");
//        System.out.println(node1);
//
//        String childPath2 ="/base/child2";
//        String node2 = zookeeperBase.createNode(childPath2,"girl");
//        System.out.println(node2);


        String data = zookeeperBase.getData(path);
        System.out.println(node);
        System.out.println(data);

        List<String> childs = zookeeperBase.getChild(path);
        System.out.println(childs);

        childs.forEach(child ->{
            try {
                System.out.println(zookeeperBase.getData(path+"/"+child));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }



}
