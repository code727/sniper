package org.sniper.kafka.test;

import java.util.Map;

import org.sniper.commons.entity.IdEntity;

public class EventMessage extends IdEntity<String> {

	private static final long serialVersionUID = 6834085174240454027L;
	
	/** 业务名称 */
	private String name;
	
	/** 事件类型*/
	private String type;
	
	/** 状态 */
	private int status;
	
	/** 事件业务明细 */
	private Map<String, Object> detail;
	
	/** 版本号 */
	private long version;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Map<String, Object> getDetail() {
		return detail;
	}

	public void setDetail(Map<String, Object> detail) {
		this.detail = detail;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
}