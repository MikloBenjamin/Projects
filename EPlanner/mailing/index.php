<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;
use PHPMailer\PHPMailer\SMTP;

require 'Exception.php';
require 'PHPMailer.php';
require 'SMTP.php';
function send($to, $subject, $message){
    $mail = new PHPMailer();
    $mail->SMTPDebug = 0;
    $mail->Host = 'smtp.gmail.com';
    $mail->isSMTP();
    $mail->SMTPAuth = true;
    $mail->Username = 'miklo.beni1@gmail.com';
    $mail->Password   = 'volcvagen';
    $mail->SMTPSecure = 'ssl';
    $mail->Port       = 465;
    $mail->setFrom('miklo.beni1@gmail.com', 'Beni');
    $mail->addAddress($to, 'Beni');
    $mail->addReplyTo('miklo.beni1@gmail.com', 'Beni');
    $mail->isHTML(true);
    $mail->Subject = $subject;
    $mail->Body = $message;
    $mail->AltBody = '';
    $mail->send();
}
?>