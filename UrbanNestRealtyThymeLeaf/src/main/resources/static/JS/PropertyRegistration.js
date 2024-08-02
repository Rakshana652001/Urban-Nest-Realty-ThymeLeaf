function autoFillPropertyDetails() 
{
    var propertySelect = document.getElementById('propertyNameSelect');
    var selectedOption = propertySelect.options[propertySelect.selectedIndex];
    var propertyId = selectedOption.value;

    document.getElementById('propertyId').value = propertyId;
}

function autoFillPropertyDistrict()
{
	var propertyDistrict = document.getElementById('propertyDistrict');
	var selectedOptions = propertyDistrict.options[propertyDistrict.selectedIndex];
	var propertyDistricts = selectedOptions.value;
	
	document.getElementById('propertyDistrict').value = propertyDistricts;
}
function validateForm()
{
    var propertyNameSelect = document.getElementById('propertyNameSelect').value;
    var propertyPrice = document.getElementById('propertyPrice').value;
    var propertyAddress = document.getElementById('propertyAddress').value;
    var propertyDistrict = document.getElementById('propertyDistrict').value;
    var propertyState = document.getElementById('propertyState').value;

    if (propertyNameSelect === "" || propertyPrice === "" || propertyAddress === "" || propertyDistrict === "" || propertyState === "")
    {
        alert("Please fill in all required fields.");
        return false;
    }

    return true;
}
function calculatePayableAmount()
{
    var propertyPrice = parseFloat(document.getElementById('propertyPrice').value);
    var payableAmountInput = document.getElementById('payableAmount');

    if (!isNaN(propertyPrice)) {
        var payableAmount = propertyPrice / 2;
        payableAmountInput.value = payableAmount.toFixed(2);
    } else {
        payableAmountInput.value = '';
    }
}