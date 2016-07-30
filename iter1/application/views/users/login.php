<?php $this->load->view('template/header.php'); ?>

<body style="background-color: #eee;">
<div class="container">
	
	<form class="form-signin" action="<?php echo site_url('login')?>" method="post">
		<h2 class="heading">Sign in</h2>
		<?php echo isset($login_error)?$login_error:"";?>
		<input type="text" class="form-control" name="username" placeholder="Username" value="<?php echo set_value('username'); ?>"/>
		<?php echo form_error('username', '<p class="help-inline">', '</p>'); ?>
	
		<input type="password" class="form-control" placeholder="Password" name="password"/>
		<?php echo form_error('password', '<p class="help-inline">', '</p>'); ?>
	
		<button class="btn btn-primary btn-lg btn-block" style="margin-top: 10px" type="submit">Sign in</button>
	</form>

</div>
</body>
</html>