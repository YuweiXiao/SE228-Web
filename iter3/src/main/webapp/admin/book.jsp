<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
	<%@include file="/template/admin_header.jsp"%>


	<table id="dg" title="Books" class="easyui-datagrid"
			url= "<%=path %>/admin/books_queryBooks"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="isbn" width="16%">isbn</th>
				<th field="title" width="16%">title</th>
				<th field="author" width="16%">author</th>
				<th field="press" width="16%">press</th>
				<th field="inventory" width="16%">inventory</th>
				<th field="soldAmount" width="16%">sales</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="<%=path%>/admin/view_bookcreate" class="easyui-linkbutton" iconCls="icon-add" plain="true" >New Book</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBook()">Edit Book</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyBook()">Remove Book</a>
	</div>
	
	<div id="dlg" class="easyui-dialog"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">Book Information</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>isbn:</label>
				<input name="book.isbn" id = "isbn" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>Title:</label>
				<input name="book.title" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>Author:</label>
				<input name="book.author" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>Price:</label>
				<input name="book.price" class="easyui-textbox" required="true">
			</div>

			<div class="fitem">
				<label>press:</label>
				<input name="book.press" class="easyui-textbox">
			</div>

			<div class="fitem">
				<label>inventory:</label>
				<input name="book.inventory" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>sales:</label>
				<input name="book.soldAmount" class="easyui-textbox">
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBook()" style="width:90px">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
	</div>


	<script src="<%=path %>/static/js/jquery-1.12.2.min.js"></script>
	<script src="<%=path %>/static/js/bootstrap.min.js"></script>
	<script src="<%=path %>/static/js/jquery.easyui.min.js"></script>
	<script src="<%=path %>/static/js/iframeResizer.contentWindow.min.js"></script>

	<script type="text/javascript">
		var base_url = "<%=path%>/admin/books";
		var url;
		function newBook(){
			$('#dlg').dialog('open').dialog('setTitle','New Book').dialog('move',{top:50});
			$('#fm').form('clear');
			url = base_url + '_saveBook';
			console.log(url);
		}
		function editBook(){
			var row = $('#dg').datagrid('getSelected');
			console.log(row);
			for(var p in row) {
				row["book."+p] = row[p];
			}
			console.log(row);
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','Edit Book').dialog('move',{top:50});
				$('#fm').form('load',row);
				url = base_url + '_updateBook?bookID='+row.bookID;
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
						$.post( base_url + '_destroyBook?bookID='+row.bookID,function(result){
							console.log(result);
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