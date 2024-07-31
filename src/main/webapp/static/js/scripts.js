String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

function deleteElementById(id) {
  const element = document.getElementById(id);
  if (element) {
    element.remove();
  } else {
    console.log(`Element with id ${id} not found.`);
  }
}

function addElementById(id, htmlString) {
  const element = document.getElementById(id);
  if (element) {
    element.insertAdjacentHTML('beforeend', htmlString);
  } else {
    console.log(`Element with id ${id} not found.`);
  }
}