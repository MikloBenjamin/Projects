<?php
    session_start();    
    $_SESSION["guest"] = "guest";
    $gname = "";
    $result = false;
    $connection = new mysqli("localhost", "root", "", "miklo_benjamin_eplanner");
    while($gname == "" && !$result){
        $gname = "";
        $chars = "0123456789";
        for($i = 0; $i < 26; $i++){
            $gname .= $chars[rand(0,9)];
        }
        $r = $connection->query("SELECT gname from gusers where gname = '".$gname."'");
        $r = $r->fetch_assoc();
        if(!$r){
            $result = true;
            $_SESSION["user"] = "guest".$gname;
            $_SESSION['fname'] = "guest".$gname;
            $_SESSION['lname'] = "";
            $connection->query("INSERT INTO gusers(gname) VALUES('".$_SESSION["user"]."')");
        }
    }
    $connection->close();
    header("location: index.php");
?>