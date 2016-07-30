<?php

/**
 * User Model
 */
class User_model extends CI_Model {

	var $table = 'users';

    var $max_idle_time = 1000;

    /**
     * constructor
     */
    public function __construct()
    {
        parent::__construct();
    }

    /**
     * check password
     * @param  [string] $password       password to be checked 
     * @param  [string] $hash_password  
     * @return [boolean]                true : password correct
     *                                  false: password incorrect
     */
    public function check_password($password, $hash_password)
    {
    	if($password == $hash_password)
    		return true;
    	return false;
    }

    /**
     * Check whether user logged in. If inactive time is longer than $idle_time,
     * user need relog in.
     * @return boolean  true : logged in
     *                  false: havn't logged in
     */
    public function is_logged_in()
    {
		$last_activity = $this->session->userdata('last_activity');
		$logged_in = $this->session->userdata('logged_in');
		$user = $this->session->userdata('user');
		
		if ( ($logged_in == 'yes') 
		  && ((time() - $last_activity) < $this->max_idle_time )) {
			$this->allow_passport( $user );
			return true;
		} else {
			$this->remove_passport();
			return false;
		}
    }

    /**
     * Check whether user role is admin
     * @return boolean true : admin
     *                 false: normal user
     */
    public function is_admin()
    {
        $user = $this->session->userdata('user');
        $admin_role = $this->get_role_by_name('admin');
        if($user['roleID'] == $admin_role['roleID'])
            return true;
        return false;
    }

    /**
     * Get role info by name
     * @param  [string] $name role name
     * @return [array]        role info
     */
    public function get_role_by_name($name)
    {
        $query = $this->db->select('*')->from('role')->where('name', $name)->get();
        return $query->row_array();
    }


    /**
     * Remove user passport from session.
     */
    public function remove_passport()
    {
    	$array_items = array('last_activity' , 'logged_in', 'user');
		$this->session->unset_userdata($array_items);
    }

    /**
     * Save user info into session.
     * @param  [array] $user_data user info
     */
    public function allow_passport($user_data)
    {
    	$this->session->set_userdata( 
    			array( 
    					'last_activity' => time(), 
    					'logged_in' => 'yes', 
    					'user' => $user_data ) 
    		);
    }

    /**
     * Get user from users table by username
     * @param  [string] $username 
     * @return [array]  Get user from database successfully
     *         [boolean] false: user do not exists.
     */
    public function get_by_username($username)
    {
    	$query = $this->db->select('*')->from($this->table)->where('username',$username)->get();
    	if($query->num_rows()>0) return $query->row_array();
    	else
    		return false;
    }
	
    /**
     * Get users from database
     * @param  integer $offset 
     * @param  integer $limits 
     * @return [array]          users info
     */
	public function get_users($offset = -1, $limits = -1)
	{
		$this->db->select('*')->from($this->table);
		if($offset != -1 and $limits != -1)
			$this->db->limit($limits, $offset);
		$query = $this->db->get();
		return $query->result();
	}

    /**
     * Get total user number.
     * @return [integer] Amount of users.
     */
	public function get_user_num()
	{
		return $this->db->count_all($this->table);
	}

	/**
	 * Create a new user
	 * @param  [array] $data the infomation of new user
	 * @return [array]       the insertion info
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
     * Delete user by ID
     * @param  [integer] $id userID
     */
	public function delete_by_id($id)
	{
		$this->db->delete($this->table,array('userID'=>$id));
	}

    /**
     * Update user by id
     * @param  [integer] $id   userID
     * @param  [array]   $data user info
     * @return [array]       the insertion info
     */
	public function update_by_id($id, $data)
	{
		$this->db->where('userID',$id);
		$str = $this->db->update($this->table,$data);
		if($str == true)
		{
			$data['userID'] = $id;
			return $data;
		}else
			return array('errorMsg'=>'Some errors occured.');
	}

}


/* End of file user_model.php */
/* Location: ./application/models/user_model.php */