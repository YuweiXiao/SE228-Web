<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Order extends CI_Controller {

	private $data = array();


	/**
	 * Constructor
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->model('order_model');
	}


	/**
	 * Check is admin, load admin order page.
	 * @return [type] [description]
	 */
	public function index()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$this->get_orders();
		$this->load->view('admin/order', $this->data);
	}


	/**
	 * Get orders info, save is into private variable $this->data;
	 */
	public function get_orders()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$page = $this->input->post('page');
		$row = $this->input->post('rows');
		$page = isset($page) ? intval($page) : 1;
		$rows = isset($rows) ? intval($rows) : 10;
		$offset = ($page-1)*$rows;
		$result = $this->order_model->get_orders($offset, $rows);
		$this->data['total'] = $this->order_model->get_order_num();
		$this->data['rows'] = $result;
	}


	/**
	 * Create new order
	 * @return [insert status]
	 */
	public function save_order()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$this->data = $this->input->post(NULL, true);
		$str = $this->order_model->insert($this->data);
		json_encode($str);
	}


	/**
	 * Destroy order by id, then reload page.
	 * @param  integer $id orderID
	 */
	public function destroy_order($id = -1)
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$str = $this->order_model->delete_by_id($id);
		$this->index();
	}


	/**
	 * Update_order
	 * @return update status
	 */
	public function update_order()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');

		$this->data = $this->input->post(NULL, true);
		$res = $this->order_model->update_order($this->data['ordersbook']);
		
		if($res)
			echo "success";
		else
			echo "fail";
	}


	/**
	 * Destory ordersbook from order
	 * @param  integer $id bookID
	 * @return [delete status]      
	 */
	public function destroy_ordersbook($id = -1)
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$str = $this->order_model->delete_ordersbook_by_id($id);
		echo json_encode($str);
	}
}


/* End of file order.php */
/* Location: ./application/controllers/admin/order.php */