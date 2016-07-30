<?php

/**
 * Order Model
 */
class Order_model extends CI_Model {

	var $table = 'orders';
	var $table_ref = 'ordersbook';

	/**
	 * constructor
	 */
    public function __construct()
    {
        parent::__construct();
    }
	

	/**
	 * Get orders from database
	 * @param  integer $offset 
	 * @param  integer $limits 
	 * @return [array] 			requested order, contain ordered books basic info.
	 */
	public function get_orders($offset = -1, $limits = -1)
	{
		$this->db->select('orders.*, name')->from($this->table);
		if($offset != -1 and $limits != -1)
			$this->db->limit($limits, $offset);
		$query = $this->db->join('users', 'users.userID = orders.userID')->order_by('orderTime','DESC')->get();
		$data = $query->result();
		for ($i = 0; $i < count($data); $i++) { 
			$data[$i]->ordersbook = $this->get_order_ele_by_id($data[$i]->orderID);
		}
		return $data;
	}

	/**
	 * Get ordered book info by ID
	 * @param  [integer] $id bookID
	 * @return [array]     	 book basic info
	 */
	public function get_order_ele_by_id($id)
	{
		$res = $this->db->select('ordersbook.*,isbn,title,author,press,press,price')->from($this->table_ref)->join('books', $this->table_ref.'.bookID = books.bookID')->where('orderID', $id)->get();
		return $res->result();
	}

	/**
	 * Get total order number.
	 * @return [integer] number of orders
	 */
	public function get_order_num()
	{
		return $this->db->count_all($this->table);
	}


	/**
	 * Update order.
	 * @param  [array] $data order's books info.
	 * @return [boolean]       True : update successfully
	 *                         False: update failed
	 */
	public function update_order($data)
	{
		/* convert object to array. */
		if(gettype($data) == 'object')
			objarray_to_array($data);	
		foreach ($data as $row) {
			if(gettype($row) == "object")
				$row = $this->objarray_to_array($row);
			$query = $this->db->replace($this->table_ref, $row);
		}
		return $query;
	}

	/**
	 * Delete book from order
	 * @param  [integer] $id bookID
	 * @return [boolean]     True : delete successfully
	 *                       False: delete failed
	 */
	public function delete_ordersbook_by_id($id)
	{
		$res = $this->db->delete($this->table_ref,array('ordersbookID'=>$id));
		return $res;
	}

	/**
	 * Convert Object to array. Reference: internet.
	 * @param  [object] 	$obj 
	 * @return [array]      
	 */
	function objarray_to_array($obj) {  
	    $ret = array();  
	    foreach ($obj as $key => $value) {  
	    if (gettype($value) == "array" || gettype($value) == "object"){  
		            $ret[$key] =  objarray_to_array($value);  
		    }else{  
		        $ret[$key] = $value;  
		    }  
	    }  
	    return $ret;  
	}  

	/**
	 * Create a new order
	 * @param  [array] $data ordered books info
	 */
	public function create_order($data)
	{
		$user = $this->session->userdata('user');
		$this->db->trans_start();
		/* create a order */
		$orderinfo = array(
				'userID' => $user['userID'],
				'orderTime'=> 'now()',
				'isDeal' => 0
			);
		$this->db->insert($this->table, $orderinfo,false);
		$id = $this->db->insert_id();

		/* add books into the order */
		foreach ($data as $book) {
			$bookinfo = array(
					'orderID' => $id,
					'bookID' => $book['bookID'],
					'amount' => $book['amount']
				);
			$this->db->insert($this->table_ref, $bookinfo);
		}
		$this->session->unset_userdata('cart');
		$this->db->trans_complete();
	}


	/**
	 * Delete order by ID
	 * @param  [integer] $id orderID
	 */
	public function delete_by_id($id)
	{
		$this->db->delete($this->table,array('orderID'=>$id));
	}

}


/* End of file order_model.php */
/* Location: ./application/models/order_model.php */