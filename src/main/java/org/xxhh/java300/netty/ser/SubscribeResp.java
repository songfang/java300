package org.xxhh.java300.netty.ser;

import java.io.Serializable;

/*
 * 消息定义
 */
public class SubscribeResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer subReqId;
	private Integer respCode;
	private String desc;
	
	public Integer getSubReqId() {
		return subReqId;
	}
	public void setSubReqId(Integer subReqId) {
		this.subReqId = subReqId;
	}
	public Integer getRespCode() {
		return respCode;
	}
	public void setRespCode(Integer respCode) {
		this.respCode = respCode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "SubscribeResp [subReqId=" + subReqId + ", respCode=" + respCode
				+ ", desc=" + desc + "]";
	}

	
	
	
}
