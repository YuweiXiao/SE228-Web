<?php

/**
 * Bool Model
 */
class Book_model extends CI_Model {

	var $table = 'books';

	/**
	 * Constructor
	 */
    public function __construct()
    {
        parent::__construct();
    }
	
	/**
	 * Get books from database
	 * @param  integer $offset 
	 * @param  integer $limits 
	 * @return [array]          Books info
	 */
	public function get_books($offset = -1, $limits = -1)
	{
		$this->db->select('*')->from($this->table);
		if($offset != -1 and $limits != -1)
			$this->db->limit($limits, $offset);
		$query = $this->db->get();
		return $query->result();
	}

	/**
	 * Get total books number
	 * @return [integer] Amount of books.
	 */
	public function get_book_num()
	{
		return $this->db->count_all($this->table);
	}

	/**
	 * Create new books
	 * @param  [array] $data The infomation of new book
	 * @return [array]       The insertion info
	 */
	public function insert($data)
	{
		$str = $this->db->insert($this->table, $data);
		if($str == true)
			return $data;
		else
			return array('errorMsg'=>'Some errors occured.');
	}

	/**
	 * Delete book by ID
	 * @param  [integer] $id bookID
	 */
	public function delete_by_id($id)
	{
		$this->db->delete($this->table,array('bookID'=>$id));
	}

	/**
	 * Update book by ID
	 * @param  [integer] $id   bookID
	 * @param  [array]   $data New info of books
	 * @return [array]       The update info
	 */
	public function update_by_id($id, $data)
	{
		$this->db->where('bookID',$id);
		$str = $this->db->update($this->table,$data);
		if($str == true)
		{
			$data['bookID'] = $id;
			return $data;
		}else
			return array('errorMsg'=>'Some errors occured.');
	}

	/**
	 * Get basic info of book by id
	 * @param  [integer] $id [bookID]
	 * @return [array]     	 
	 */
	public function get_basic_info_by_id($id)
	{
		$query = $this->db
				->select('isbn, title, price, author, press')
				->from($this->table)
				->where('bookID',$id)->get();
		return $query->row_array();
	}

}


/* End of file book_model.php */
/* Location: ./application/models/book_model.php */