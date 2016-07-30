<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Auth {

	protected $CI;

	/**
	 * Constructor
	 */
	public function __construct()
	{
		$this->CI =& get_instance();				/* get super object */
		$this->CI->load->model('user_model');
	}


	/**
	 * Check whether logged in.
	 * @return boolean true : logged in
	 *                 false: havn't logged in
	 */
	public function is_logged_in()
	{
		if($this->CI->user_model->is_logged_in())
			return true;
		return false;	
	}


	/**
	 * Check whether is admin role
	 * @return boolean true : is admin
	 *                 false: isn't admin or havn't logged in
	 */
	public function is_admin()
	{
		if($this->CI->user_model->is_logged_in() && $this->CI->user_model->is_admin())
			return true;
		return false;
	}

}


/* End of file auth.php */
/* Location: ./application/libraries/auth.php */