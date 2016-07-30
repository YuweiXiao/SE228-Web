<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Book extends CI_Controller {

	private $data = array();

	/**
	 * Constructor
	 * Autoload： Auth
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->model('book_model');
	}


	/**
	 * Check is admin, load admin book page.
	 */
	public function index()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$this->load->view('admin/book');
	}


	/**
	 * Get books info
	 * @return [array]  Info of requested books
	 */
	public function get_books()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$page = $this->input->post('page');
		$row = $this->input->post('rows');
		$page = isset($page) ? intval($page) : 1;
		$rows = isset($rows) ? intval($rows) : 10;
		$offset = ($page-1)*$rows;

		$result = $this->book_model->get_books($offset, $rows);

		$data['total'] = $this->book_model->get_book_num();
		$data['rows'] = $result;
		echo json_encode($data);
	}


	/**
	 * Update book
	 * @return [update status]
	 */
	public function save_book()
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$data = $this->input->post(NULL, true);
		$str = $this->book_model->insert($data);
		json_encode($str);
	}


	/**
	 * Destroy book by id
	 * @param  integer $id bookID
	 * @return [array]     delete status
	 */
	public function destroy_book($id = -1)
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$str = $this->book_model->delete_by_id($id);
		echo json_encode(array('success'=>true));
	}


	/**
	 * Update_book by id
	 * @param  integer $id bookID
	 * @return [update status]
	 */
	public function update_book($id = -1)
	{
		if(!$this->auth->is_admin())
			redirect('login','refresh');
		$data = $this->input->post(NULL, true);
		$str = $this->book_model->update_by_id($id, $data);
		return json_encode($str);
	}
}


/* End of file book.php */
/* Location: ./application/constrollers/admin/book.php */