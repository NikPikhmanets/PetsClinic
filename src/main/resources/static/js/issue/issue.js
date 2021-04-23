$(document).ready(function () {
    function fetchInfoAboutPet(petId) {
        $.ajax({
            type: "GET",
            url: `/pets/info/${petId}`,
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
            url: `/issues/info/${id}`,
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

    $("#addComment").submit(function (e) {
        e.preventDefault();
        let id = $('#issue-id').attr('value');

        $.ajax({
            type: "post",
            url: `/issues/${id}/visits`,
            data: {
                comment: $('#inputRecord').val()
            },
            success: function (data) {
                console.log('Submission was successful.');
                console.log(data);
            },
            error: function (data) {
                console.log('An error occurred.');
                console.log(data);
            }
        });
    });

    $("#closeIssue").submit(function (e) {
        e.preventDefault();
        let id = $('#issue-id').attr('value');

        $.ajax({
            type: "post",
            url: `/issues/${id}`,
            data: {
                status: "close"
            },
            success: function (data) {
                console.log('Submission was successful.');
                console.log(data);
                fetchIssue();
            },
            error: function (data) {
                console.log('An error occurred.');
                console.log(data);
            }
        });
    });

    (function () {
        fetchIssue();
    })();
});