package org.flywind.widgets.core.json;

import net.sf.json.JSONException;
import net.sf.json.util.PropertySetStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>json对象转java，属性转换策略包装类 bean时候使用的属性转换策略，本类，作用是忽略json对象中多余的属性，使json有比bean更多的属性时，不报错</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月2日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class PropertyStrategyWrapper extends PropertySetStrategy {
    /**
     * @Fields LOGGER : 日志操作类
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyStrategyWrapper.class);
    /**
     * @Fields original : 属性设置策略
     */
    private final PropertySetStrategy original;

    /**
     * <p>
     * Title: 构造函数
     * </p>
     * <p>
     * Description: 转换策略
     * </p>
     * @param original 一般使用默认转换策略
     */
    public PropertyStrategyWrapper(PropertySetStrategy original) {
        this.original = original;
    }

    @Override
    public void setProperty(Object o, String string, Object o1) {
        try {
            original.setProperty(o, string, o1);
        } catch (JSONException e) {
            LOGGER.warn("JSONObject to Bean error:" + e.getMessage());
        }
    }
}
