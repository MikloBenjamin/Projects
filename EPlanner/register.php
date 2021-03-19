<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel = "stylesheet" type = "text/css" href = "CSS/register.css">
</head>
<body onload = "rdy()">
    <div id = "image">
        <img src="logo.png" id = "innerimg" width="20%" height="20%">
    </div>
    <form action = "registeruser.php" method = "POST" enctype="multipart/form-data" id = "loginform">
        <u><strong><i><h5 align = "center">Register</i></strong></u></h5>
        <strong><i><p align = "center">*After succesfully registering you will be redirected to the login page*</p></i></strong>
        <div class = "forlabels">
            <?php
                if(isset($_SESSION["substr"])){
                    echo '<p>'.$_SESSION["substr"].'<p><br>';
                    unset($_SESSION["substr"]);
                }
                if(isset($_SESSION["ut"])){
                    echo '<p>'.$_SESSION["ut"].'</p>';
                    unset($_SESSION["ut"]);
                }
            ?>
            <label for = "username"><strong><i>Username: </i></strong></label>
            <input type = "text" name = "username" id = "username" required autocomplete = "off"><br>
        </div>
        <br>
        <div class = "forlabels">
            <label for = "fname"><strong><i>First Name: </i></strong></label>
            <input type = "text" name = "fname" id = "fname" autocomplete = "off"><br>
        </div>
        <br>
        <div class = "forlabels">
            <label for = "lname"><strong><i>Last Name: </i></strong></label>
            <input type = "text" name = "lname" id = "lname" autocomplete = "off"><br>
        </div>
        <br>
        <div class = "forlabels">
            <?php
                if(isset($_SESSION["we"])){
                    echo '<p>'.$_SESSION["we"].'</p>';
                    unset($_SESSION["we"]);
                }
                if(isset($_SESSION["et"])){
                    echo '<p>'.$_SESSION["et"].'</p>';
                    unset($_SESSION["et"]);
                }
            ?>
            <label for = "email"><strong><i>E-mail: </i></strong></label>
            <input type = "text" name = "email" id = "email" required autocomplete = "off"><br>
        </div>
        <br>
        <div class = "forlabels">
            <label for = "password"><strong><i>Password: </i></strong></label>
            <input type = "password" name = "password" id = "password" required autocomplete = "off"><br>
        </div>
        <br>
        <div class = "forlabels">
            <?php
                if(isset($_SESSION["wp"])){
                    echo '<p>'.$_SESSION["wp"].'</p>';
                    unset($_SESSION["wp"]);
                }
            ?>
            <label for = "cpassword"><strong><i>Confirm Password: </i></strong></label>
            <input type = "password" name = "cpassword" id = "cpassword" required autocomplete = "off"><br>
        </div>
        <br>
        <a href = "login.php">Login Now!</a><input type="submit" value="Register" name="submit" id = "submit">
    </form>
    <script src = "JS/rjs.js"></script>
</body>
</html>