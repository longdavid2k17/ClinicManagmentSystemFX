<?php
	session_start();

	$email    = "";
	$idUzytkownik    = "";
	$errors = array();
	$_SESSION['success'] = "";


	$db = mysqli_connect('localhost', 'root', '', 'clinic');


	if (isset($_POST['log_user']))
    {
		$login = mysqli_real_escape_string($db, $_POST['loginId']);
		$password = mysqli_real_escape_string($db, $_POST['passwordId']);

		if (empty($login))
        {
			array_push($errors, "Login jest wymagany");
		}

		if (empty($password))
        {
			array_push($errors, "Hasło jest wymagane");
		}

		if (count($errors) == 0)
        {
			$query = "SELECT * FROM users WHERE login='$login' AND password='$password';";
			$results = mysqli_query($db, $query);
            
            $pobranieIDkwerenda = "SELECT id_user FROM users WHERE login='$login'";
            $wyslaniekwerendy = mysqli_query($db,$pobranieIDkwerenda);
            $pobierzID = mysqli_fetch_assoc($wyslaniekwerendy);
            $idUzytkownik = $pobierzID['id_user'];

			$roleCheck = "SELECT account_type FROM account_type where id_account = '$idUzytkownik'";
			$checkResult = mysqli_query($db,$roleCheck);
			$row = mysqli_fetch_assoc($checkResult);


			if (mysqli_num_rows($results) == 1)
            {
				if($row['account_type'] == "admin")
                {
					header('location: userManagment.php');
                    $_SESSION['admin'] = true;
				}
				else
                {
					
                    array_push($errors, "Nie masz uprawnień do przeglądania tej strony!");
				}
			}
            else
            {
				array_push($errors, "Niepoprawne dane logowania! Podane dane logowania Login: '$login' Hasło: '$password'");
			}
		}
	}

?>