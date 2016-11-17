define(["jquery","plugin/adialog"],function($,adialog){
	var init;
	init = function(spec){
		var obj = $('#'+spec.id);
		obj.on('click',function(){
			art.dialog.list[spec.closeId].close();
		});
	}
	
	return {
		init:init
	}
})