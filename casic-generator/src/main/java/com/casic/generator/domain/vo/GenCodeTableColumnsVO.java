package com.casic.generator.domain.vo;

import com.casic.generator.domain.GenCodeTableColumns;

public class GenCodeTableColumnsVO extends GenCodeTableColumns {

    private String fkTableValue;

    private String fkFieldValue;

    public String getFkTableValue() {
        return fkTableValue;
    }

    public void setFkTableValue(String fkTableValue) {
        this.fkTableValue = fkTableValue;
    }

    public String getFkFieldValue() {
        return fkFieldValue;
    }

    public void setFkFieldValue(String fkFieldValue) {
        this.fkFieldValue = fkFieldValue;
    }
}
