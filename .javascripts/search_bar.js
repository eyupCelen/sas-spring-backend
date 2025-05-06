//-------------------------------------------------------------------------------------------------------

const searchInput = document.getElementById('searchInput');

// Add event listener for keydown events
searchInput.addEventListener('keydown', function(event) 
{
    // Call the requestSearchBar function on every keydown
    requestSearchBar();
});

//-------------------------------------------------------------------------------------------------------