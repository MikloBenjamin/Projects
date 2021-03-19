
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel = "stylesheet" type = "text/css" href = "CSS/login.css">
</head>
<body onload = "rdy()">
    <?php
        if(isset($_GET["confirm"])){
            if($_GET["confirm"] == 0){
                unset($_GET["confirm"]);
            }
            else{
                echo "<div align = 'center'>Confirm your account</div>";
            }
        }
    ?>
    <div class = "container">
        <div id = "image">
            <img src="logo.png" id = "innerimg" width="20%" height="20%">
        </div>
        <form action = "loginuser.php?confirm="<?php if(isset($_GET["confirm"])) echo $_GET["confirm"];?> method = "POST" enctype="multipart/form-data" id = "loginform">
            <strong><i><u><h5 align = "center">Login</i></strong></u></h5>
            <div class = "forlabels">
                <label for = "username"><strong><i>Username: </i></strong></label>
                <input type = "text" name = "username" id = "username" required autocomplete = "off"><br>
            </div>
            <br>
            <div class = "forlabels">
                <label for = "password"><strong><i>Password: </i></strong></label>
                <input type = "password" name = "password" id = "password" required autocomplete = "off"><br>
            </div>
            <br>
            <a href = "register.php">Register Now</a><input type="submit" value="Login" name="submit" id = "submit">
        </form>

    </div>
    <script>
        function rdy(){
            var sp = document.getElementById("password");
            sp.addEventListener("mouseover", function(){
                sp.type = "text";
            })
            sp.addEventListener("mouseout", function(){
                sp.type = "password";
            })
        }
    </script>
</body>
</html>