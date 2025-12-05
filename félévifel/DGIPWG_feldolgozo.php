<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feldolgozó</title>
</head>
<body>
    <?php

    if (isset($_POST))
        {
            echo "<h2>Értékelés</h2>";

            $nev = $_POST["nev"];
            $pin = $_POST["pin"];
            $fav_termek = $_POST["fav_termek"];
            $confidence = $_POST["confidence"];

            echo "<p><strong>Név:</strong> " . $nev . "</p>";
            echo "<p><strong>PIN kód:</strong> " . $pin . "</p>";
            echo "<p><strong>Kedvenc termék:</strong> " . $fav_termek . "</p>";
            echo "<p><strong>Mennyire volt hasznos az oldal?:</strong> " . $confidence . "</p>";
        } else {
            echo "<h2><strong>Űrlap nem lett beküldve!</strong></h2>"; 
        }
        
        ?>

        <a href="kezdooldal.html"><strong>Vissza az oldalra.</strong></a>
</body>
</html>