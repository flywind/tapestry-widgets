package org.flywind.widgets.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * <p>jquery dialog组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月11日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Import(stylesheet={"${widget.plugins.assets.path}/artdialog/skin/default.css"})
public class FArtDialog implements ClientElement {

	/**
	 * 弹出层id
	 * 
	 * en *
	 * Dialog client id
	 */
	@Parameter(value="prop:componentResources.id",defaultPrefix=BindingConstants.LITERAL)
	private String clientId;
	
	@Inject
	private ComponentResources componentResources;
	
	public void beginRender(MarkupWriter writer){
		writer.element("div", "id", clientId, "style", "display:none");
	}
	
	public void afterRender(MarkupWriter writer){
		componentResources.renderInformalParameters(writer);
		writer.end();
	}

	public String getClientId()
    {
        return this.clientId;
    }
}
