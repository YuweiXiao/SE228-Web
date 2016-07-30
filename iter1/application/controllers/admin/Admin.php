<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Admin extends CI_Controller {

	var $data = array();

	/**
	 * Constructor
	 * Autoload: auth
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->model('user_model');
		$this->load->model('book_model');
	}

	/**
	 * Check admin auth, load dashboard.
	 */
	public function index()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$this->data['name'] = $this->session->userdata('user')['name'];
		$this->load->view('admin/dashboard', $this->data);
	}
}