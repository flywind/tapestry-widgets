(function(){
	requirejs.config({
		shim: {
			'chosen': ['jquery']
		},
		paths: {
			'chosen': 'plugins/chosen/chosen.jquery.min'
		},
		waitSeconds: 0
	});
	
	define(['jquery','chosen','t5/core/dom','t5/core/events','t5/core/forms','t5/core/console','t5/core/zone','t5/core/messages'],function($,c,dom,events,forms,console,zone,messages){
		var component = {};
		component.init = function(spec){
			var model = spec.model,
				$obj = $('#'+spec.id),
				selectId = spec.id+'_select';
			
			//Create select
			var	ops = '<select id="'+selectId+'" multiple >';
			$.each(model,function(i,v){
				ops += '<option value="'+model[i].value+'">'+model[i].text+'</option>';
			});
			ops += '</select>';
			$obj.after(ops);
			
			//Init chosen
			var $select = $('#'+selectId);
			$select.chosen(spec.params);
			
			//Init value
			var cVals = $obj.val();
			if(cVals){
				$select.val(component.stringToArray(cVals));
				$select.trigger("chosen:updated");
			}
			
			//If value changed
			$select.change(function(){
				var sVals = $select.val();
				if(sVals){
					$obj.val(component.arrayToString(sVals));
				}else{
					$obj.val('');
				}
				console.info($select.val());
			});
			
			 var form = $obj.parents('form');
			 
			 /*form.submit(function(){
				 var requiredMsg = $obj.attr('data-optionality');
				 if(typeof requiredMsg == "string"){
					 if($obj.val() == ""){
						form.addClass('has-error');
						var divChosen = $('#'+selectId+'_chosen');
						if(divChosen.next().hasClass('help-block')){
							divChosen.next('.help-block').remove();
						}
						var warnHtml = '<p class="help-block" data-error-block-for="tags2">'+$obj.attr('data-required-message')+'</p>'
						$('#'+selectId+'_chosen').after(warnHtml);
						return false;
					}else{
						if(form.hasClass('has-error')){
							form.removeClass('has-error')
						}
					}
				 } 
			 })*/
			
		};
		
		component.arrayToString = function(arr){
			
			try {
				arr = arr.join(',')
			} catch (e) {
				arr = arr
			}
			
			return arr;
		};
		
		component.stringToArray = function(str){
			if(str == null || str == "") return;
			var arr = [];
			try {
				str = str.split(",");
			} catch (e) {
				str = arr.push(str);
			}
			
			return str;
		}
		
		return {
			init: component.init
		}
	});
	
}).call(this);