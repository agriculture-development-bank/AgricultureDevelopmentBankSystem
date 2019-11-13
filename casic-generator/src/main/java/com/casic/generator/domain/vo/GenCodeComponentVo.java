package com.casic.generator.domain.vo;

import com.casic.generator.domain.GenCodeComponent;
import com.casic.generator.domain.GenCodeDomain;
import com.casic.system.domain.SysUser;

public class GenCodeComponentVo extends GenCodeComponent {
    /**
     * 所属领域id
     */
    public String domainId;

    /**
     * 所属领域
     */
    public GenCodeDomain domain;

    /**
     * 创建人
     */
    public SysUser createUser;

    /**
     * 更新人
     */
    public SysUser updateUser;

    /**
     * 关联表gen_code_domain_component id(添加是需要)
     */
    public String domainComponentId;

    public GenCodeDomain getDomain() {
        return domain;
    }

    public void setDomain(GenCodeDomain domain) {
        this.domain = domain;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public SysUser getCreateUser() {
        return createUser;
    }

    public void setCreateUser(SysUser createUser) {
        this.createUser = createUser;
    }

    public SysUser getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(SysUser updateUser) {
        this.updateUser = updateUser;
    }

    public String getDomainComponentId() {
        return domainComponentId;
    }

    public void setDomainComponentId(String domainComponentId) {
        this.domainComponentId = domainComponentId;
    }
}
