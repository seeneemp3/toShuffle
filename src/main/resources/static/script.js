function toSearch() {
    document.getElementById("welcomePage").style.display = "none";
    document.getElementById("searchPage").style.display = "block";
  }

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
      
        // Mock data array with three items
        const mockData = ['Artist 1', 'Artist 2', 'Artist 3'];
      
        // Generating the results HTML with three mocked items
        let resultsHtml = '<ul>';
      
        mockData.forEach(artist => {
          resultsHtml += '<li class="search-result-item">' + artist + '</li>';
        });
      
        resultsHtml += '</ul>';
      
        // Set the inner HTML of the results container
        $(resultsContainer).html(resultsHtml).show();
      }
      
/*
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
      */

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
          
          $(document).on('click', function(e) {
            if (!$(e.target).closest(resultsSelector).length && !$(e.target).is(inputSelector)) {
              $(resultsSelector).hide(); 
            }
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
        document.getElementById("showPlaylistPage").style.display = "flex";
        event.preventDefault();

        // Используйте мок данные
        var data = getMockTracks();
        parseAndDisplayResults(data);
    });

    function getMockTracks() {
        return JSON.stringify([
            { name: "Трек 1", artists: [{ name: "Артист 1" }] },
            { name: "Трек 2", artists: [{ name: "Артист 2" }] },
            { name: "Трек 1", artists: [{ name: "Артист 1" }] },
            { name: "Трек 2", artists: [{ name: "Артист 2" }] },
            { name: "Трек 1", artists: [{ name: "Артист 1" }] },
            { name: "Трек 2", artists: [{ name: "Артист 2" }] },
            { name: "Трек 1", artists: [{ name: "Артист 1" }] },
            { name: "Трек 2", artists: [{ name: "Артист 2" }] },
            { name: "Трек 1", artists: [{ name: "Артист 1" }] },
            { name: "Трек 1", artists: [{ name: "Артист 1" }] },
            { name: "Трек 2", artists: [{ name: "Артист 2" }] },
            { name: "Трек 1", artists: [{ name: "Артист 1" }] },
            { name: "Трек 2", artists: [{ name: "Артист 2" }] },
            { name: "Трек 1", artists: [{ name: "Артист 1" }] },
            { name: "Трек 2", artists: [{ name: "Артист 2" }] }

        ]);
    }

    function parseAndDisplayResults(data) {
        var tracks = JSON.parse(data);
        var resultsHtml = '<div class="scrollable-list">';
      
        tracks.forEach(function(track) {
          resultsHtml += '<div class="track">' + 
                         '<div class="track-info">' + 
                         '<p class="track-name">' + track.name + '</p>' +
                         '<p class="artist-name">' + track.artists.map(artist => artist.name).join(', ') + '</p>' +
                         '</div>' +
                         '</div>';
        });
        resultsHtml += '</div>';

        var playlistPage = document.getElementById('showPlaylistPage');
        var button = playlistPage.querySelector('button'); // Предполагаем, что кнопка - это элемент button
        
        // Вставка результатов перед кнопкой
        if (button) {
          button.insertAdjacentHTML('beforebegin', resultsHtml);
        } else {
          // Если кнопки нет, просто добавляем HTML в конец 'showPlaylistPage'
          playlistPage.insertAdjacentHTML('beforeend', resultsHtml);
        }

        //document.getElementById('showPlaylistPage').insertAdjacentHTML('beforeend', resultsHtml);
      }
      
});


//   document.addEventListener('DOMContentLoaded', function() {
//       var form = document.getElementById('sendInputs');
//       var submitButton = document.getElementById('submit-button');

//       submitButton.addEventListener('click', function(event) {
//           document.getElementById("searchPage").style.display = "none";
//           document.getElementById("showPlaylistPage").style.display = "block";
//           event.preventDefault();

//           var queryParams = new URLSearchParams(new FormData(form)).toString();
//           var requestUrl = form.action + '?' + queryParams;

//           fetch(requestUrl).then(response => {
//               if(response.ok) {
//                   return response.text();
//               }
//               throw new Error('Сетевая ошибка при запросе');
//           }).then(data => {

//               console.log(data);

//               parseAndDisplayResults(data);
//           }).catch(error => {
//               console.error('Ошибка:', error);
//           });
//       });

//       function parseAndDisplayResults(data) {
//           var tracks = JSON.parse(data);
//           var resultsHtml = '<ul>';

//           tracks.forEach(function(track) {
//               resultsHtml += '<li>' +
//                              '<strong>Трек:</strong> ' + track.name + '<br>' +
//                              '<strong>Артист:</strong> ' + track.artists.map(artist => artist.name).join(', ') +
//                              '</li>';
//           });
//           resultsHtml += '</ul>';
//           document.getElementById('showPlaylistPage').insertAdjacentHTML('beforeend', resultsHtml);

//       }

//   });


