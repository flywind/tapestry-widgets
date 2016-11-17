requirejs.config({
	"shim" : {
		"b64" : ["jquery"]
	},
	"paths" : {
		"b64" : "base64"
	}
});
define([ "t5/core/dom", "t5/core/events", "t5/core/zone", "plugin/easyui","b64" ],
		function(dom, events, zone, easyui,b) {
			var test;
			test = function(spec) {
				var editIndex = undefined;
				var gridObj = $('#newContracts').datagrid();
				function endEditing() {
					if (editIndex == undefined) {
						return true
					}
					if (gridObj.datagrid('validateRow', editIndex)) {
						gridObj.datagrid('endEdit', editIndex);
						editIndex = undefined;
						return true;
					} else {
						return false;
					}
				}

				// 处理结束编辑
				gridObj.datagrid('options').onEndEdit = function(index, row) {
					var ed = gridObj.datagrid('getEditor', {
						index : index,
						field : 'companyId'
					});
					row.companyName = $(ed.target).combobox('getText');
				};

				// 处理单击行
				gridObj.datagrid('options').onClickCell = function(index, field) {
					if (editIndex != index) {
						if (endEditing()) {
							gridObj.datagrid('selectRow', index).datagrid(
									'beginEdit', index);
							var ed = gridObj.datagrid('getEditor', {
								index : index,
								field : field
							});
							if (ed) {
								($(ed.target).data('textbox') ? $(ed.target)
										.textbox('textbox') : $(ed.target))
										.focus();
							}
							editIndex = index;
						} else {
							setTimeout(function() {
								gridObj.datagrid('selectRow', editIndex);
							}, 0);
						}
					}
				};

				// 修改之后ajax保存
				$('#save').click(function() {
					// 如果还在编辑模式下取消所有选择
					if (endEditing()) {
						var row = gridObj.datagrid('unselectAll');
					}

					var rows = gridObj.datagrid('getChanges', "updated");
					//$('#sendData').submit();
					var data = $('#sendData').serializeObject();
					var dataString = JSON.stringify(rows)
					var b = new Base64();
					var database64 = b.encode(dataString);
					data.selectData = database64;
					var ajaxOpts = {
						type : 'POST',
						data : data,
						success:function(){
							alert('success');
						},
						error:function(){
							alert('error')
						}
					}
					dom.ajaxRequest(spec.save, ajaxOpts);
					// alert(rows.length+' rows are changed!');
				})

				$.fn.serializeObject = function() {
					var o = {};
					var a = this.serializeArray();
					$.each(a, function() {
						if (o[this.name] !== undefined) {
							if (!o[this.name].push) {
								o[this.name] = [ o[this.name] ];
							}
							o[this.name].push(this.value || '');
						} else {
							o[this.name] = this.value || '';
						}
					});
					return o;
				};

			}

			return {
				test : test
			}
		})