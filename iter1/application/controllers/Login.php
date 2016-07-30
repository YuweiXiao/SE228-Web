<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Login extends CI_Controller {

	private $data;

	/**
	 * Constructor
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->model('user_model');
		$this->load->library('form_validation');
		$this->load->helper('url');
		$this->load->helper('form');
	}


	/**
	 * Check is logged in.
	 * Load login pages, check form.
	 */
	public function index()
	{
		if($this->auth->is_logged_in())
			redirect('store','refresh');
		$this->form_validation->set_rules('username', 'Username', 'required');
		$this->form_validation->set_rules('password', 'Password', 'required',
		        array('required' => 'You must provide a %s.')
		);
		if ($this->form_validation->run())
		{
			$data = $this->input->post(NULL, TRUE);
			$username = $this->input->post('username');
			$password = $this->input->post('password');

			$user = $this->user_model->get_by_username($username);
			if( $user )
			{
				if($this->user_model->check_password($password, $user['password']))
				{
					$this->user_model->allow_passport($user);
					if($this->user_model->is_admin())
						redirect('admin/admin','refresh');
					else redirect('store');
				} 
				else
					$this->data['login_error'] = 'Invalid username or password';
			}
			else
				$this->data['login_error'] = 'Username not exists';
		}
		$this->load->view('users/login', $this->data);
	}


	/**
	 * Log out, redirect to login page.
	 */
	public function logout()
	{
		if(!$this->auth->is_logged_in())
			redirect('login','refresh');
		$this->user_model->remove_passport();
		redirect('login','refresh');
	}


	/* debug */
	public function test()
	{
		//$res =  $this->user_model->get_by_username('zhangsan');
		//var_dump($res);
		//echo "hello";
		// redirect('/User/index','refresh');
	}
}

/* End of file login.php */
/* Location: ./application/controllers/login.php */