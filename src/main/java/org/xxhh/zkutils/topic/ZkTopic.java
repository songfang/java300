package org.xxhh.zkutils.topic;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.Collections;
import java.util.List;

/**
 * Created by yanqin.yang on 2016/5/20.
 */
public class ZkTopic {
    private String zkAddress;

    public ZkTopic(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public List<String> getTopicsFromZK() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, new RetryNTimes(10, 5000));
        client.start();
        List<String> topics = client.getChildren().forPath("/brokers/topics");
        client.close();
        if(topics==null){
            return Collections.emptyList();
        }
        return topics;
    }
}
