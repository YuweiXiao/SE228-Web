<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class User extends CI_Controller {

	private $data = array();

	/**
	 * Constructor
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->model('user_model');
	}


	/**
	 * Check is admin, load admin user page.
	 * @return [type] [description]
	 */
	public function index()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$this->load->view('admin/user');
	}


	/**
	 * Get_users info
	 * @return [array] [info of all users]
	 */
	public function get_users()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$page = $this->input->post('page');
		$row = $this->input->post('rows');
		$page = isset($page) ? intval($page) : 1;
		$rows = isset($rows) ? intval($rows) : 10;
		$offset = ($page-1)*$rows;

		$result = $this->user_model->get_users($offset, $rows);

		$data['total'] = $this->user_model->get_user_num();
		$data['rows'] = $result;
		echo json_encode($data);
	}


	/**
	 * Create a new user
	 * @return [insert status] 
	 */
	public function save_user()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$data = array(
			'username' => $this->input->post('username'),
			'password' => '123456',
			'name' => $this->input->post('name'),
			'e_mail' => $this->input->post('e_mail')
		);
		$str = $this->user_model->insert($data);
		json_encode($str);
	}


	/**
	 * Destroy user by userID
	 * @param  [integer] $id userID
	 * @return [delete status]    
	 */
	public function destroy_user($id)
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$str = $this->user_model->delete_by_id($id);
		echo json_encode(array('success'=>true));
	}


	/**
	 * Update_user by id
	 * @param  [integer] $id userID
	 * @return [update status]     
	 */
	public function update_user($id)
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$data = array(
			'username' => $this->input->post('username'),
			'password' => '123456',
			'name' => $this->input->post('name'),
			'e_mail' => $this->input->post('e_mail')
		);
		$str = $this->user_model->update_by_id($id, $data);
		return json_encode($str);
	}
}



/* End of file user.php */
/* Location: ./application/controllers/admin/user.php */