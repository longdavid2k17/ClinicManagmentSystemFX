<?php
  session_start();

$conn = mysqli_connect(

    "localhost",
    "root",
    "",
    "clinic"

);
if(isset($_POST['editBtn']))
{
    $id = mysqli_real_escape_string($conn, $_POST['editId']);
    
    $sql_private_data = "SELECT * FROM private_data WHERE user_id='$id'";

    $result = $conn->query($sql_private_data);
}



?>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Klinika Sunshine</title>
  <link rel="icon" href="favicon.ico">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</head>
<body>
<div class="container" >
    <div class="row">
    <div class="col"></div>
    <div class="col">
    <center>
    <div class="table-responsive">
    <table class='table table-striped table-bordered table-hover'>
    <tr>
        <th>ID</th>
        <th>Imię</th>
        <th>Nazwisko</th>
        <th>Numer PESEL</th>
        <th>Ulica</th>
        <th>Kod pocztowy</th>
        <th>Miasto</th>
        <th>Numer telefonu</th>
        <th>Usuń</th>
        <th>Edytuj</th>
    </tr>
    <?php
    if ($result->num_rows > 0) 
    {
        while($row = $result->fetch_assoc()) 
        {
            echo "<tr><td>"
            .$row["user_id"]."</td><td>"
            .$row["name"]."</td><td>"
            .$row["surname"]."</td><td>"
            .$row["pesel_number"]."</td><td>"
            .$row["address_street"]."</td><td>"
            .$row["address_zipcode"]."</td><td>"
            .$row["address_city"]."</td><td>"
            .$row["phone_number"]."</td>";
            ?>
        
            </td></tr>
        </table>
    </div>
        <div class="div-center col-sm-3 shadow p-3 mb-5 bg-white rounded mt-5">
             <form method="post" action="updateAndDelete.php">
                 <div class="form-group">
                <label >ID</label>
                <input type="text" class="form-control" name="id"  placeholder="ID" value="<?php echo $row['user_id']?>">
              </div>
              <div class="form-group">
                <label >Imię</label>
                <input type="text" class="form-control" name="nameId"  placeholder="Imię" value="<?php echo $row['name']?>">
              </div>
                 <div class="form-group">
                <label >Nazwisko</label>
                <input type="text" class="form-control" name="surnameId"  placeholder="Nazwisko" value="<?php echo $row['surname']?>">
              </div>
              <div class="form-group">
                <label >Numer PESEL</label>
                <input type="text" class="form-control" name="pesel"  placeholder="PESEL" value="<?php echo $row['pesel_number']?>">
              </div>
                 <div class="form-group">
                <label >Ulica</label>
                <input type="text" class="form-control" name="street"  placeholder="Ulica" value="<?php echo $row['address_street']?>">
              </div>
                 
                 <div class="form-group">
                 <label >Kod Pocztowy</label>
                <input type="text" class="form-control" name="zip_code"  placeholder="Kod pocztowy" value="<?php echo $row['address_zipcode']?>">
                </div>
                 <div class="form-group">
                 <label >Miasto</label>
                <input type="text" class="form-control" name="city"  placeholder="Miasto" value="<?php echo $row['address_city']?>">
                 </div>
                 <label >Numer telefonu</label>
                <input type="text" class="form-control" name="phoneNumber"  placeholder="Miasto" value="<?php echo $row['phone_number']?>">
              </div>
              <button type="submit" class="btn btn-primary" name="updateUser">Zapisz</button>
            </form>
            </div>
            <?php
        }
      } 
      else 
      {
        echo "0 results";
      }
    ?>
        <hr>
    <center>
            <a href="userManagment.php"><button class='btn btn-success' type="submit" name="dataTable">Zarządzaj użytkownikami</button></a>
    </center>
    </div>
    <div class="col"></div>
    </div>
</div>
</body>
</html>