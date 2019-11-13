package org.casic.workflow.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;

/**
 * 流程模型表 act_de_model
 * 
 * @author yuzengwen
 * @date 2019-04-03
 */
public class ActDeModel extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** id */
	private String id;
	/** 名称 */
	private String name;
	/** key */
	private String modelKey;
	/** 描述 */
	private String description;
	/**  */
	private String modelComment;
	/**  */
	private Date created;
	/**  */
	private String createdBy;
	/**  */
	private Date lastUpdated;
	/**  */
	private String lastUpdatedBy;
	/**  */
	private Integer version;
	/**  */
	private String modelEditorJson;
	/**  */
	private byte[] thumbnail;
	/**  */
	private Integer modelType;
	/**  */
	private String tenantId;

	private ActProcessType processType;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setModelKey(String modelKey) 
	{
		this.modelKey = modelKey;
	}

	public String getModelKey() 
	{
		return modelKey;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public void setModelComment(String modelComment) 
	{
		this.modelComment = modelComment;
	}

	public String getModelComment() 
	{
		return modelComment;
	}
	public void setCreated(Date created) 
	{
		this.created = created;
	}

	public Date getCreated() 
	{
		return created;
	}
	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}

	public String getCreatedBy() 
	{
		return createdBy;
	}
	public void setLastUpdated(Date lastUpdated) 
	{
		this.lastUpdated = lastUpdated;
	}

	public Date getLastUpdated() 
	{
		return lastUpdated;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) 
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedBy() 
	{
		return lastUpdatedBy;
	}
	public void setVersion(Integer version) 
	{
		this.version = version;
	}

	public Integer getVersion() 
	{
		return version;
	}
	public void setModelEditorJson(String modelEditorJson) 
	{
		this.modelEditorJson = modelEditorJson;
	}

	public String getModelEditorJson() 
	{
		return modelEditorJson;
	}
	public void setThumbnail(byte[] thumbnail)
	{
		this.thumbnail = thumbnail;
	}

	public byte[] getThumbnail()
	{
		return thumbnail;
	}
	public void setModelType(Integer modelType) 
	{
		this.modelType = modelType;
	}

	public Integer getModelType() 
	{
		return modelType;
	}
	public void setTenantId(String tenantId) 
	{
		this.tenantId = tenantId;
	}

	public String getTenantId() 
	{
		return tenantId;
	}

	public ActProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ActProcessType processType) {
		this.processType = processType;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("modelKey", getModelKey())
            .append("description", getDescription())
            .append("modelComment", getModelComment())
            .append("created", getCreated())
            .append("createdBy", getCreatedBy())
            .append("lastUpdated", getLastUpdated())
            .append("lastUpdatedBy", getLastUpdatedBy())
            .append("version", getVersion())
            .append("modelEditorJson", getModelEditorJson())
            .append("thumbnail", getThumbnail())
            .append("modelType", getModelType())
            .append("tenantId", getTenantId())
            .toString();
    }
}
