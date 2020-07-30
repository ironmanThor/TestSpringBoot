package com.example.springboot.datafill.metadata;

import java.lang.reflect.Field;
import lombok.Data;

/**
 * 字段填充元数据信息，最终这些数据将可以完成一个填充操作
 * @author szz
 */
@Data
public class DataFillMetadata {

    /**
     * 填充字段
     */
    private Field fillField;

    /**
     * 填充对象
     */
    private Object fillObj;

    /**
     * 查询条件
     */
    private Object selectionKey;

    public Field getFillField() {
        return fillField;
    }

    public void setFillField(Field fillField) {
        this.fillField = fillField;
    }

    public Object getFillObj() {
        return fillObj;
    }

    public void setFillObj(Object fillObj) {
        this.fillObj = fillObj;
    }

    public Object getSelectionKey() {
        return selectionKey;
    }

    public void setSelectionKey(Object selectionKey) {
        this.selectionKey = selectionKey;
    }
}
