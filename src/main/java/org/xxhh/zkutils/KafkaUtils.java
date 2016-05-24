package org.xxhh.zkutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xxhh.zkutils.model.BrokerInfo;
import org.xxhh.zkutils.utils.ZK;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.OffsetRequest;


/**
 * Created by yanqin.yang on 2016/5/23.
 */
public class KafkaUtils {

    public static final Logger LOG = LoggerFactory.getLogger(KafkaUtils.class);
    private String _zkPath="";


    /*
    * 取得kafka 对应的topic list
    * */
    public static List<String> getTopicsFromZK(CuratorFramework client) {

        return ZK.getNodesChildrenNode(client,ZK.BrokerTopicsPath);
    }

    /*
    * 取得kafka对应的broker信息
    * */
    public static HashMap<Integer,BrokerInfo> getBrokerInfos( CuratorFramework client) throws Exception {

        HashMap<Integer,BrokerInfo>result = new HashMap<Integer,BrokerInfo>();

        List<String> borkers = client.getChildren().forPath(ZK.BrokerIdsPath);
        for(String broker:borkers){
            String  brokerPath = ZK.BrokerIdsPath + "/" + broker;
            String brokerInfo  =  ZK.getData(client,brokerPath);
            JSONObject jsonObject = JSON.parseObject(brokerInfo);
            BrokerInfo brokerObject = new BrokerInfo(Integer.valueOf(broker),jsonObject.get("host").toString(),Integer.valueOf(jsonObject.get("port").toString()));
            result.put(brokerObject.getId(),brokerObject);
        }
        return result;
    }

    /*
    * 取得consumeGroup的值
    * */
    public static List<String> getConsumeGroupName(CuratorFramework client){

        return  ZK.getNodesChildrenNode(client,ZK.ConsumersPath);
    }

    public static int getLeaderFor( CuratorFramework client,String topicName,HashMap<Integer,BrokerInfo> brokers) {

        List<HashMap<String,BrokerInfo>> result  = new ArrayList<HashMap<String,BrokerInfo>>();
        try {
            String topicBrokersPath = ZK.BrokerTopicsPath + "/" + topicName + "/partitions";

            List<String> patitions = ZK.getNodesChildrenNode(client,topicBrokersPath);

            for(String partition : patitions){
                String key = topicName + "." + partition;
                HashMap<String,BrokerInfo> data = new HashMap<String,BrokerInfo>();
                byte[] hostPortData = client.getData().forPath(topicBrokersPath + "/" + partition + "/state");
                JSONObject value = JSON.parseObject(new String(hostPortData, "UTF-8"));
                Integer leader = ((Number) value.get("leader")).intValue();
                if (leader == -1) {
                    throw new RuntimeException("No leader found for partition " + partition);
                }
                BrokerInfo broker = brokers.get(leader);

                String leadBroker = broker.getHost();
                String clientName = "Client_" + topicName + "_" + partition;

                SimpleConsumer consumer = new SimpleConsumer(leadBroker, broker.getPort(), 100000, 64 * 1024, clientName);

                long earliestOffSet = getLastOffset(consumer,topicName,Integer.valueOf(partition),kafka.api.OffsetRequest.EarliestTime(), clientName);
                long lastOffSet= getLastOffset(consumer,topicName,Integer.valueOf(partition),kafka.api.OffsetRequest.LatestTime(), clientName);

                LOG.info("topic name is {}, partition is {}, leader is {}, EarliestTime offset is {}," +
                        "LatestTime offset is {}",topicName,partition,brokers.get(leader),earliestOffSet,lastOffSet);
            }
            return 1;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * get topic leader value
    * */
    public  static void getLeaderForTopic(CuratorFramework client) throws Exception {
        List<String> topics = getTopicsFromZK(client);
        HashMap<Integer,BrokerInfo> brokers = getBrokerInfos(client);
        for(String topic:topics){

            getLeaderFor(client,topic,brokers);
        }
    }

    private static long getLastOffset(SimpleConsumer consumer, String topic, int partition, long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        kafka.javaapi.OffsetResponse response = consumer.getOffsetsBefore(request);
        if (response.hasError()) {
            LOG.error("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition));
            return 0;
        }
        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }

    /*
    * 取得consume group list 对应的topic name
    * */
    public  static void getConsumeGroupsOffset(CuratorFramework client){
        List<String> groups = getConsumeGroupName(client);

        for(String group:groups){
            getConsumeGroupTopicName(client,group);
        }

    }

    /*
    * 取得consume group 对应的topic name
    * */
    private static String getConsumeGroupTopicName(CuratorFramework client,String groupName){
        String consumePath = ZK.ConsumersPath + "/" + groupName + "/offsets" ;
        List<String> topics = ZK.getNodesChildrenNode(client,consumePath);

        for(String topicName: topics){
            String topicBrokersPath = ZK.BrokerTopicsPath + "/" + topicName + "/partitions";
            List<String> partitions = ZK.getNodesChildrenNode(client,topicBrokersPath);
            for(String partition:partitions){
                long offset = getConsumeGroupOffset(client,groupName,topicName,partition);

                LOG.info("Kafka group name is {}, topic name is {}, patition is {}," +
                        " consume offset is {}",groupName,topicName,partition,offset);
            }

        }

        return "";
    }

    /*
    *get  /consumers/jd-group/offsets/data_clean_target_topic/0
    *26203
    * 取得consume group，中 topic ,partition 对应消费到offset的信息
    * */
    private static  long getConsumeGroupOffset(CuratorFramework client,String groupName, String topicName,String partition){

        String offsetPath = ZK.ConsumersPath + "/" + groupName + "/offsets/" + topicName + "/" + partition;
        String offsetValue = ZK.getData(client,offsetPath);
        return Long.valueOf(offsetValue);
    }


    /*
    * get /datacleantestoffset/kafkastorm-data_clean_source_topic/partition_0
    *{"topology":{"id":"cce7bae4-3d60-434b-a4d9-63932cf47264","name":"datacleanjobstest"},"offset":95199,"partition":0,"broker":{"host":"10.199.196.43","port":9092},"topic":"data_clean_source_topic"}
    * */




    /*public static String partitionPath(String toipcName) {
        return _zkPath + "/topics/" + toipcName + "/partitions";
    }

    public String brokerPath() {
        return _zkPath + "/ids";
    }*/



}
