<?php
session_start();
$user = $_SESSION["user"];
unset($_SESSION);
session_destroy();
session_write_close();
$db = "miklo_benjamin_eplanner";
$connection = new mysqli("localhost","root","",$db);

$sql = "DELETE FROM gevents";
$bto = "location: ../login.php";
if(substr($user, 0, 5) == "guest"){
    $connection->query($sql);
    $connection->query("DELETE FROM gusers WHERE gname = '".$user."'");
    $bto = "location: ../index.php";
}
header($bto);
$connection->close();
exit;
?>