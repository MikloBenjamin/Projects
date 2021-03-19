<?php
    error_reporting(E_ALL);
    ini_set('display_errors', 1);
    session_start();
    $db = "miklo_benjamin_eplanner";
    $connection = new mysqli("localhost","root","",$db);
    if(!$connection){
        die("Failed to connect to the database");
    }
    else{
        if($_SERVER["REQUEST_METHOD"] == "POST"){
            $text = $_POST["text"];
            $ename = $_POST["ename"];
            $checked = $_POST["imp"];
            $uname = $_SESSION["user"];
            $date = strtotime($_SESSION["date"]);
            $date = date('Y-m-d', $date);
            if($checked == "on"){
                $checked = 1;
            }
            else{
                $checked = 0;
            }
            if(strlen($ename) > 0){
                $sql = "INSERT INTO events(username, ename, edesc, edate, important) VALUES(?,?,?,?,?) order by edate";
                if(substr($uname, 0, 5) == "guest"){
                    $sql = "INSERT INTO gevents(username, ename, edesc, edate, important) VALUES(?,?,?,?,?) order by edate";
                }
                if($query = $connection->prepare($sql)){
                    $query->bind_param("ssssi", $uname, $ename, $text, $date, $checked);
                    $query->execute();
                    echo "Text added succesfully";
                }
                else{
                    die($connection->errno . " " . $connection->error);
                }
            }
        }
    }
    header("location: index.php");
    exit;
?>