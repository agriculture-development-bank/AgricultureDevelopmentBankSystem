package org.casic.workflow.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;

/**
 * 流程部署表 act_re_procdef
 * 
 * @author yuzengwen
 * @date 2019-04-08
 */
public class ActReProcdef extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/**  */
	private Integer rev;
	/**  */
	private String category;
	/**  */
	private String name;
	/**  */
	private String key;
	/**  */
	private Integer version;
	/**  */
	private String deploymentId;
	/**  */
	private String resourceName;
	/**  */
	private String dgrmResourceName;
	/**  */
	private String description;
	/**  */
	private Integer hasStartFormKey;
	/**  */
	private Integer hasGraphicalNotation;
	/**  */
	private Integer suspensionState;
	/**  */
	private String tenantId;
	/**  */
	private String engineVersion;
	/**  */
	private String derivedFrom;
	/**  */
	private String derivedFromRoot;
	/**  */
	private Integer derivedVersion;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setRev(Integer rev) 
	{
		this.rev = rev;
	}

	public Integer getRev() 
	{
		return rev;
	}
	public void setCategory(String category) 
	{
		this.category = category;
	}

	public String getCategory() 
	{
		return category;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setKey(String key) 
	{
		this.key = key;
	}

	public String getKey() 
	{
		return key;
	}
	public void setVersion(Integer version) 
	{
		this.version = version;
	}

	public Integer getVersion() 
	{
		return version;
	}
	public void setDeploymentId(String deploymentId) 
	{
		this.deploymentId = deploymentId;
	}

	public String getDeploymentId() 
	{
		return deploymentId;
	}
	public void setResourceName(String resourceName) 
	{
		this.resourceName = resourceName;
	}

	public String getResourceName() 
	{
		return resourceName;
	}
	public void setDgrmResourceName(String dgrmResourceName) 
	{
		this.dgrmResourceName = dgrmResourceName;
	}

	public String getDgrmResourceName() 
	{
		return dgrmResourceName;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public void setHasStartFormKey(Integer hasStartFormKey) 
	{
		this.hasStartFormKey = hasStartFormKey;
	}

	public Integer getHasStartFormKey() 
	{
		return hasStartFormKey;
	}
	public void setHasGraphicalNotation(Integer hasGraphicalNotation) 
	{
		this.hasGraphicalNotation = hasGraphicalNotation;
	}

	public Integer getHasGraphicalNotation() 
	{
		return hasGraphicalNotation;
	}
	public void setSuspensionState(Integer suspensionState) 
	{
		this.suspensionState = suspensionState;
	}

	public Integer getSuspensionState() 
	{
		return suspensionState;
	}
	public void setTenantId(String tenantId) 
	{
		this.tenantId = tenantId;
	}

	public String getTenantId() 
	{
		return tenantId;
	}
	public void setEngineVersion(String engineVersion) 
	{
		this.engineVersion = engineVersion;
	}

	public String getEngineVersion() 
	{
		return engineVersion;
	}
	public void setDerivedFrom(String derivedFrom) 
	{
		this.derivedFrom = derivedFrom;
	}

	public String getDerivedFrom() 
	{
		return derivedFrom;
	}
	public void setDerivedFromRoot(String derivedFromRoot) 
	{
		this.derivedFromRoot = derivedFromRoot;
	}

	public String getDerivedFromRoot() 
	{
		return derivedFromRoot;
	}
	public void setDerivedVersion(Integer derivedVersion) 
	{
		this.derivedVersion = derivedVersion;
	}

	public Integer getDerivedVersion() 
	{
		return derivedVersion;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("rev", getRev())
            .append("category", getCategory())
            .append("name", getName())
            .append("key", getKey())
            .append("version", getVersion())
            .append("deploymentId", getDeploymentId())
            .append("resourceName", getResourceName())
            .append("dgrmResourceName", getDgrmResourceName())
            .append("description", getDescription())
            .append("hasStartFormKey", getHasStartFormKey())
            .append("hasGraphicalNotation", getHasGraphicalNotation())
            .append("suspensionState", getSuspensionState())
            .append("tenantId", getTenantId())
            .append("engineVersion", getEngineVersion())
            .append("derivedFrom", getDerivedFrom())
            .append("derivedFromRoot", getDerivedFromRoot())
            .append("derivedVersion", getDerivedVersion())
            .toString();
    }
}
