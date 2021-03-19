<?php 
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        $db = "miklo_benjamin_eplanner";
        $connection = new mysqli("localhost","root","",$db);
        if(!$connection){
            echo "Failed to connect to the database";
        }
        $id = (int)$_POST["id"];
        $sql = "DELETE FROM events WHERE id = $id";
        if(substr($_SESSION["user"], 0, 5) == "guest"){
            $sql = "DELETE FROM gevents WHERE id = $id";
        }
        if($connection->query($sql)){
            echo "Texts removed succesfully";
        }
        else{
            echo "Error removing texts from table<br>" . $connection->error;
        }
        $connection->close();
    }
    header("location: index.php");
?>