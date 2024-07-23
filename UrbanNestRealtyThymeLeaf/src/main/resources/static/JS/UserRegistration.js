let codeNumber = localStorage.getItem('codeNumber') ? parseInt(localStorage.getItem('codeNumber')) : 1;

function promptForCode(option) {
    if (option === 'Admin') {
        let correctCode = "UrbanNest654386";
        let userCode = prompt("Please enter the code for " + option);

        if (userCode === correctCode) {
            document.getElementById('designationDropdown').textContent = option;
            document.getElementById('designationInput').value = option;
        } else {
            alert("Incorrect code. Please try again.");
        }
    } else {
        document.getElementById('designationDropdown').textContent = option;
        document.getElementById('designationInput').value = option;
    }
}

function autoFillDistrict()
{
	var district = document.getElementById('district');
	var selectedOption = district.options[district.selectedIndex];
	var districtName = selectedOption.value;
	
	document.getElementById('district').value = districtName;
}



function selectOption(option) {
    document.getElementById('designationDropdown').textContent = option;
    document.getElementById('designationInput').value = option;
}

function generateUserID() {
    let designation = document.getElementById('designationInput').value;
    let companyName = "UNR";
    let userID = companyName + '_' + designation + '_' + codeNumber;

    document.getElementById('generatedUserID').value = userID;
    document.getElementById('userIDLabel').textContent = "Please Note Generated User ID: " + userID;
    document.getElementById('userIDLabel').style.display = "block";

    codeNumber++;
    localStorage.setItem('codeNumber', codeNumber);
}

function validateForm() {
    let form = document.getElementById('registrationForm');
    let phoneNumber = document.getElementById('phoneNumber');
    let email = document.getElementById('emailID');
    let password = document.getElementById('password');

    
    if (!phoneNumber.checkValidity()) {
        alert("Please enter a valid phone number.");
        return;
    }

    if (!email.checkValidity()) {
        alert("Please enter a valid email address.");
        return;
    }

    if (!password.checkValidity()) {
        alert("Please enter a valid password.\nPassword must contain at least 8 characters including at least one uppercase letter, one lowercase letter, and one number.");
        return;
    }

    generateUserID();
    setTimeout(function(){ form.submit(); }, 5000);
}