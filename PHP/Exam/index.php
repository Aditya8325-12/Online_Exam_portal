<?php

include __DIR__ . DIRECTORY_SEPARATOR . 'main_class.php';
$strRequest = file_get_contents('php://input');

if (!empty($strRequest)) {
    $response = ["success" => true, "data_recd" => json_decode($strRequest, true)];
    $data = json_decode($strRequest, true);

    if ($data['option'] == 'get_test_no') {
        echo json_encode($main_obj->application_get_data($data['query']));
    }
    if($data['option']=='Test_name')
    {
        $main_obj->send_query($data['query']);
        echo json_encode($response);
    }
    if($data['option']=='question_insert')
    {
        $test_id = $main_obj->get_test_name_id($data['name'],$data['t_type']);
        $main_obj->insert_the_question($data,$test_id);
        echo json_encode($response);
    }
    die();
}

include __DIR__ . '/header.php';
include __DIR__ . '/footer.php';

?>