function toSearch() {
    document.getElementById("welcomePage").style.display = "none";
    document.getElementById("searchPage").style.display = "block";
    document.getElementById("showPlaylistPage").style.display = "none";
  }

//function toShow() {
//    document.getElementById("searchPage").style.display = "none";
//    document.getElementById("showPlaylistPage").style.display = "block";
//}

  function toFinish() {
    document.getElementById("showPlaylistPage").style.display = "none";
    document.getElementById("finishPage").style.display = "block";
  }

  function toWelcome() {
    document.getElementById("finishPage").style.display = "none";
    document.getElementById("welcomePage").style.display = "block";
  }

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

      function setupInput(inputSelector, resultsSelector) {
          var timeout = null;

              $(inputSelector).on('keyup', function() {
                  clearTimeout(timeout);

                  var inputValue = $(this).val();
                  if (inputValue.length > 2) {
                      timeout = setTimeout(function() {
                          search(inputSelector, resultsSelector);
                      }, 550);
                  }
              });

          $(document).on('click', resultsSelector + ' .search-result-item', function() {
              var selectedItemText = $(this).text();
              $(inputSelector).val(selectedItemText);
              $(resultsSelector).hide();
          });
      }

      setupInput('#search-input1', '#search-results1');
      setupInput('#search-input2', '#search-results2');
      setupInput('#search-input3', '#search-results3');
  });

  document.addEventListener('DOMContentLoaded', function() {
      var form = document.getElementById('sendInputs');
      var submitButton = document.getElementById('submit-button');

      submitButton.addEventListener('click', function(event) {
          document.getElementById("searchPage").style.display = "none";
          document.getElementById("showPlaylistPage").style.display = "block";
          event.preventDefault();

          var queryParams = new URLSearchParams(new FormData(form)).toString();
          var requestUrl = form.action + '?' + queryParams;

          fetch(requestUrl).then(response => {
              if(response.ok) {
                  return response.text();
              }
              throw new Error('Сетевая ошибка при запросе');
          }).then(data => {

              console.log(data);

              parseAndDisplayResults(data);
          }).catch(error => {
              console.error('Ошибка:', error);
          });
      });

      function parseAndDisplayResults(data) {
          var tracks = JSON.parse(data);
          var resultsHtml = '<ul>';

          tracks.forEach(function(track) {
              resultsHtml += '<li>' +
                             '<strong>Трек:</strong> ' + track.name + '<br>' +
                             '<strong>Артист:</strong> ' + track.artists.map(artist => artist.name).join(', ') +
                             '</li>';
          });
          resultsHtml += '</ul>';
          document.getElementById('showPlaylistPage').insertAdjacentHTML('beforeend', resultsHtml);

      }

  });


