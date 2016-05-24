package org.xxhh.zkutils.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by yanqin.yang on 2016/5/23.
 */
public class ZK {

    public static final Logger LOG = LoggerFactory.getLogger(ZK.class);
    public static String ConsumersPath = "/consumers";
    public static String BrokerIdsPath = "/brokers/ids";
    public static String BrokerTopicsPath = "/brokers/topics";
    public static String ControllerPath = "/controller";
    public static String ControllerEpochPath = "/controller_epoch";
    public static String ReassignPartitionsPath = "/admin/reassign_partitions";
    public static String PreferredReplicaLeaderElectionPath = "/admin/preferred_replica_election";

    private String zkAddress;

    private static CuratorFramework client;

    public ZK(String zkAddress) {
        this.zkAddress = zkAddress;
        client = CuratorFrameworkFactory.newClient(zkAddress, new RetryNTimes(10, 5000));
        client.start();
    }
    public CuratorFramework getClient() {
        return client;
    }

    /*
    * 取得path node节点对应的值
    * */
    public static String getData(CuratorFramework newClient, String path){



        try {
            if(existedNode(newClient,path)){
                return new String(newClient.getData().forPath(path));
            }else{
                LOG.warn("path {} not existed in zoomkeeper", path);
            }

        } catch (Exception e) {
            System.out.println("获取数据失败, elog=" + e.getMessage());
        }
        return null;
    }

    /*
    * 取得path节点下一级对应的节点的名称
    * */
    public static List<String> getNodesChildrenNode(CuratorFramework newClient, String path){
        List<String> values = null;
        try {
            if(existedNode(newClient,path)){
                values = newClient.getChildren().forPath(path);
            }else{
                LOG.warn("path {} not existed in zoomkeeper", path);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(values==null){
            return Collections.emptyList();
        }
        return values;
    }

    /*
    * check node 是否存在
    * */
    public static  boolean existedNode(CuratorFramework zk, String path) throws Exception{
        Stat stat = zk.checkExists().forPath(path);
        return stat != null;
    }
}
