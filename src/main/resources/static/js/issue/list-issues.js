$(document).ready(function () {
    function fetchIssues(page) {
        let pageNumber = (typeof page !== 'undefined') ? page : 0;

        $.ajax({
            type: "GET",
            url: "/issues/new-list",
            data: {
                page: pageNumber
            },
            success: function (response) {
                console.log(response)
                $(`#issue-table`).empty();

                for (let i = 0; i < response.content.length; i++) {
                    let item = response.content[i];

                    $('#issue-table').append(
                        '<tr>' +
                        '   <th scope="row"><a href="/issues/' + item.id + '">' + (response.pageable.offset + i + 1) + ' </a></th>' +
                        '   <td>' + item.description + '</td>' +
                        '    <td><a class="btn btn-secondary" href="/pets/' + item.petId + '">Profile of pet</a></td>' +
                        '    <td><button class="btn btn-secondary assign" value="' + item.id + '">Assign to myself</button></td>' +
                        '</tr>'
                    );
                }
                $('ul.pagination').buildPagination(response);
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });
    }

    $(document).on("click", "ul.pagination li a", function () {
        let active = "li.active";
        let val = $(this).text();

        if (val.toUpperCase() === "NEXT") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            let totalPages = $("ul.pagination li").length - 2;

            if (activeValue < totalPages) {
                let currentActive = $(active);
                fetchIssues(activeValue);
                $(active).removeClass("active");
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "PREVIOUS") {
            let activeValue = parseInt($("ul.pagination li.active").text());

            if (activeValue > 1) {
                fetchIssues(activeValue - 2);
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                currentActive.prev().addClass("active");
            }
        } else {
            fetchIssues(parseInt(val) - 1);
            $(active).removeClass("active");
            $(this).parent().addClass("active");
        }
    });

    $(`#issue-table`).on('click', '.assign', function (e) {
        e.preventDefault();
        let issueId = $(this).attr('value');

        $.ajax({
            url: `/issues/${issueId}/assign`,
            type: 'PUT',
            success: function (data) {
                console.log(data.status);
                alert("Success assign");
                fetchIssues();
            },
            error: function (e) {
                console.log("ERROR: ", e);
                alert("Error assign");
            }
        });
    });

    (function () {
        fetchIssues(0);
    })();
});