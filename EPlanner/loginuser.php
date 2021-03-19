<?php
    session_start();
    ini_set("display_errors", true); error_reporting(E_ALL);
    $uname = $_POST['username'];
    $pwd = $_POST['password'];
    $db = "miklo_benjamin_eplanner";
    $connection = new mysqli("localhost", "root", "", $db);
    if(!$connection){
        die("Could not connect: " . $connection->connect_error);
    }
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        $sql = "SELECT firstname, lastname, username FROM users WHERE username = '$uname' AND password = '$pwd' LIMIT 1";
        $result = $connection->query($sql);
        if($result && $result->num_rows){
            $row = $result->fetch_assoc();
            $_SESSION["fname"] = $row["firstname"];
            $_SESSION["lname"] = $row["lastname"];
            $_SESSION["user"] = $row["username"];
            header("location: index.php");
            exit;
        }
        else{
            if($_GET["confirm"] == 0){
                header("location: login.php");
            }
        }
    }
?>