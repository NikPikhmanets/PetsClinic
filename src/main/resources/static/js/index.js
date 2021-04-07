function updateSelectInForm(element, data) {
    $(element).empty();
    $.each(data, function () {
        $(element).append('<option value="' + this.id + '">' + this.name + '</option>')
    })
}

function getDoctors() {
    $.ajax({
        url: "users/doctors",
        async: true,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            updateSelectInForm('#doctorsFormControlSelect', data);
        }
    });
}

function getKindsOfPet() {
    $.ajax({
        url: "pets/kinds",
        async: true,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            updateSelectInForm('#petsFormControlSelect', data);
        }
    });
}

$(document).ready(function () {
    getDoctors();
    getKindsOfPet();
});
