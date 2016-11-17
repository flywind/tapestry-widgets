(function($){
	T5.extendInitializers(function(){
		function init(spec){
			var map = new BMap.Map("container");          // 创建地图实例  
			var point = new BMap.Point(116.404, 39.915);  // 创建点坐标  
			map.centerAndZoom(point, 15);    
		}
		
		return{
			test : init
		}
	});
}(jQuery))