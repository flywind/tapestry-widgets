package org.flywind.widgets.utils;

import java.sql.Clob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Predicate;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.flywind.widgets.core.json.JsonDateValueProcessor;
import org.flywind.widgets.core.json.JsonLobValueProcessor;

import net.sf.json.JsonConfig;

public class JQueryUtils {
	/**
	 * 合并json对象.
	 * 
	 * @param obj1
	 * @param obj2
	 * @return null if obj1 is null. Else return obj1 merged with obj2
	 */
	public final static JSONObject merge(JSONObject obj1, JSONObject obj2) {
		if (obj1 == null)
			return null;

		if (obj2 == null)
			return obj1;

		for (String key : obj2.keys()) {
			obj1.put(key, obj2.get(key));
		}

		return null;
	}

	public static Mapper<Asset, StylesheetLink> assetToStylesheetLink = new Mapper<Asset, StylesheetLink>() {
		public StylesheetLink map(Asset input) {
			return new StylesheetLink(input);
		};
	};
	
	//TODO Unit Test
	public static JSONObject convertInformalParametersToJson(
			final ComponentResources resources, final String prefix) {
		final JSONObject json = new JSONObject();
		F.flow(resources.getInformalParameterNames()).filter(new Predicate<String>() {

			public boolean accept(String param) {
				return param.startsWith(prefix);
			}
		}).each(new Worker<String>() {

			public void work(String params) {
				json.put(params.substring(prefix.length()),
						resources.getInformalParameter(params, String.class));
			}
		});
		
		return json;
	}
	
	/**
	 * list转换成json,结果集为datagrid的json格式数据
	 * @param source
	 * @param rowCount
	 * @return datagrid的json格式数据
	 */
	public static final JSONObject toGridJson(List<?> source,int rowCount){
		// 处理日期类型
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
        jsonConfig.registerJsonValueProcessor(Clob.class, new JsonLobValueProcessor());
        
        JSONObject result = new JSONObject();
        
        @SuppressWarnings("rawtypes")
		String sourceStr = net.sf.json.JSONArray.fromObject(source==null?new ArrayList():source, jsonConfig).toString();
        result.put("total", rowCount);
        result.put("rows", new JSONArray(sourceStr));
        
        return result;
	}
	
	/**
	 * 查找分页的总页数
	 * @param rowCount 总记录数
	 * @param pageSize 当前页显示的记录数
	 * @return 总页数
	 */
	public static final int findTotalPages(int rowCount, int pageSize){
		int condition = rowCount % pageSize;
		int totalPages = rowCount / pageSize;
		if(condition != 0){
			totalPages += 1;
		}
		return totalPages;
	}

	
	/**
	 * 根据请求获得文件上传的原来的名称及文件格式
	 * @param request HttpServletRequest请求
	 * @return map
	 */
	public static final Map<String,Object> getFileInfo(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String contentDisposition = request.getHeader("Content-Disposition");
        String[] params = contentDisposition.split(";");
        String filename = "";
        String suffix = "";
        for(String str : params){
        	if(str.contains("filename=")){
        		str = str.intern();
        		filename = str.substring(str.indexOf("\"") +1, str.lastIndexOf("\""));
        		break;
        	}
        }
        suffix = filename.substring(filename.indexOf(".")+1);
        map.put("filename", filename);
        map.put("suffix", suffix);
        
		return map;
	}
}