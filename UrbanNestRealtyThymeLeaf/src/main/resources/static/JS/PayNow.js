function validateForm() {
    var yourAccountNumber = document.getElementById("yourAccountNumber").value;
    var senderAccountNumber = document.getElementById("senderAccountNumber").value;
    var amount = document.getElementById("amount").value;

    if (yourAccountNumber === senderAccountNumber) {
        alert("The account numbers must be different.");
        return false;
    }

    if (amount <= 0) {
        alert("The amount must be greater than zero.");
        return false;
    }

    return true;
}