$(document).ready(function () {
  var date = new Date();
  var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
  todayDate(today);
});
function todayDate(today) {
  $("#datepicker").datepicker({
    dateFormat: "dd-mm-yy",
    orientation: "bottom",
  });

  $("#datepicker").datepicker("setDate", today);
}
function formValidation(){
  den = document.getElementById('denominazione').value;
  categoria = document.getElementById('categoria').value;
  cliente = document.getElementById('cliente').value;
  ordine = document.getElementById('ordine').value;
  alert = document.getElementById('alert');


  if (den == "" || categoria == "Categoria" || cliente == "" || ordine == "" ) 
  {
    alert.classList().remove("hidden");
    return false;
   
    
  }else{
    alert.classList().add("hidden");
    return true;
  }
  
}
// Example starter JavaScript for disabling form submissions if there are invalid fields
function formValidation(){
  'use strict'
console.log("ciao");
  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
}
