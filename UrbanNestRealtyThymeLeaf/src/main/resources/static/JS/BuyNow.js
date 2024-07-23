document.addEventListener("DOMContentLoaded", function() {
        var propertyPrice = parseFloat(document.getElementById("propertyPrice").value);
        var payableAmountInput = document.getElementById("payableAmount");

        if (propertyPrice) {
            var payableAmount = propertyPrice / 2;
            payableAmountInput.value = payableAmount.toFixed(2);
        }
    });