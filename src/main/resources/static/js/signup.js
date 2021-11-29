/**
 * Utility file for the page signup
 */


 function onSubmit(token) {
    $('#signupForm').append('<input type="text" name="token" value="'+token+'" />');
    $('signupForm').submit();
 }