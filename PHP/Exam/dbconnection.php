<?php
class dbconnection
{

    protected $connection;

    function __construct()
    {
        $this->connection = new mysqli('localhost', 'root', '', 'exam_gate');
        if ($this->connection->errno) {
            die("database does not connected ");
        }
    }


    function get_test_name_id($test_name,$test_type)
    {
        $query = 'SELECT id FROM test_name WHERE testname="'.$test_name.'" AND test_type="'.$test_type.'";';
        $result = $this->connection->query($query);
        $data = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $data[] = $row;
        }
        return $data[0]['id'];
    }


    function send_query($query)
    {
        $this->connection->query($query);
    }


    function insert_the_question($data, $test_id)
    {
        $query = 'INSERT INTO question(que,o1,o2,o3,o4,test_no,coorect)VALUE("' . $data['question'] . '","' . $data['option1'] . '","' . $data['option2'] . '","' . $data['option3'] . '","' . $data['option4'] . '",' . $test_id . ',"' . $data['correct'] . '");';
        $this->connection->query($query);
    }

    // application side 
    function send_data($query)
    {
       if($this->connection->query($query))
       {
            return true;
       }
    }



    function application_get_data($query)
    {
        $row = $this->connection->query($query);
        $data = array();
        while($result=mysqli_fetch_array(($row)))
        {
            $data[] = $result;
        }
        return $data;

    }

    
    function application_get_query_data($query,$key)
    {
        $row = $this->connection->query($query);
        $data = array();
        while($result=mysqli_fetch_array(($row)))
        {
            $data[] = $result;
        }
        $object = array(
            "key" => $key,
            "arrayData" => $data
         );
         
        return $object;
    }




    function get_test_week_date($query)
    {
        $result = $this->connection->query($query);
        $data = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $data[] = $row;
        }
        return $data[0]['shortest_date'];
    }

}

?>