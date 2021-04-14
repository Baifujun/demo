package com.baixs.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

class ZooKeeperTest {

    @Test
    void contextLoads() throws IOException, InterruptedException, KeeperException {
        String connStr = "192.168.0.110:2181,192.168.0.110:2182,192.168.0.110:2183";
        CountDownLatch countDown = new CountDownLatch(1);
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    if (event.getType() == Event.EventType.None) {
                        System.out.println("eventType:None");
                        countDown.countDown();
                    } else if (event.getType() == Event.EventType.NodeCreated) {
                        System.out.println("listen:节点创建");
                    } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        System.out.println("listen:子节点修改");
                    } else if (event.getType() == Event.EventType.NodeDataChanged) {
                        System.out.println("listen:节点被修改");
                    } else if (event.getType() == Event.EventType.NodeDeleted) {
                        System.out.println("listen:节点被删除");
                    }
                }
            }
        };

        ZooKeeper zookeeper = new ZooKeeper(connStr, 5000, watcher);
        countDown.await();

        System.out.println(zookeeper.exists("/test", watcher));

        // 创建节点
        String znode = zookeeper.create("/test", "一生一世".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(znode);
        Thread.sleep(10);

        Stat stat = new Stat();

        // 获取节点
        byte[] bs = zookeeper.getData(znode, true, stat);
        System.out.println("创建节点后的数据是:" + new String(bs));
        System.out.println("创建节点后的state:" + stat);

        // 修改节点
        System.out.println(zookeeper.setData(znode, "I love you".getBytes(), -1));
        Thread.sleep(10);

        bs = zookeeper.getData(znode, true, stat);
        System.out.println("修改节点后的数据是:" + new String(bs));
        System.out.println("修改节点后的state:" + stat);

        // 删除节点
        zookeeper.delete(znode, -1);
        System.out.println("节点删除成功");
        Thread.sleep(10);
    }

}
