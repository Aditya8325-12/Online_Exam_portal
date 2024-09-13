<?php
include __DIR__ . DIRECTORY_SEPARATOR . 'main_class.php';


$process = $_GET['t1'];


    

    if($process=='login')
    {
      $query = 'SELECT * FROM login WHERE user_name="'.$_GET['t2'].'" AND  pass="'.$_GET['t3'].'";';
      print(json_encode($main_obj->application_get_data($query)));
    }


    // for registration 
    if($process=="signup")
    {
      $data['username'] = $_GET['t2'];
      $data['password'] = $_GET['t3'];
      $data['email'] = $_GET['t4'];
      $data['college'] = $_GET['t5'];
      $query = 'INSERT INTO login(user_name,pass,email,college)VALUE("'.$data['username'].'","'.$data['password'].'","'.$data['email'].'","'.$data['college'].'");';
      $result = $main_obj->send_data($query);
         if($result==true)
         {
              echo 'sign_up_done';
         }
         else
         {
            echo 'not_sign_up';
         }
     
    }


//    for geting the question list 
   if($process=="Test_question")
   {
      $query = 'SELECT * FROM question WHERE test_no="'.$_GET['t2'].'";';
      print(json_encode($main_obj->application_get_data($query)));
   }


   // origional

   // test list 
   if($process=="Test_list")
   {
       $query = 'SELECT * from test_name WHERE test_type="'.$_GET['t2'].'";';
       print(json_encode($main_obj->application_get_data($query))); 
   }

   // user id
   if($process=="user")
   {
      $query = 'SELECT * FROM login WHERE user_name="'.$_GET['t2'].'" AND pass="'.$_GET['t3'].'" ;';
      print(json_encode($main_obj->application_get_data($query)));
   }

   
   if($process=="record")
   {
      $query = 'SELECT * FROM user_progress WHERE user_id='.$_GET['t2'].' AND Test_type="'.$_GET['t3'].'";';
      print(json_encode($main_obj->application_get_data($query)));
   }



   // send the result of test  to database
   if($process=="test_result")
   {

      $query = 'INSERT INTO user_progress(test_name,Test_type,Total_marks,Total_question,marks_obtain,user_id)VALUES("' . $_GET['t2'] . '","' . $_GET['t3'] . '","' . $_GET['t4'] . '","' . $_GET['t5'] . '","' . $_GET['t6'] . '","' . $_GET['t7'] . '");';
      $data = $main_obj->send_data($query);
      if($data==true)
      {
           echo 'data save';
      }
      else
      {
         echo 'not save';
      }

   }



   if($process=="week_test_list")
   {
      $query = "SELECT min(test_date) AS shortest_date FROM test_name WHERE test_date >= '".$_GET['t2']."';";
      $date=$main_obj->get_test_week_date($query);
      $query2 = " SELECT * FROM test_name WHERE test_date='".$date."';";
      print(json_encode($main_obj->application_get_data($query2)));
   }








?>