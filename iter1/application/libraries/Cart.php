<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 *  Shopping cart class. support insert operation.
 */
class Cart
{
	private $_cart_content = array();      /* use array to store books in cart */

    /**
     * empty constructor
     */
    public function __construct() {
    }


    /**
     * add book in cart. check for duplicate.
     * @param   array   book info.
     * @return  bool 
     */
    public function insert($item = array()) {
        //parameter error
        if( ! is_array($item) OR count($item) == 0) {
            return FALSE;
        }
        // var_dump($item);
        foreach ($this->_cart_content as $index=>$book) {
        	// var_dump($book);
        	if($book['bookID'] == $item['bookID']){
        		$this->_cart_content[$index]['amount'] += $item['amount'];
        		return TRUE;
        	}
        }
        array_push($this->_cart_content, $item);
        return TRUE;
    }


    /**
     * get cart_content
     * @return  array   ordered books
     */
    public function contents() {
        return $this->_cart_content;
    }    
}



/* End of file cart.php */
/* Location: ./application/libraries/cart.php */