(function ($) {
        $.fn.buildPagination = function (data) {
            let page = $.extend(data);

            function main(e) {
                if (e.children().length - 2 !== page.totalPages) {
                    e.empty();

                    let totalPages = page.totalPages;
                    let pageIndex = '<li class="page-item"><a class="page-link">Previous</a></li>';
                    e.append(pageIndex);

                    for (let i = 1; i <= totalPages; i++) {
                        if (i === 1) {
                            pageIndex = `<li class='page-item active'><a class='page-link'>${i}</a></li>`
                        } else {
                            pageIndex = `<li class='page-item'><a class='page-link'>${i}</a></li>`
                        }
                        e.append(pageIndex);
                    }
                    pageIndex = '<li class="page-item"><a class="page-link">Next</a></li>';
                    e.append(pageIndex);
                }
            }

            this.each(function () {
                main($(this));
            });
            return this;
        }
    }
)(jQuery);