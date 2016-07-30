<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Admin Dashboard</title>
	<meta name="author" content="Xiao YuWei">
	<link rel="stylesheet" type="text/css" href="<?php echo base_url('css/themes/default/easyui.css');?>">
	<link rel="stylesheet" type="text/css" href="<?php echo base_url('css/themes/icon.css');?>">
	<link rel="stylesheet" type="text/css" href="<?php echo base_url('css/bootstrap.min.css');?>">
	<link rel="stylesheet" type="text/css" href="<?php echo base_url('css/style.css');?>">
	<script src="<?php echo base_url('js/jquery-1.12.2.min.js');?>"></script>
	<script src="<?php echo base_url('js/jquery.easyui.min.js');?>"></script>
	<script>
		function addTab(title, url){
			if ($('#tt').tabs('exists', title)){
				$('#tt').tabs('select', title);
			} else {
				console.log($('#tt').outerHeight(true));
				console.log($('#tt').height());
				var height = $('#tt').outerHeight(true) - 70;
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:'+height+'px;"></iframe>';
				$('#tt').tabs('add',{
					title:title,
					content:content,
					closable:true
				});
			}
		}
	</script>
</head>
<body style="padding-top:50px">

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<?=site_url('store')?>">Bookstore</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">

            <li><a href="#">Hello <?php echo $name?></a></li>
            <li><a href="<?php echo site_url('login/logout')?>">Log out</a></li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container-fuild">
    	<div class="row">
	    	<div class="col-sm-3 col-md-2 sidebar">
	    		<ul class="nav nav-sidebar">
		            <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>
		            <li><a href="#" class="" onclick="addTab('Edit book','<?php echo site_url('admin/book')?>')">Edit book</a></li>
		            <li>
					<a href="#" class="" onclick="addTab('Edit user','<?php echo site_url('admin/user')?>')">Edit users</a></li>
		            <li><a href="#" class="" onclick="addTab('Edit orders','<?php echo site_url('admin/order')?>')">Edit orders</a></li>
	          	</ul>
			</div>
			<div class= "col-sm-9 col-md-10 col-sm-offset-3 col-md-offset-2">
			<h1 class="page-header" style="padding-left: 20px">Dashboard</h1>
				<div id="tt" class="easyui-tabs">
					<div title="Home" style="padding:20px">
						<p>Hello. </p>
						<p>Here, you can update\insert\delete\query books and users infomation.</p>
						<p>And you can update\insert\delete order info.</p>
						<p>If you want to create new order, you can go to the store index page, browse books and place orders</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>