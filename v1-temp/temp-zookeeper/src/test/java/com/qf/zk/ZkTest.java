package com.qf.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/3
 */
public class ZkTest {

    @Test
    public void connectionTest() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        System.out.println(zooKeeper);
    }

    @Test
    public void createTest() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        String result = zooKeeper.create("/mynode", "111".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(result);
    }

    @Test
    public void getStatTest() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        Stat stat = zooKeeper.exists("/mynode", false);
        System.out.println(stat.getCzxid());
        System.out.println(stat.getCtime());
        System.out.println(stat.getDataLength());
    }

    @Test
    public void getDateTest() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        byte[] data = zooKeeper.getData("/mynode", false, null);
        System.out.println(new String(data));
    }

    @Test
    public void setDataTest() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        Stat stat = zooKeeper.setData("/mynode", "9999".getBytes(), 0);
        System.out.println(stat.getCzxid());
    }

    @Test
    public void delTest() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        zooKeeper.delete("/mynode",-1);
    }

    @Test
    public void existsWatcherTest() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        zooKeeper.exists("/newnode", new MyWatcher());
        Thread.sleep(600000);
    }
    @Test
    public void getDatarTest() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        zooKeeper.getData("/newnode", new MyWatcher(), null);
        Thread.sleep(600000);
    }

    @Test
    public void getChildrenTest() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.36.147.81:2181", 100, null);
        zooKeeper.getChildren("/parent", new MyWatcher());
        Thread.sleep(600000);
    }


}
