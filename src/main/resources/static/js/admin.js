$(document).ready(function () {
    const FIRST_PAGE = 0;

    function fetchUsers(page) {
        let pageNumber = (typeof page !== 'undefined') ? page : FIRST_PAGE;
        let selected = $(`#selected-users`).val();

        if (selected === 'DOCTOR') {
            fetch('/users/doctors', function (data) {
                for (let i = 0; i < data.content.length; i++) {
                    let item = data.content[i];

                    $('#table-of-users').append(
                        '<tr>' +
                        '   <th scope="row">' + (data.pageable.offset + i + 1) + '</th>' +
                        '   <td>' + item.name + " " + item.surname + '</td>' +
                        '   <td>' + item.specialty + '</td>' +
                        '   <td><button value ="' + item.id + '" type="button" class="btn btn-secondary disable-role">Change</button></td>' +
                        '</tr>'
                    );
                }
            });
        } else if (selected === 'USER') {
            fetch('/users', function (data) {
                for (let i = 0; i < data.content.length; i++) {
                    let item = data.content[i];

                    $('#table-of-users').append(
                        '<tr>' +
                        '   <th scope="row">' + (data.pageable.offset + i + 1) + '</th>' +
                        '   <td>' + item.name + " " + item.surname + '</td>' +
                        '   <td class="w-25">' +
                        '       <label class="w-100">' +
                        '           <select id="selected-' + item.id + '" class="custom-select d-flex justify-content-center specialty-selected">' +
                        '           </select>' +
                        '        </label>' +
                        '   </td> ' +
                        '   <td><button value ="' + item.id + '" type="button" class="btn btn-secondary enable-role">Set</button></td>' +
                        '</tr>'
                    );
                }
                setSpecialties();
            });
        }

        function setSpecialties() {
            $.ajax({
                url: "specialties",
                async: true,
                dataType: 'json',
                success: function (data) {
                    console.log(data);

                    document.querySelectorAll('.specialty-selected').forEach(function (node) {
                        for (let i = 0; i < data.length; i++) {
                            let opt = document.createElement('option');
                            opt.value = data[i].id;
                            opt.innerHTML = data[i].name;
                            node.appendChild(opt);
                        }
                    });
                },
                error: function () {
                    console.log("ERROR: ", e);
                }
            });
        }

        function fetch(url, build) {
            $.ajax({
                url: url,
                async: true,
                dataType: 'json',
                data: {
                    page: pageNumber
                },
                success: function (data) {
                    console.log(data);
                    $(`#table-of-users`).empty();
                    build(data);
                    $('ul.pagination').buildPagination(data);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                }
            });
        }
    }

    fetchUsers();

    $(document).on("click", "ul.pagination li a", function () {
        let active = "li.active";

        let val = $(this).text();

        if (val.toUpperCase() === "NEXT") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            let totalPages = $("ul.pagination li").length - 2;

            if (activeValue < totalPages) {
                let currentActive = $(active);
                fetchUsers(activeValue);
                $(active).removeClass("active");
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "PREVIOUS") {
            let activeValue = parseInt($("ul.pagination li.active").text());

            if (activeValue > 1) {
                fetchUsers(activeValue - 2);
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                currentActive.prev().addClass("active");
            }
        } else {
            fetchUsers(parseInt(val) - 1);
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });

    $(document).on("click", ".disable-role", function () {
        let id = $(this).attr('value');
        updateRole(`users/${id}/disable-role-doctor`);
    });

    $(document).on("click", ".enable-role", function () {
        let id = $(this).attr('value');
        let element = document.getElementById(`selected-${id}`);
        let specialtyId = element.value;
        updateRole(`users/${id}/enable-role-doctor/specialty/${specialtyId}`);
    });

    function updateRole(url) {
        $.ajax({
            type: "PUT",
            url: url,
            contentType: 'application/json',
            success: function () {
                fetchUsers();
            },
            error: function (e) {
                console.log("Error updateStatus: ", e);
                alert("Failed to update role of User")
            }
        });
    }

    $(`#selected-users`).change(function () {
        fetchUsers();
    });

    (function () {
        fetchUsers();
    })();
});