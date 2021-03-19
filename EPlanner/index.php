<?php session_start(); 
    function actualize(){
        $servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "miklo_benjamin_eplanner";
        $connection = new mysqli($servername, $username, $password, $dbname);
        if ($connection->connect_error) {
            die("Connection failed: " . $connection->connect_error);
        }
        else{
            $uname = isset($_SESSION["user"]) ? $_SESSION["user"] : "";
            $date = date('Y-m-d');
            $sql = $connection->query("DELETE FROM events WHERE edate < '".$date."'");
        }
        $connection->close();
    }
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plan</title>
    <link rel = "stylesheet" type = "text/css" href = "CSS/css.css">
    <link href='https://fonts.googleapis.com/css?family=Petit Formal Script' rel='stylesheet'>
</head>
<body onload = "rdy()">
    <?php
        if(isset($_SESSION["user"])){
            // benikiraly2 is a username i set with which i send emails to users when i log in to it
            // these emails are sent to users who have important events that are going to happen in less than 2 weeks
            // and of course, these emails are only sent if the user haven't been notified yet 
            if(strcmp("benikiraly2", $_SESSION["user"]) == 0){
                include('sendNotifications.php');
            }
        }
        if(!isset($_SESSION['fname'])){
            echo '
            <div id = "notloggedDiv">
            <img src="logo.png">
            <div class = "betweensignandimage">
            </div>
            <a href = "setUpGuest.php"><button class = "sign">Preview</button></a>
            <a href = "login.php"><button class = "sign">Sign in</button></a>
            <a href = "register.php"><button class = "sign">Sign up</button></a>
            </div>
            ';
        }
        else{
            if(!isset($_SESSION["guest"])){
                actualize();
            }
            echo '
            <div id = "image">
            <img src="logo.png" id = "innerimg" width="20%" height="20%">
                <div id="imageright">
                <h3 id = "wlc"><i><u>Welcome <br>' . $_SESSION['fname'] . " " . $_SESSION['lname'] . ' </u></i></h3>
                <form action = "ToDo/logout.php" method = "POST" id = "lout">
                <input type = "submit" name = "submit" value = "Log Out">
                </form>
                <h2 align = "right" id = "time"></h2>
                </div>
            </div>
            <div id = "midleft">
            <h2 id="middlemonth"></h2>
            <div id = "dnames">
                <div>Su</div>
                <div>Mo</div>
                <div>Tu</div>
                <div>We</div>
                <div>Th</div>
                <div>Fr</div>
                <div>Sa</div>
            </div>
            <br>
            <div id = "days"></div>    
        
            <div id = "yandm">
                <div id = "years" onchange = "yearReset()">
                    <select id = "yearselector"></select>
                </div>
                <div id = "months">
                    <select onchange = "daysReset()" id = "monthselector"></select>
                </div>
            </div>
            </div>
            <div id = "midright">
            <div id = "rightevents">
            <h2 align = "center">Your important plans a week from now</h2>';
            
            $servername = "localhost";
            $username = "root";
            $password = "";
            $dbname = "miklo_benjamin_eplanner";
            $connection = new mysqli($servername, $username, $password, $dbname);
            $date = date("Y-m-d", mktime(0, 0, 0, date("m"), date("d")+7, date("Y")));
            $uname = $_SESSION["user"];
            $sql = "SELECT ename from events where username = '".$uname."' and edate < '".$date."' and important = 1";
            if(substr($uname, 0, 5) == "guest"){
                $sql = "SELECT ename from gevents where username = '".$uname."' and edate < '".$date."' and important = 1";
            }
            $result = $connection->query($sql);
            while($r = $result->fetch_assoc()){
                echo '<h3 align = "center">'.$r["ename"].'</h3>';
            }

            echo '</div><div id = "cont">
                Select a date
            </div>
            <form action = "sendSelected.php" method = "POST" id = "lform"> 
                <input style = "display:none;" type = "text" name = "year" value = "Year" readonly>
                <input style = "display:none;" type = "text" name = "month" value = "Month" readonly>
                <input style = "display:none;" type = "text" name = "day" value = "Day" readonly>
            </form>
            <input type = "submit" id = "submit" form = "lform">
            </div><br>
            <h2 id = "footer"> &copy MIKLO BENJAMIN 2020 </h2>
            ';
            if(isset($_SESSION["mustselect"])){
                echo '<h2 align = "center"><u><i>'. $_SESSION["mustselect"] . '</i></u></h2>';
                unset($_SESSION["mustselect"]);
            }
            $connection->close();
        }
    ?>
    <script src = "JS/js.js"></script>  
</body>
</html>