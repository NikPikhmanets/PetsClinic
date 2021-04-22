$(document).ready(function () {
    function getPetOfUser() {
        $.ajax({
            url: "pets",
            async: true,
            dataType: 'json',
            success: function (data) {
                console.log(data);
                updateSelectInForm('#petsFormControlSelect', data);
            },
            error: function (data) {
                $("#petGroup").hide();
            }
        });
    }

    function updateSelectInForm(element, data) {
        $(element).empty();
        $.each(data, function () {
            $(element).append('<option value="' + this.id + '">' + this.name + '</option>')
        })
    }

    function submitForm() {
        function getDataForm() {
            return {
                petId: $("#petsFormControlSelect").val(),
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
            $("#errorDescription").remove();
            let formData = getDataForm();

            if (isEmptyDescription(formData)) {
                return;
            }
            let url = $(this).attr('action');

            $.ajax({
                type: "post",
                url: url,
                data: formData,
                success: function (data) {
                    console.log('Submission was successful.');
                    console.log(data);

                    $("#recordsForm").empty().append(
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

    getPetOfUser();
    submitForm();
});
