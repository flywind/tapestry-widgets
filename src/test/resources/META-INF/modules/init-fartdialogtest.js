define(["jquery","plugin/adialog"],function($,adialog){
	return function(){
		$('#show').click(function(){
			art.dialog({
				content: document.getElementById("medialog"),
				title: "welcome again two ",
				id:"medialog",
				height: 100,
				width: 400,
				resize: false,
				fixed: true,
				lock:true
				
			});
		});
		
		$('#close').click(function(){
			art.dialog.list['medialog'].close();
		})
		
	}
	
})