$(document).ready(function () {
    $('#headingOne').on('click', function () {
        $('.collapse').toggleClass('show');
    });

    getKindsForForm();
    getPetsOfUser();

    function getKindsForForm() {
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
    }

    function getPetsOfUser() {
        $.ajax({
            url: "/pets",
            async: true,
            dataType: 'json',
            success: function (data) {
                console.log(data);
                buildTableOfPets(data);
            }
        });
    }

    function buildTableOfPets(data) {
        $(`#tableOfPets`).empty();

        for (let i = 0; i < data.length; i++) {
            $('#tableOfPets').append(
                '        <tr>' +
                '            <th scope="row">' + (i + 1) + ' </th>' +
                '            <td><a href="' + '/pets/' + data[i].id + '">' + data[i].name + '</a></td>' +
                '            <td>' + data[i].kindOfPet.name + '</td>' +
                '        </tr>'
            );
        }
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
                getPetsOfUser();
            },
            error: function (data) {
                console.log('An error occurred.');
                console.log(data);
            }
        });
    });

    function getDataForm() {
        return {
            name: $("#inputNameOfPet").val(),
            kindId: $("#petsFormControlSelect").val()
        };
    }
});