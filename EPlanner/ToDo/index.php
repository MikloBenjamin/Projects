<?php   error_reporting(E_ALL);
        ini_set('display_errors', 1);
        session_start();
?>
<!DOCTYPE html>
<html lang="en">
  <head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>To Do List</title>
    <link rel="stylesheet" href="planner.css">
  </head>
  <body onload="rdy()">
    <?php
        $user = $_SESSION["user"];
        $date = $_SESSION["date"];
        $db = "miklo_benjamin_eplanner";
        $connection = new mysqli("localhost","root","",$db);
        if(isset($date)){
            echo "
            <div id = 'image'>
                <a href = '../index.php'>
                    <img src='../logo.png' id = 'innerimg' width='100%' height='110%'>
                </a>
                <form action = 'logout.php' method = 'POST'>
                    <label for = 'lgbtn'><input type = 'submit' value = 'Logout' name = 'lgbtn'></label>
                </form>
            </div>
            <div id = 'firstdiv'>
                <h3><i>The date you are planning the event for is ". $date . "</i></h3>
                <form action = 'add.php' method = 'POST' id = 'add-event'>
                    <label for = 'ename'  id = 'ename'>Event name: <input type = 'text' name = 'ename' autocomplete='off' placeholder = 'Event name ...' required></label><br>
                    <textarea name = 'text' id = 'tarea' placeholder = 'Event description ... ' rows = '6' cols = '50' autocomplete='off'></textarea><br>
                    <label for = 'imp' id = 'imp'>Important: <input type = 'checkbox' name = 'imp' checked></label><br>
                    <input type = 'submit' name = 'submit' id = 'add' value = 'Add'>
                    </form>
                    <a class = 'a' href = '../index.php'><button id = 'fk'>Abandon</button></a>
            </div>
            <div id = 'list'>";
                $sql = "SELECT id, ename, edesc, edate, important from events where username = '$user' and  edate = '$date'";
                if(substr($user, 0, 5) == "guest"){
                    $sql = "SELECT id, ename, edesc, edate, important from gevents where username = '$user' and  edate = '$date'";
                }
                $result = $connection->query($sql);
                while($row = $result->fetch_assoc()){
                    echo $row["ename"]."<br>".$row["edesc"]."<br>";
                    if($row["important"] == 1){
                        echo "IMPORTANT<br>";
                    }
                    $form = "
                        <form action = 'remove.php' method = 'POST'>
                            <input style = 'display: none;' type = 'text' name = 'id' value = '".$row["id"]."'>
                            <input style = 'width: 100px; height: 40px; border-radius: 10px; cursor: pointer; border: none;' type = 'submit' value = 'DELETE'>
                        </form>
                    ";
                    echo $form."<br>";
                }
            echo "</div>";
            $connection->close();
        }
        else{
            $_SESSION["user"] = "guest";
            header("location: logout.php");
        }
    ?>
    <script>
        var input;
        var textarea;
        function rdy(){
            input = document.getElementsByTagName("input");
            textarea = document.getElementsByTagName("textarea");
            clearonpaste();
        }

        function clearonpaste(){
            for(var i = 0; i < input.length; i++)(function(){
                input[i].addEventListener("paste", function(){
                    this.disabled = true;
                    this.disabled = false;
                })
                input[i].addEventListener("input", function(){
                    var stringinput = this.value;
                    var disabledcharacters = "\\,\'/;:[]{}$%&()`^~=+*\"";
                    for(var j = 0; j < disabledcharacters.length; j++){
                        if(disabledcharacters[j] == stringinput[stringinput.length - 1]){
                            stringinput = stringinput.substring(0, stringinput.length - 1);
                            this.value = stringinput;
                            break;
                        }
                    }
                })
            })(i);
            for(var i = 0; i < textarea.length; i++)(function(){
                textarea[i].addEventListener("input", function(){
                    var stringinput = this.value;
                    var disabledcharacters = "\\\'/`\"";
                    for(var j = 0; j < disabledcharacters.length; j++){
                        if(disabledcharacters[j] == stringinput[stringinput.length - 1]){
                            stringinput = stringinput.substring(0, stringinput.length - 1);
                            this.value = stringinput;
                            break;
                        }
                    }
                })
            })(i);
        }
    </script>
  </body>
</html>
