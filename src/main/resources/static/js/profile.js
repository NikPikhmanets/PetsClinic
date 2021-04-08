$(document).ready(function () {
    $('#headingOne').on('click', function () {
        $('.collapse').toggleClass('show');
    });

    $.ajax({
        url: "/pets/kinds",
        async: true,
        dataType: 'json',
        success: function (data) {
            console.log(data);

            $('#petsFormControlSelect').empty();
            $.each(data, function () {
                $('#petsFormControlSelect').append('<option value="' + this.id + '">' + this.name + '</option>');
            })
        }
    });

    function getDataForm() {
        return {
            name: $("#inputNameOfPet").val(),
            kindId: $("#petsFormControlSelect").val()
        };
    }

    $("#addPetForm").submit(function (e) {
        e.preventDefault();
        let formData = getDataForm();
        let url = $(this).attr('action');

        $.ajax({
            type: "post",
            url: url,
            data: formData,
            success: function (data) {
                console.log('Submission was successful.');
                console.log(data);
                $(`#tableOfPets`).empty();

                for (let i = 0; i < data.length; i++) {
                    $('#tableOfPets').append(
                        '        <tr>' +
                        '            <th scope="row">' + (i + 1) + ' </th>' +
                        '            <td><a href="' + '/pets/' + i + '">' + data[i].name + '</a></td>' +
                        '            <td>' + data[i].kindId + '</td>' +
                        '        </tr>'
                    );
                }
            },
            error: function (data) {
                console.log('An error occurred.');
                console.log(data);
            }
        });
    });
});