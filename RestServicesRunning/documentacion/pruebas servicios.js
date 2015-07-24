//************ CONSULTAS USUARIOS *****************

//findById
http://localhost:8080/RestServicesRunning/running/usuarios/usuariosById/1

//findAll
http://localhost:8080/RestServicesRunning/running/usuarios/usuariosAll

//update Tenes que modificar algún campo y ver que se actualice en la base de datos. El usuario tiene que existir.
$.ajax({ type: "POST", url: "http://localhost:8080/RestServicesRunning/running/usuarios/updateUsuario",data: '{"id": 1,"email": "dcamarro@gmail.com","tipoCuenta": "A","password": "dario","nombre": "Dario","apellido": "Camarro","fechaNacimiento": "1984/10/23","fotoPerfil": "foto","nick": "Dario","grupoId": "1"}' ,dataType: "application/json", contentType:"application/json; charset=UTF-8", success: function(r){console.log(r)}});
//update Para probar respuesta cuando no existe el usuario. 
$.ajax({ type: "POST", url: "http://localhost:8080/RestServicesRunning/running/usuarios/updateUsuario",data: '{"id": 10000,"email": "dcamarro@gmail.com","tipoCuenta": "A","password": "dario","nombre": "Dario","apellido": "Camarro","fechaNacimiento": "1984/10/23","fotoPerfil": "foto","nick": "Dario","grupoId": "1"}' ,dataType: "application/json", contentType:"application/json; charset=UTF-8", success: function(r){console.log(r)}});

//Crear: Tenes que modificar algún campo y ver que se actualice en la base de datos. El usuario tiene que existir.
$.ajax({ type: "POST", url: "http://localhost:8080/RestServicesRunning/running/usuarios/saveUsuario",data: '{"email": "fcostazini@gmail.com","tipoCuenta": "A","password": "facundo","nombre": "Facundo","apellido": "Costa Zini","fechaNacimiento": "1985/06/19","fotoPerfil": "foto","nick": "Nahuel","grupoId": "1"}' ,dataType: "application/json", contentType:"application/json; charset=UTF-8", success: function(r){console.log(r)}});

//getUsuarioByEmail Exito
http://localhost:8080/RestServicesRunning/running/usuarios/usuariosByEmail/dariocamarro@gmail.com

//getUsuarioByEmail No encuentra
http://localhost:8080/RestServicesRunning/running/usuarios/usuariosByEmail/pirulo@gmail.com
	
// ************ CONSULTAS CARRERAS DETALLE *******************

//findAll
http://localhost:8080/RestServicesRunning/running/carreras/carrerasDtoAll

//findAll by filtro (provisorio... objeto filtro incompleto)
$.ajax({ type: "POST", url: "http://localhost:8080/RestServicesRunning/running/carreras/carrerasDtoAll",data: '{"nombreCarrera": "Nike 10K"}' ,dataType: "application/json", contentType:"application/json; charset=UTF-8", success: function(r){console.log(r)}});