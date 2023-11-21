function toSearch() {
    document.getElementById("welcomePage").style.display = "none";
    document.getElementById("searchPage").style.display = "block";
  }

toFinish();
function toFinish() {
    document.getElementById('button3').addEventListener('click', function() {
        fetch('/createPlaylist', {
            method: 'GET'
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
            throw new Error('Request error');
        })
        .then(data => {
            console.log('Server response:', data);
            document.getElementById("playlistDescription").textContent =
                "Your playlist is ready. It can be found by the name \"" + data + "\"";
            document.getElementById("showPlaylistPage").style.display = "none";
            document.getElementById("finishPage").style.display = "block";
        })
        .catch(error => {
            window.location.href = '/error';
        });
    });
}


  function toWelcome() {
    location.reload();
  }

  $(document).ready(function() {
    
   /* //MOCK
    function search(input, resultsContainer) {
        const query = $(input).val();
      
        const mockData = ['Artist 1', 'Artist 2', 'Artist 3'];
      
        let resultsHtml = '<ul>';
      
        mockData.forEach(artist => {
          resultsHtml += '<li class="search-result-item">' + artist + '</li>';
        });
      
        resultsHtml += '</ul>';
      
        $(resultsContainer).html(resultsHtml).show();
      }
    */ //MOCK
    
      

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

 

/* //MOCK
document.addEventListener('DOMContentLoaded', function() {
    var form = document.getElementById('sendInputs');
    var submitButton = document.getElementById('submit-button');



    submitButton.addEventListener('click', function(event) {
      var allInputsFilled = true;
      $('input[type="text"]').each(function() {
          if ($(this).val() === '') {
              allInputsFilled = false;
              return false; 
          }
      });
  
      if (!allInputsFilled) {
        alert('Please fill in all the fields before continuing.'); 
        event.preventDefault(); 
        return; 
    }

        document.getElementById("searchPage").style.display = "none";
        document.getElementById("showPlaylistPage").style.display = "flex";
        event.preventDefault();

        var data = getMockTracks();
        parseAndDisplayResults(data);
    });

    function getMockTracks() {
        return JSON.stringify([
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 1", artists: [{ name: "АртистАртистАртистАртистАртистАртистАртистАртистАртистАртистАртистАртист 1" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 2", artists: [{ name: "АртистАртистАртистАртистАртистАртистАртистАртистАртистАртистАртистАртист 2" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 1", artists: [{ name: "АртистАртистАртистАртистАртистАртистАртистАртистАртистАртистАртистАртист 1" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 2", artists: [{ name: "АртистАртистАртистАртистАртистАртистАртистАртистАртистАртистАртистАртист 2" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 1", artists: [{ name: "Артист 1" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 2", artists: [{ name: "Артист 2" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 1", artists: [{ name: "Артист 1" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 2", artists: [{ name: "Артист 2" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 1", artists: [{ name: "Артист 1" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 1", artists: [{ name: "Артист 1" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 2", artists: [{ name: "Артист 2" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 1", artists: [{ name: "Артист 1" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 2", artists: [{ name: "Артист 2" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 1", artists: [{ name: "Артист 1" }] },
            { name: "ТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрекТрек 2", artists: [{ name: "Артист 2" }] }

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
    
      var container = document.querySelector('.cont3');
      
      if (container) {
        container.innerHTML = resultsHtml; 
      } else {
        console.error('The container with class "cont3" was not found.');
      }
    }  
});
*///MOCK
  


  document.addEventListener('DOMContentLoaded', function() {
      var form = document.getElementById('sendInputs');
      var submitButton = document.getElementById('submit-button');

      submitButton.addEventListener('click', function(event) {

        var allInputsFilled = true;
        $('input[type="text"]').each(function() {
            if ($(this).val() === '') {
                allInputsFilled = false;
                return false; 
            }
        });
    
        if (!allInputsFilled) {
          alert('Please fill in all the fields before continuing.'); 
          event.preventDefault(); 
          return; 
      }

          document.getElementById("searchPage").style.display = "none";
          document.getElementById("showPlaylistPage").style.display = "flex";
          event.preventDefault();

          var queryParams = new URLSearchParams(new FormData(form)).toString();
          var requestUrl = form.action + '?' + queryParams;

          fetch(requestUrl).then(response => {
              if(response.ok) {
                  return response.text();
              }
              throw new Error('Request error');
          }).then(data => {

              console.log(data);

              parseAndDisplayResults(data);
          }).catch(error => {
              window.location.href = '/error';
          });
      });

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

        var container = document.querySelector('.cont3');
      
      if (container) {
        container.innerHTML = resultsHtml; 
      } else {
        window.location.href = '/error';
      }
    }
});



