function updateSelectInForm(element, data) {
    $(element).empty();
    $.each(data, function () {
        $(element).append('<option value="' + this.id + '">' + this.name + '</option>')
    })
}

function getDoctors() {
    function updateSlider(data) {
        let wrapper = '.slider-wrapper';
        $(wrapper).children().remove();

        for (const item of data) {
            $(wrapper).append(
                '<div class="about-item text-center"> ' +
                '     <img src="https://nationwide-energy.co.uk/wp-content/uploads/2017/07/blank-avatar.jpg"' +
                '          alt="void">' +
                '         <p class="about-name">' + data.name + '</p>' +
                '         <p class="about-speciality">' + data.email + '</p>' +
                ' </div>'
            );
        }
    }

    $.ajax({
        url: "users/doctors",
        async: true,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            updateSelectInForm('#doctorsFormControlSelect', data);
            updateSlider(data);
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
