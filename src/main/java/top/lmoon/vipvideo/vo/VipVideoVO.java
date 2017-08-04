/**
 * 
 */
package top.lmoon.vipvideo.vo;

import java.sql.Timestamp;

/**
 * @author LMoon
 * @date 2017年8月4日
 * 
 */
public class VipVideoVO {
	
	private String remark;
	
	private Timestamp ctime;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCtime() {
		return ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	
	

}
