<?php
    session_start();
    $db = "miklo_benjamin_eplanner";
    $connection = new mysqli("localhost", "root", "", $db);
    if(!$connection){
        die("Could not connect: " . $connection->connect_error);
    }
    else{
        echo 'Connection successfull<br>';
    }
    if ($_SERVER["REQUEST_METHOD"] == "POST" && $_POST["year"] != "Year") {
        $year = $_POST["year"];
        $month = $_POST["month"];

        $months = array(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        );

        for($i = 0; $i < 12; $i++){
            if($months[$i] == $month){
                $month = (string)($i + 1);
                break;
            }
        }
        $day = $_POST["day"];

        //echo $year . "-" . $month . "-" . $day;
        
        $date = $year . '-' . $month . '-' . $day;
        $_SESSION["date"] = $date;
        $bto = "location: ToDo/index.php";
        if($_SESSION["user"] == "guest"){
            $bto .= "?guest=1";
        }
    }
    else{
        $_SESSION["mustselect"] = "You must select a date before submitting";
        $bto = "location: index.php";
        if($_SESSION["user"] == "guest"){
            $bto .= "?guest=1";
        }
    }
    header($bto);
    
?>