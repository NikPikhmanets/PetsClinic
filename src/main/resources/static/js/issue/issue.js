$(document).ready(function () {
    function fetchInfoAboutPet(petId) {
        $.ajax({
            type: "GET",
            url: `/pets/info/${petId}`,
            success: function (pet) {
                console.log(pet);
                $('#name-pet').text(`   ${pet.name}`);
                $('#kind-of-pet').text(`    ${pet.kind}`);
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

                if (response.statusIssue === "CLOSED") {
                    $('#add-comment').hide();
                    $("#close-issue").hide();
                }

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

    $("#add-comment").submit(function (e) {
        e.preventDefault();
        let id = $('#issue-id').attr('value');

        $.ajax({
            type: "post",
            url: `/issues/${id}/visits`,
            data: {
                comment: $('#input-comment').val()
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

    $("#close-issue").submit(function (e) {
        e.preventDefault();
        let id = $('#issue-id').attr('value');

        $.ajax({
            type: "PUT",
            url: `${id}/status`,
            contentType: 'application/json',
            dataType: 'text',
            data: JSON.stringify("CLOSED"),
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