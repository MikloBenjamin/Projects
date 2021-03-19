<?php
    require_once("./mailing/index.php");
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "miklo_benjamin_eplanner";
    $connection = new mysqli($servername, $username, $password, $dbname);
    $date = date("Y-m-d", mktime(0, 0, 0, date("m"), date("d")+14, date("Y")));
    $sql = "SELECT id, username, ename, edesc, edate from events where important = 1 and edate < '".$date."' and notified = 0";
    $result = $connection->query($sql);
    $ids = array();
    $unames = array();
    while($row = $result->fetch_assoc()){
        /*
            (uname ->(0->email, 1->new array(ename, edesc, edate), 2-> ...)) 
        */
        if(!in_array($row["id"], $ids)){
            array_push($ids, $row["id"]);
        } 
            
        $sql = "SELECT email from users where username = '".$row["username"]."'";
        $r2 = $connection->query($sql)->fetch_assoc();
        $email = $r2["email"];
        $uname = $row["username"];
        $ename = $row["ename"];
        $edesc = $row["edesc"];
        $edate = $row["edate"];
        if(array_key_exists($uname, $unames)){
            array_push($unames[$uname], array($ename, $edesc, $edate));
        }
        else{
            $unames[$uname] = array($email, array($ename, $edesc, $edate));
        }
    }
    foreach($unames as $key => $value){
        $eml = $value[0]; /// email
        for($i = 1; $i < count($value); $i++){
            $a = $value[$i];
            $ename = $a[0]; // event name
            $edesc = $a[1]; // event description
            $edate = $a[2]; // event date
            $message = "
                <h1 align = 'center'>Reminder from EPlanner</h1>
                <h3>In less than 2 weeks you are having an event on:</h3>
                ".$edate."<br>".$ename."<br>".$edesc."
            "; // message to send 
            send($eml, "EPlanner Event Reminder", $message);
        }
    }
    for($i = 0; $i < count($ids); $i++){
        $sql = "UPDATE events set notified = 1 where id = $ids[$i]";
        $connection->query($sql);
    }
    $connection->close();
?>