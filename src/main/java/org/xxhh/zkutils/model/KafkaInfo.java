package org.xxhh.zkutils.model;

import java.util.List;

/**
 * Created by yanqin.yang on 2016/5/23.
 * name: String, brokers: Seq[BrokerInfo], offsets: Seq[OffsetInfo]
 */
public class KafkaInfo {

    private String  name;
    private List<BrokerInfo> brokers;
    private List<OffsetInfo> offsets;

    public KafkaInfo(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrokers(List<BrokerInfo> brokers) {
        this.brokers = brokers;
    }

    public void setOffsets(List<OffsetInfo> offsets) {
        this.offsets = offsets;
    }

    public String getName() {
        return name;
    }

    public List<BrokerInfo> getBrokers() {
        return brokers;
    }

    public List<OffsetInfo> getOffsets() {
        return offsets;
    }
}
