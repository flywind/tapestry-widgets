package org.flywind.widgets.test.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

@Import(module={"plugin/easyui"},stylesheet={"${widget.plugins.assets.path}/easyui/themes/bootstrap/easyui.css","${widget.plugins.assets.path}/easyui/themes/icon.css"})
public class JGrid {
	
	
	private JSONObject data;
	
	public JSONObject getData(){
		JSONObject j = new JSONObject();
		j.put("productid", "FI-SW-01");
		j.put("productid", "FI-SW-01");
		
		JSONArray ja = new JSONArray();
		ja.put(j);
		
		JSONObject result = new JSONObject();
		result.put("total", 1);
        result.put("rows", ja);
		return result;
	}
}
