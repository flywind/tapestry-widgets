requirejs.config({
	"shim" : {
		"editor": ["jquery"],
		"lang": ["jquery","editor"]
	},
	"paths" : {
		"editor" : "plugins/editor/xheditor/xheditor-1.2.2.min",
		"lang" : "plugins/editor/xheditor/xheditor_lang/zh-cn"
	}
});
define(["lang","jquery"],function(lang,$){
	var init;
	init = function(spec){
		var Obj = $('#'+spec.id);
		var plugins={
				Code:{c:'btnCode',t:'插入代码',h:1,e:function(){
					var _this=this;
					var htmlCode='<div><select id="xheCodeType"><option value="html">HTML/XML</option><option value="js">Javascript</option><option value="css">CSS</option><option value="php">PHP</option><option value="java">Java</option><option value="py">Python</option><option value="pl">Perl</option><option value="rb">Ruby</option><option value="cs">C#</option><option value="c">C++/C</option><option value="vb">VB/ASP</option><option value="">其它</option></select></div><div><textarea id="xheCodeValue" wrap="soft" spellcheck="false" style="width:300px;height:100px;" /></div><div style="text-align:right;"><input type="button" id="xheSave" value="确定" /></div>';			
					var jCode=$(htmlCode),jType=$('#xheCodeType',jCode),jValue=$('#xheCodeValue',jCode),jSave=$('#xheSave',jCode);
					jSave.click(function(){
						_this.loadBookmark();
						_this.pasteHTML('<pre class="prettyprint lang-'+jType.val()+'">'+_this.domEncode(jValue.val())+'</pre>');
						_this.hidePanel();
						return false;	
					});
					_this.saveBookmark();
					_this.showDialog(jCode);
				}}
			};
		
		var a = {	
			plugins:plugins,
			loadCSS:'<style>pre {display:block;padding:9.5px;margin: 0 0 10px;font-size: 13px;line-height: 1.42857143;color: #333;word-break: break-all;word-wrap: break-word;background-color: #f5f5f5;border: 1px solid #ccc;border-radius: 4px} pre code {padding: 0;font-size: inherit;color: inherit;white-space: pre-wrap;background-color: transparent;border-radius: 0} code {padding: 2px 4px;font-size: 90%;color: #c7254e;background-color: #f9f2f4;border-radius: 4px;} code, kbd, pre, samp {color: #2f6f9f;font-family: Menlo,Monaco,Consolas,"Courier New",monospace;}</style>'
		}
		var opts = $.extend({},spec.params,a);
		Obj.xheditor(opts);
	}
	return {
		init:init
	}
})