define(["jquery","plugin/easyui"],function($,easyui){
	var gridInit,cutHeight,fixed,toggleMenu;
	gridInit = function(spec){	
		if(spec){
			if(spec.params.language == "zh-CN"){
				if ($.fn.pagination){
					$.fn.pagination.defaults.beforePageText = '第';
					$.fn.pagination.defaults.afterPageText = '共{pages}页';
					$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
				}
			}
			var obj = "#"+spec.target;
			var fixLayout = spec.params.fixLayout;
			
			if(fixLayout == false){
				$(obj).datagrid(spec.params);
				
				$(window).resize(function(){
					if(spec.params.width == "auto"){
						var reswidth = "auto";
						$(obj).datagrid('resize',{
							width:reswidth
						})
					}
				})
			}else{
				fixed(obj,spec.params,spec.params.cutWidth);
				toggleMenu(obj,spec.params,spec.params.cutWidth);
			}	
		}
		
	};
	
	cutHeight = function(layoutW){
		var obj = $('#selectCondition');
		if(obj.size() < 0){
			return;
		}
		var selectH = obj.height();
		return 168+selectH;
	};
	
	fixed = function(obj,params,cutWidth,layoutW){
		var cutH = cutHeight();
		var lw = layoutW || 0;
		var h = $(window).height()-cutH;
		var w = $(window).width()-cutWidth+lw;
		var gridCotent = $('#gridContent');
		if(gridCotent.size() == 0){
			alert('datagrid没有父级id=gridContent的div,无法初始化datagrid的高度!');
			return;
		}
		gridCotent.css({
			'height':h+'px',
			'width': w+'px'
		});
		$(obj).datagrid(params);
		$(obj).datagrid('options').width="auto";
		$(obj).datagrid('options').height=h;
		$(obj).datagrid('reload');
		
		$(window).resize(function(){
			h = $(window).height()-cutH;
			var w = $(window).width()-cutWidth+lw;
			gridCotent.css({
				'height':h+'px',
				'width': w+'px'
			});
			$(obj).datagrid("resize",{
				width:'auto',
				height:h
			});
		});
	};
	
	toggleMenu = function(obj,params,cutWidth,layoutW){
		var memuBtn = $("[data-toggle='offcanvas']");
		if(memuBtn.size() == 0){
			//alert("没有找到属性[data-toggle='offcanvas']的html元素!");
			return;
		}
		memuBtn.click(function(){
			if ($(window).width() > 767) {
				if($("body").is('.sidebar-collapse')){
					fixed(obj,params,cutWidth,layoutW);
				}else{
					fixed(obj,params,cutWidth,180);
				}
				$("body").toggleClass('sidebar-collapse');
			}
		})	
	}
	
	return {
		init:gridInit
	}
})