<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
	<%@include file="/template/admin_header.jsp"%>


	<table id="dg" title="My Users" class="easyui-datagrid"
			url= "<%=path %>/admin/users_allUsers"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="username" width="20%">Username</th>
				<th field="password" width="20%">password</th>
				<th field="name" width="20%">name</th>
				<th field="e_mail" width="20%">E-mail</th>
				<th field="role_name" width="20%">role</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>
	</div>
	
	<div id="dlg" class="easyui-dialog" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">User Information</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>Username:</label>
				<input name="user.username" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>Password:</label>
				<input name="user.password" class="easyui-textbox">
			</div>
			<div class="fitem">
				<label>name:</label>
				<input name="user.name" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>E-mail:</label>
				<input name="user.e_mail" class="easyui-textbox" validType="email">
			</div>
			<div class="fitem">
				<label>Role Name:</label>
				<input name="user.role_name" class="easyui-textbox">
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
	</div>


	<script src="<%=path %>/static/js/jquery-1.12.2.min.js"></script>
	<script src="<%=path %>/static/js/bootstrap.min.js"></script>
	<script src="<%=path %>/static/js/jquery.easyui.min.js"></script>
	<script src="<%=path %>/static/js/iframeResizer.contentWindow.min.js"></script>
	<script type="text/javascript">
		var base_url = "<%=path%>/admin/users";
		var url;

		function newUser(){
			$('#dlg').dialog('open').dialog('setTitle','New User').dialog('move',{top:50});
			$('#fm').form('clear');
			url = base_url + '_saveUser';
			console.log(url);
		}
		function editUser(){
			var row = $('#dg').datagrid('getSelected');
			console.log(row.userID);
			console.log(row.role.name);
			for(var p in row) {
				row["user."+p] = row[p];
			}
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','Edit User').dialog('move',{top:50});
				$('#fm').form('load',row);
				url = base_url + '_updateUser?userID='+row.userID;
			}

		}
		function saveUser(){
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
		                $('#dg').datagrid('reload');    // reload the user data
		            }
		        }
		    });
		}
		function destroyUser(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
					if (r){
						$.post(base_url + '_destroyUser?userID='+row.userID,function(result){
							if (result.success){
								$('#dg').datagrid('reload');	// reload the user data
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