$(document).ready(function () {
    function fetchInfoAboutPet(petId) {
        $.ajax({
            type: "GET",
            url: "/pets/" + petId,
            success: function (pet) {
                console.log(pet);
                $('#petInfo').text(`Pet: ${pet.name} (${pet.kind})`);
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });
    }

    function fetchIssue() {
        let id = $('#issue-id').attr('value');

        $.ajax({
            type: "GET",
            url: "/issues/info/" + id,
            success: function (response) {
                console.log(response)
                fetchInfoAboutPet(response.petId);

                $(`#visits-table`).empty();
                let history = response.visits;

                for (let i = 0; i < history.length; i++) {
                    let item = history[i];

                    $('#visits-table').append(
                        '<tr>' +
                        '   <th scope="row">' + item.id + '</th>' +
                        '   <td>' + item.commentOfDoctor + '</td>' +
                        '   <td>' + item.dateTime.split("T") + '</td>' +
                        '</tr>'
                    );
                }
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });
    }

    (function () {
        fetchIssue();
    })();
});