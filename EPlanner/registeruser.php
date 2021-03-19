<?php
    session_start();
    error_reporting(E_ALL);

    require_once("./mailing/index.php");

    function clearInput($data) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "miklo_benjamin_eplanner";
        $connection = new mysqli($servername, $username, $password, $dbname);
        if ($connection->connect_error) {
            die("Connection failed: " . $connection->connect_error);
        }
        $uname = clearInput($_POST['username']);
        $fname = clearInput($_POST['fname']);
        $lname = clearInput($_POST['lname']);
        $email = clearInput($_POST['email']);
        $pwd = clearInput($_POST['password']);
        $cpwd = clearInput($_POST['cpassword']);

        $unamelen = strlen($uname);

        $substr = substr($username, 0, 5);

        
        $backto = "location: register.php";
        
        if($substr == "guest"){
            $_SESSION["substr"] = "guest as first 5 character in username is reserved.";
            if($uname != '' && $unamelen >= 4 && $unamelen <= 32){
                $email = filter_var($email, FILTER_SANITIZE_EMAIL);
                if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
                    $_SESSION["we"] = "Incorrect email format";
                }
                if(!isset($_SESSION["we"])){
                    $x = 0;
                    if($connection->query("SELECT username FROM users WHERE username = $uname")){
                        $_SESSION["ut"] = "Username already taken";
                        $x++;
                    }
                    if($connection->query("SELECT email FROM users WHERE email = $email")){
                        $_SESSION["et"] = "Email already in use";
                        $x++;
                    }
                    if($pwd != $cpwd){
                        $_SESSION["wp"] = "Passwords do not match";
                        $x++;
                    }
                    else{
                        $subject = "EPlanner Registration Confirmation";
                        $message = "
                            <h1 align = 'center'>A registration has been detected with this email on EPlanner.</h1>
                            <a href = 'http://localhost/EPlanner/index.php'>www.eplanner.com</a>
                            <h2>Username: ".$uname."<br>Password: ".$pwd."<br>First Name: ".$fname."<br>Last Name: ".$lname."</h2>
                            <h1>If you made this operation, please confirm your account clicking on the button below</h2>
                            <form action = 'http://localhost/EPlanner/addUser.php' method = 'POST'>
                                <input style = 'display: none;' type = 'text' name = 'fname' value = '".$fname."'>
                                <input style = 'display: none;' type = 'text' name = 'lname' value = '".$lname."'>
                                <input style = 'display: none;' type = 'text' name = 'uname' value = '".$uname."'>
                                <input style = 'display: none;' type = 'text' name = 'pwd' value = '".$pwd."'>
                                <input style = 'display: none;' type = 'text' name = 'email' value = '".$email."'>
                                <input style = 'width: 140px; height: 40px; margin-left: 45%; border-radius: 10px; background: gray; color: white;' type = 'submit' value = 'CONFIRM'>
                            </form>
                        ";
                        unset($_SESSION["we"], $_SESSION["ut"], $_SESSION["et"]);
                        $backto = "location: ./login.php?confirm=1";
                        send($email, $subject, $message);
                    }
                }
            }
        }
        else{
            $_SESSION["eu"] = "Username cannot be empty and it's length must be between 4 and 32";
        }
        $connection->close();
    }
    echo "<script>window.location.assign('".$backto."')</script>";
    exit;
?>