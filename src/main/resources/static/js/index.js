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

function submitForm() {
    function clearError() {
        $("#errorDescription").remove();
    }

    function getDataForm() {
        return {
            id: null,
            doctorId: $("#doctorsFormControlSelect").val(),
            kindOfPetId: $("#petsFormControlSelect").val(),
            description: $("#description").val().toString(),
        };
    }

    function isEmptyDescription(formData) {
        if (formData.description === "") {
            $("#descriptionGroup").append(
                '  <span id="errorDescription" style="color: red">This field is required.</span>'
            );
            return true;
        }
    }

    $("#recordsForm").submit(function (e) {
        e.preventDefault();
        clearError();
        let formData = getDataForm();

        if (isEmptyDescription(formData)) {
            return;
        }
        let url = $(this).attr('action');

        $.ajax({
            type: "post",
            url: url,
            dataType: "json",
            data: formData,
            success: function (data) {
                console.log('Submission was successful.');
                console.log(data);

                $(this).empty();
                $(this).append(
                    '<span class="list-group-item list-group-item-action list-group-item-success">Success!</span>'
                );
            },
            error: function (data) {
                console.log('An error occurred.');
                console.log(data);
            }
        });
    });
}

$(document).ready(function () {
    getDoctors();
    getKindsOfPet();
    submitForm();
});
