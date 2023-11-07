$(document).ready(function() {

    function search(input, resultsContainer) {
        const query = $(input).val();

        $.get("/search", { query: query }, function(data) {
            let resultsHtml = '<ul>';

            data.forEach(artist => {
                resultsHtml += '<li class="search-result-item">' + artist + '</li>';
            });

            resultsHtml += '</ul>';

            $(resultsContainer).html(resultsHtml).show();
        });
    }


    function setupInput(inputSelector, resultsSelector, hiddenInputSelector) {
        var timeout = null;

            $(inputSelector).on('keyup', function() {
                clearTimeout(timeout);

                var inputValue = $(this).val();
                if (inputValue.length > 2) {
                    timeout = setTimeout(function() {
                        search(inputSelector, resultsSelector);
                    }, 100);
                }
            });

        $(document).on('click', resultsSelector + ' .search-result-item', function() {
            var selectedItemText = $(this).text();
            $(inputSelector).val(selectedItemText);
            $(hiddenInputSelector).val(selectedItemText);
            $(resultsSelector).hide();
        });
    }

    setupInput('#search-input1', '#search-results1', '#hiddenInput1');
    setupInput('#search-input2', '#search-results2', '#hiddenInput2');
    setupInput('#search-input3', '#search-results3', '#hiddenInput3');


});
