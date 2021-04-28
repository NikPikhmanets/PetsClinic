$(document).ready(function () {
    const FIRST_PAGE = 0;

    function fetchIssues(page) {
        let pageNumber = (typeof page !== 'undefined') ? page : FIRST_PAGE;
        let selectStatus = $('#selected-status').val();
        console.log(selectStatus);

        $.ajax({
            type: "GET",
            url: `/issues/assigned`,
            data: {
                page: pageNumber,
                status: selectStatus
            },
            success: function (response) {
                console.log(response)
                $(`#issue-table`).empty();

                function isShowButtonByStatus(item) {
                    if (item.statusIssue === 'NEW') {
                        return '<td><button class="btn btn-info update-status-in-progress" value="' + item.id + '">In Progress</button></td>';
                    }
                    if (item.statusIssue === 'IN_PROGRESS') {
                        return '<td><button class="btn btn-danger update-status-close" value="' + item.id + '">Close</button></td>';
                    }
                    return '<td class="pt-3">Closed</td>';
                }

                for (let i = 0; i < response.content.length; i++) {
                    let item = response.content[i];

                    $('#issue-table').append(
                        '<tr>' +
                        '   <th scope="row"><a href="/issues/' + item.id + '">' + (response.pageable.offset + i + 1) + ' </a></th>' +
                        '   <td class="w-75">' + item.description + '</td>' +
                        '   <td><a class="btn btn-secondary" href="/pets/' + item.petId + '">pet</a></td>' +
                        isShowButtonByStatus(item) +
                        '</tr>'
                    );
                }
                $('ul.pagination').buildPagination(response);
            },
            error: function (e) {
                console.log("Error fetchIssues: ", e);
            }
        });
    }

    $(document).on("click", ".update-status-in-progress", function () {
        let id = $(this).attr('value');
        console.log('progress: ' + id);
        updateStatus(`issues/${id}/status`, "IN_PROGRESS");
    });

    $(document).on("click", ".update-status-close", function () {
        let id = $(this).attr('value');
        console.log('close: ' + id);
        updateStatus(`issues/${id}/status`, "CLOSED");
    });

    function updateStatus(url, data) {
        $.ajax({
            type: "PUT",
            url: url,
            contentType: 'application/json',
            dataType: 'text',
            data: JSON.stringify(data),
            success: function () {
                fetchIssues(FIRST_PAGE);
            },
            error: function (e) {
                console.log("Error updateStatus: ", e);
                alert("Failed to update status")
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
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });

    $(`#selected-status`).change(function () {
        fetchIssues(FIRST_PAGE);
    });

    (function () {
        fetchIssues(FIRST_PAGE);
    })();
});