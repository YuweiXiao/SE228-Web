<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Store extends CI_Controller {

	private $data = array();


	/**
	 * Constructor
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->model('book_model');
		$this->load->library('cart');
	}


	/**
	 * Load store index pages.
	 */
	public function index()
	{
		$this->data['books'] = $this->book_model->get_books();
		if( $this->session->userdata('user') != NULL )
			$this->data['name'] = $this->session->userdata('user')['name'];
		$this->load->view('home', $this->data);
	}


	/**
	 * Add book to shopping cart.
	 * Add book info to session using Cart class.
	 */
	public function add_to_cart()
	{
		/* Need logged in */
		if(!$this->auth->is_logged_in())
		{
			echo 1;
			return ;
		}

		$new_book = $this->input->post(NULL, true);
		$cart = $this->session->userdata('cart');	/* get cart object from session */
		if($cart == NULL)							/* cart object havn't exist, then create one */
			$cart = new Cart();
		else
			$cart = unserialize($cart);
		$cart->insert($new_book);					/* Add book to cart */

		$this->session->set_userdata('cart',serialize($cart));
		echo 'success';
	}


	/**
	 * Get cart info
	 * @return [array] the basic info of book which is in the cart.
	 */
	public function get_cart_info()
	{
		if(!$this->auth->is_logged_in())			/* need logged in */
			redirect('login','refresh');
		$cart = $this->session->userdata('cart');
		if($cart == NULL)							/* cart object do not exist. */
		{
			echo json_encode(array());
			return ;
		}
		$cart = unserialize($cart);					/* get cart object */
		$content = $cart->contents();				/* get books in the cart */
		foreach ($content as $index => $book) 		/* get books basic info by book model */
		{
			$content[$index] = array_merge($book, $this->book_model->get_basic_info_by_id($book['bookID']));
		}
		echo json_encode($content);
	}


	/**
	 * Create a order
	 * @return [order status]
	 */
	public function order()
	{
		if(!$this->auth->is_logged_in())
			redirect('login','refresh');
		$this->load->model('order_model');
		$cart = unserialize($this->session->userdata('cart'));
		$this->order_model->create_order($cart->contents());
		echo TRUE;
	}
}


/* End of file store.php */
/* Location: ./application/controllers/store.php */