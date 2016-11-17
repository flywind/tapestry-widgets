package org.flywind.widgets.core.dao;


/**
 * <p>DataSourceHolder</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月2日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class FPageHolder {

    @SuppressWarnings("rawtypes")
	private static final ThreadLocal pageHolder = new ThreadLocal();
    
    public static void setPage(FPage page) {
        if(!page.equals(pageHolder.get())){
            pageHolder.set(page);
        }
    }

    public static FPage getPage() {
        return (FPage) pageHolder.get();
    }

    public static void clearContext() {
        pageHolder.remove();
    }
}
