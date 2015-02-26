<%-- 
    Document   : index
    Created on : Feb 25, 2015, 5:09:27 PM
    Author     : cvadmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hotels</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    <h1>Hotel Information</h1>
    <p>Enter Your Hotel Information Below</p>
    <p><i>${message}</i></p>
    <form action="..\Source Packages\hotel.maven.model\HotelsController.java" method="post">
        <input type="hidden" name="action" value="add">        
        <label class="pad_top">Hotel ID:</label>
        <input type="hotelId" name="hotelId" value="${hotel.hotelId}"><br>
        <label class="pad_top">Hotel Name:</label>
        <input type="text" name="firstName" value="${hotel.HotelName}"><br>
        <label class="pad_top">Street Address:</label>
        <input type="text" name="streetAddress" value="${hotel.streetAddress}"><br>
        <label class="pad_top">City:</label>
        <input type="text" name="city" value="${hotel.city}"><br>  
        <label class="pad_top">State:</label>
        <input type="text" name="state" value="${hotel.state}"><br>  
        <label class="pad_top">Postal Code:</label>
        <input type="text" name="postalCode" value="${hotel.postalCode}"><br>  
        <label class="pad_top">Notes:</label>
        <input type="text" name="notes" value="${hotel.notes}"><br>  
        <label>&nbsp;</label>
        <input type="submit" value="Add Now" class="margin_left">
    </form>

</body>
</html>