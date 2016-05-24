package org.xxhh.zkutils.model;

import java.util.Date;

/**
 * Created by yanqin.yang on 2016/5/23.
 * group: String,
 topic: String,
 partition: Int,
 offset: Long,
 logSize: Long,
 owner: Option[String],
 creation: Time,
 modified: Time
 */
public class OffsetInfo {

    private String group;
    private String topic;
    private int partition;
    private long offset;
    private long logSize;
    private String owner;
    private Date creationTime;
    private Date modifiedTime;

    public void setGroup(String group) {
        this.group = group;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setLogSize(long logSize) {
        this.logSize = logSize;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getGroup() {
        return group;
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }

    public long getLogSize() {
        return logSize;
    }

    public String getOwner() {
        return owner;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }
}
