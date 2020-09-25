<?php
	session_start();
    if(!isset($_SESSION['admin'])){
        $_SESSION['msg'] = "Brak uprawnień!";
        header('location: index.php');

      }
	if (isset($_GET['logout']))
    {
		session_destroy();
		unset($_SESSION['email']);
		header("location: index.php");
    }

    $errors = array();


$conn = mysqli_connect(

    "localhost",
    "root",
    "",
    "clinic"

);
?>
<?php
if(isset($_POST['updateUser']))
{
        $id = $_POST['id'];
        $name = mysqli_real_escape_string($conn, $_POST['nameId']);
        $surname = mysqli_real_escape_string($conn, $_POST['surnameId']);		
        $pesel_number = mysqli_real_escape_string($conn, $_POST['pesel']);
		$street = mysqli_real_escape_string($conn, $_POST['street']);
		$zip_code = mysqli_real_escape_string($conn, $_POST['zip_code']);
		$city = mysqli_real_escape_string($conn, $_POST['city']);
		$phone_number = mysqli_real_escape_string($conn, $_POST['phoneNumber']);

        if (empty($id)) { array_push($errors, "ID jest wymagane"); }
		if (empty($name)) { array_push($errors, "Imię jest wymagane"); }
		if (empty($surname)) { array_push($errors, "Nazwisko jest wymagane"); }
		if (empty($pesel_number)) { array_push($errors, "PESEL jest wymagany"); }
        if (empty($street)) { array_push($errors, "Ulica jest wymagana"); }
        if (empty($zip_code)) { array_push($errors, "Kod pocztowy jest wymagany"); }
        if (empty($city)) { array_push($errors, "Miasto jest wymagane"); }
        if (empty($phone_number)) { array_push($errors, "Numer telefonu jest wymagany"); }

        if (count($errors) == 0) {
            $sql = "UPDATE private_data SET name='$name', surname='$surname', pesel_number='$pesel_number', address_street='$street', address_zipcode='$zip_code', address_city='$city',phone_number='$phone_number' where user_id='$id'";
            $result = $conn->query($sql);

        header('Location: userDataManagment.php');

}
}
?>

<?php
if(isset($_POST['deleteBtn'])){
    $id = $_POST['deleteId'];

    $sql = "DELETE FROM account_type WHERE id_account = '$id'";
    $sql1 = "DELETE FROM private_data WHERE user_id = '$id'";
    $sql2 = "DELETE FROM users WHERE id_user = '$id'";
    $result = $conn->query($sql);
    $result1 = $conn->query($sql1);
    $result2 = $conn->query($sql2);

        header('Location: userManagment.php');

}
?>

