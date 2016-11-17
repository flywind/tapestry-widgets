define(["t5/core/dom","t5/core/events","t5/core/forms","jquery"],function(dom,events,forms,$){
	
	
	return function(){
		/*dom.onDocument(events.form.validateInError,function(){
            alert(1)
        });
		
		dom.onDocument(events.form.validateInError,function(){
            alert(2)
        });*/
		
		/*dom('createForm').submit(function(){
			alert(2)
		})
		
		alert(1)*/
		dom.onDocument('submit','#createForm',function(){
            alert(3)
        });
		dom.onDocument(events.form.validateInError,'#createForm',function(){
            alert(2)
        });
	
	}
})