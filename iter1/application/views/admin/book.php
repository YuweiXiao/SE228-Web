<?php $this->load->view('admin/admin_header.php'); ?>

<body>

	<table id="dg" title="Books" class="easyui-datagrid" style="width:100%;height:250px;"
			url= "<?php echo site_url('admin/book/get_books')?>"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="isbn" width="50">isbn</th>
				<th field="title" width="50">title</th>
				<th field="author" width="50">author</th>
				<th field="press" width="50">press</th>
				<th field="inventory" width="50">inventory</th>
				<th field="sales" width="50">sales</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBook()">New Book</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBook()">Edit Book</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyBook()">Remove Book</a>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">Book Information</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>isbn:</label>
				<input name="isbn" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>Title:</label>
				<input name="title" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>Author:</label>
				<input name="author" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>Price:</label>
				<input name="price" class="easyui-textbox" required="true">
			</div>

			<div class="fitem">
				<label>press:</label>
				<input name="press" class="easyui-textbox">
			</div>

			<div class="fitem">
				<label>inventory:</label>
				<input name="inventory" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>sales:</label>
				<input name="sales" class="easyui-textbox">
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBook()" style="width:90px">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
	</div>
	
	<script type="text/javascript">
		var base_url = "<?php echo site_url('admin/book')?>/";
		var url;
		function newBook(){
			$('#dlg').dialog('open').dialog('setTitle','New Book');
			$('#fm').form('clear');
			url = base_url + 'save_book';
			console.log(url);
		}
		function editBook(){
			var row = $('#dg').datagrid('getSelected');
			console.log(row);
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','Edit Book');
				$('#fm').form('load',row);
				url = base_url + 'update_book/'+row.bookID;
			}
		}
		function saveBook(){
		    $('#fm').form('submit',{
		        url: url,
		        onSubmit: function(){
		            return $(this).form('validate');
		        },
		        success: function(result){
		           // var result = eval('('+result+')');
		            if (result.errorMsg){
		                $.messager.show({
		                    title: 'Error',
		                    msg: result.errorMsg
		                });
		            } else {
		                $('#dlg').dialog('close');        // close the dialog
		                $('#dg').datagrid('reload');    // reload the Book data
		            }
		        }
		    });
		}
		function destroyBook(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.confirm('Confirm','Are you sure you want to destroy this book?',function(r){
					if (r){
						$.post( base_url + 'destroy_book/'+row.bookID,function(result){
							if (result.success){
								$('#dg').datagrid('reload');	// reload the book data
							} else {
								$.messager.show({	// show error message
									title: 'Error',
									msg: result.errorMsg
								});
							}
						},'json');
					}
				});
			}
		}
	</script>
</body>
</html>