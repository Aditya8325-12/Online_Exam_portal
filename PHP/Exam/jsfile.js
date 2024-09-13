

let test_name;
let test_type;
let question_count;


// for user want chapter wise or week wise test 
function handleKeyChange() {
    var key = document.getElementById("key").value;
    if (key === "Weekly_test") {
      document.getElementById("Weekly_test").style.display = "block";
      document.getElementById("question").value = 5;


      // for set the minimum current date 
      let dateInput = document.getElementById("dateinput");
      let today = new Date();
      let year = today.getFullYear();
      let month = String(today.getMonth() + 1).padStart(2, '0');
      let day = String(today.getDate()).padStart(2, '0');
      let minDate = year + '-' + month + '-' + day;
      dateInput.setAttribute('min', minDate);
    

      get_week_test_no();
    } else {
      document.getElementById("Weekly_test").style.display = "none";
      document.getElementById("question").value = 10;
      get_chapter_no();
    }
  }

//   geting the  test no  week wise 
  function get_week_test_no()
  {
    let str='SELECT MAX(week_test_no) AS max_value FROM test_name;';
          const querydata = {
              query: str,
              option: 'get_test_no'
          };

          const fetch_obj = {
              method: "post",
              header: {
                  "Content-Type": "application/JSON",
              },
              body: JSON.stringify(querydata)
          };
          fetch('index.php',fetch_obj)
              .then(response => response.json())
              .then(data => {
                  console.log(data[0][0]);                     
                  let test_no=data[0][0];
                  test_no++;                     
                  document.getElementById("testname").value ="Test No "+test_no;
                  document.getElementById("test_no").value =test_no; 
              })
  }
//   geting the  test no  chapter wise 
  function get_chapter_no()
  {
    let str='SELECT MAX(ch_test_no) AS max_value FROM test_name;';
          const querydata = {
              query: str,
              option: 'get_test_no'
          };
  
          const fetch_obj = {
              method: "post",
              header: {
                  "Content-Type": "application/JSON",
              },
              body: JSON.stringify(querydata)
          };
          fetch('index.php',fetch_obj)
              .then(response => response.json())
              .then(data => {
                  console.log(data[0][0]);
                  let test_no=data[0][0];
                  test_no++;                     
                  document.getElementById("testname").value ="Test No "+test_no;
                  document.getElementById("test_no").value =test_no;     
          })
  }
  
//    create the new  test  
  async function test_creation_table() {
    Swal.fire({
      title: 'Test creation ',
      html:`  <div class="container w-100">
      <div class="row form-group my-2 ">
        <div class="col-4 d-flex justify-content-center align-items-center">
            <label for="key" class=" fs-6 "> Test Type</label>
        </div>
        <div class="col-8">
          <select class="form-control" id="key" onchange="handleKeyChange()" required>
            <option value="none">none</option>
            <option value="Chapter_wise_test">Chapter Wise Test</option>
            <option value="Weekly_test">Weekly Test</option>
          </select>
        </div>
      </div>
  
      <div class="row form-group my-2 ">
        <div class="col-4 d-flex justify-content-center align-items-center">
          <label for="testname" class=" fs-6"> Test Name :</label>
        </div>
        <div class="col-8">
          <input type="text" class="form-control " id="testname" placeholder="Enter the test name">
        </div>
      </div>
  
      <div class="row form-group my-2 ">
        <div class="col-4 d-flex justify-content-center align-items-center">
          <label for="topicname" class="fs-6">Topic Name</label>
        </div>
        <div class="col-8">
          <input type="text" class="form-control" id="topicname" placeholder="Enter the topic name">
        </div>
      </div>
  
  
  
      <div class="row form-group my-2 ">
        <div class="col-4 d-flex justify-content-center align-items-center">
          <label for="question" class=" fs-6">How many questions</label>
        </div>
        <div class="col-8">
          <input type="number" class="form-control" placeholder="0" id="question" required>
          <input type="hidden" id="test_no">
        </div>
      </div>
  
  
  
  
      <div id="Weekly_test" style="display: none;">
        <div class="row form-group my-2 ">
          <div class="col-4 d-flex justify-content-center align-items-center">
            <label for="test_date" class=" fs-6">Date (day/month/year)</label>
          </div>
          <div class="col-8">
            <input type="date" class="form-control" id="dateinput" pattern="\d{4}-\d{2}-\d{2}">
          </div>
        </div>
  
        <div class="row form-group my-2 ">
          <div class="col-4 d-flex justify-content-center align-items-center">
            <label for="test_duration" class=" fs-6">Test Duration (Hour/Minute)</label>
          </div>
          <div class="col-8">
            <input type="time" class="form-control" placeholder="0" id="test_duration">
          </div>
        </div>
      </div>
    </div>`,
      focusConfirm: false,
      preConfirm: async () => {


        test_name = document.getElementById("testname").value;
        test_topic=document.getElementById("topicname").value;
        test_type = document.getElementById("key").value;
        test_duration = document.getElementById("test_duration").value;
        question_count = parseInt(document.getElementById("question").value);
        test_no=parseInt(document.getElementById("test_no").value);
        test_date=document.getElementById("dateinput").value;
        
        str = null;
        if (test_type === "Weekly_test") {
          str = 'INSERT INTO  test_name(testname,test_type,test_duration,week_test_no,topic_name,no_of_question,test_date) VALUE("' + test_name + '","' + test_type + '","' + test_duration + ':00",'+test_no+',"'+test_topic+'",'+question_count+',"'+test_date+'");';
          console.log(str);
        }
        else {
          str = 'INSERT INTO  test_name(testname,test_type,ch_test_no,topic_name,no_of_question) VALUE("' + test_name + '","' + test_type + '","'+test_no+'","'+test_topic+'","'+question_count+'");';
        }
        const querydata = {
          query: str,
          option: 'Test_name'
        };
        const fetch_obj = {
          method: "post",
          header: {
            "Content-Type": "application/JSON",
          },
          body: JSON.stringify(querydata)
        };
        return fetch("index.php", fetch_obj)
        return true;
      },
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          'test  will be the created',
          'done',
        )
        document.getElementById("table_div").style.display = "block";
        console.log(question_count);
        console.log(result);
        display_block();
        removeEventListener('onclick',test_creation_table);
      }
    });

  }

//  for inserting the question  one by one  
  function display_block() {


    let form = document.getElementById('myForm');
    let  counter = 1;
    document.getElementById("num").innerHTML = counter;
    document.getElementById("total").innerHTML = question_count;
    form.addEventListener('submit', function user(event) {
    event.preventDefault(); // Prevent form submission
      

      let right;
      if(form.elements.option.value==1)
      {
        right=form.elements.option1.value;
      }if(form.elements.option.value==2)
      {
        right=form.elements.option2.value; 
      }
      if(form.elements.option.value==3)
      {
        right=form.elements.option3.value; 
      }if(form.elements.option.value==4)
      {
        right=form.elements.option4.value; 
      }

      const querydata = {
        question: form.elements.question_name.value,
        option1: form.elements.option1.value,
        option2: form.elements.option2.value,
        option3: form.elements.option3.value,
        option4: form.elements.option4.value,
        correct: right,
        name: test_name,
        t_type:test_type,  
        option: 'question_insert'
      };
      let data = getfile(querydata);
      console.log(data);
      form.reset();
      counter++;
      if (counter === question_count + 1) {
        Swal.fire({
          icon: 'success',
          title: 'Exam Has been created',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("table_div").style.display = "none";
        form.removeEventListener('submit',user);
        return; // Exit the function
      } 
      document.getElementById("num").innerHTML = counter;
    });

  }

//   get the data from the server 
  async function getfile(data) {
    const fetch_obj = {
      method: "post",
      header: {
        "Content-Type": "application/JSON",
      },
      body: JSON.stringify(data)
    };
    let response = await fetch("index.php", fetch_obj);
    let result = response.json();
    return result;
  }

