package org.flywind.widgets.test.entities.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.flywind.widgets.test.common.constants.FBaseConstants;



/**
 * <p>实体基类，所有实体类都继承自该基类</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@MappedSuperclass
public abstract class FBase implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 9169367643882940766L;
	
	/**
	 * 对象唯一标识(主键)
	 */
	private Long id;
	
	/**
	 * 是否显示
	 */
	private Boolean isOpen = Boolean.TRUE;
	
	/**
	 * 客户代码
	 */
	private String customerCode;
	
	/**
	 * 创建人
	 */
	private String creater;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后更新人
	 */
	private String lastUpdatePerson;

	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	public Long getId(){
		return id;
	}
	
	/*@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		if (!StringUtils.isBlank(this.id)) {
			return this.id;
		}
		return UUID.randomUUID().toString();
	}*/
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "creater", length = 36)
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 7)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_update_person", length = 36)
	public String getLastUpdatePerson() {
		return lastUpdatePerson;
	}

	public void setLastUpdatePerson(String lastUpdatePerson) {
		this.lastUpdatePerson = lastUpdatePerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_time", length = 7)
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "is_open", length = 1)
	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	@Column(name = "customer_code", nullable = false, length = 36)
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	/**
	 * 初始化实例的基础信息
	 * 
	 * @param userName 
	 *        登录用户名
	 */
	public void initBaseInfo(String userName) {
		if (null != userName) {
			this.setCreater(userName);
			this.setLastUpdatePerson(userName);
		}
		if (null == this.getCustomerCode()) {
			this.setCustomerCode(FBaseConstants.EMPTY_STRING);
		}
		Date currTime = new Date();
		this.setCreateTime(currTime);
		this.setLastUpdateTime(currTime);
	}
	
	/**
	 * 初始化实例的基础信息
	 * 
	 * @param userName
	 *        登录用户名
	 * @param customerCode
	 *        客户代码
	 */
	public void initBaseInfo(String userName, String customerCode) {
		if (null != customerCode) {
			this.setCustomerCode(customerCode);
		}
		this.initBaseInfo(userName);
	}
	
}
