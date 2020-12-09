package com.yxd.core.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * @Description：JDK类编辑器
 * @Date 2020/12/09 21:36
 * @Author YXD
 * @Version 1.0
 */
public class PropertyEditorUtil {
    /**
     * 基本类型处理
     *
     * @param targetType
     * @param text
     * @return
     */
    public static Object convert(Class<?> targetType, String text) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(text);
        return editor.getValue();
    }
}
