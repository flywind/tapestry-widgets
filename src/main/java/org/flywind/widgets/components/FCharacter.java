package org.flywind.widgets.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * <p>html字符转换组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月11日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FCharacter {

	@Property
	@Parameter(value="space",required=true,defaultPrefix=BindingConstants.LITERAL)
	private String type;
	
	@Parameter(value="1")
	private int count;
	
	public boolean afterRender(MarkupWriter writer){
		String str = "";
		String value = "";
		if("space".equals(type)){
			str = "&nbsp;";
		}else if("leftQuotes".equals(type)){
			str = "&#8220;";
		}else if("rightQuotes".equals(type)){
			str = "&#8221;";
		}else if("brokenLine".equals(type)){
			str = "&#8212;";
		}else if("shortBrokenLine".equals(type)){
			str = "&#8211;";
		}else if("pound".equals(type)){
			str = "&pound;";
		}else if("euro".equals(type)){
			str = "&#8364;";
		}else if("yen".equals(type)){
			str = "&yen;";
		}else if("copy".equals(type)){
			str = "&copy;";
		}else if("reg".equals(type)){
			str = "&reg;";
		}else if("brand".equals(type)){
			str = "&#8482;";
		}else{
			str = "";
		}

		for(int i=0;i<count;i++){
			value += str;
		}
		
		writer.writeRaw(value);
		return true;
	}
}
