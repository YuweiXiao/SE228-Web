<?php $this->load->view('admin/admin_header.php'); ?>

<body>

	<table id="dg" title="My Users" class="easyui-datagrid" style="width:100%;height:250px;"
			url= "<?php echo site_url('admin/user/get_users')?>"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="username" width="50">Username</th>
				<th field="password" width="50">password</th>
				<th field="name" width="50">name</th>
				<th field="e_mail" width="50">E-mail</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">User Information</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>Username:</label>
				<input name="username" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>Password:</label>
				<input name="password" class="easyui-textbox">
			</div>
			<div class="fitem">
				<label>name:</label>
				<input name="name" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>E-mail:</label>
				<input name="e_mail" class="easyui-textbox" validType="email">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
	</div>
	<script type="text/javascript">
		var base_url = "<?php echo site_url('admin/user')?>/";
		var url;
		function newUser(){
			$('#dlg').dialog('open').dialog('setTitle','New User');
			$('#fm').form('clear');
			url = base_url + 'save_user';
			console.log(url);
		}
		function editUser(){
			var row = $('#dg').datagrid('getSelected');
			console.log(row.userID);
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','Edit User');
				$('#fm').form('load',row);
				url = base_url + 'update_user/'+row.userID;
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
						$.post('/index.php/admin/user/destroy_user/'+row.userID,function(result){
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