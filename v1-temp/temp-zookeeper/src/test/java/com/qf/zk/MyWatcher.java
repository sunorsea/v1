package com.qf.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/3
 */
public class MyWatcher implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("监听的节点发生了变化,收到变化通知.....");
        System.out.println("发生变化的节点"+watchedEvent.getPath());
        System.out.println("发生什么变化"+watchedEvent.getType());
    }
}
