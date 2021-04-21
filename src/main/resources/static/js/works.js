$(document).ready(function () {
    function fetchIssues(page) {
        let pageNumber = (typeof page !== 'undefined') ? page : 0;

        $.ajax({
            type: "GET",
            url: "/issues/assigned/NEW",
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
                        '    <td><a class="btn btn-primary" href="/pets/' + item.petId + '">pet</a></td>' +
                        '</tr>'
                    );
                }

                if ($('ul.pagination li').length - 2 !== response.totalPages) {
                    $('ul.pagination').empty();
                    buildPagination(response);
                }
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });
    }

    function buildPagination(response) {
        let totalPages = response.totalPages;

        let pagination = "ul.pagination";

        let pageIndex = '<li class="page-item"><a class="page-link">Previous</a></li>';
        $(pagination).append(pageIndex);

        for (let i = 1; i <= totalPages; i++) {
            if (i === 1) {
                pageIndex = "<li class='page-item active'><a class='page-link'>" + i + "</a></li>"
            } else {
                pageIndex = "<li class='page-item'><a class='page-link'>" + i + "</a></li>"
            }
            $("ul.pagination").append(pageIndex);
        }

        pageIndex = '<li class="page-item"><a class="page-link">Next</a></li>';
        $(pagination).append(pageIndex);
    }

    $(document).on("click", "ul.pagination li a", function () {
        let desc = false;
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

    (function () {
        fetchIssues(0);
    })();
});