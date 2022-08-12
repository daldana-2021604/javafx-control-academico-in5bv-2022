/*
* Nombre de los estudiantes: 
* Dorbal Emilio Aldana     2021604
* Kevin Josue Xulu Solis   2021348
* 
* Código técnico: IN5BV
* Grupo: 1 (Jueves)
* Fecha de crecion: 20/04/2022
* Fecha de modificación: 
*/
/*DML*/
DROP DATABASE IF EXISTS db_control_academico_in5bv;
CREATE DATABASE db_control_academico_in5bv;

USE db_control_academico_in5bv;

DROP TABLE IF EXISTS alumnos;
CREATE TABLE IF NOT EXISTS alumnos(
	carne VARCHAR(16) NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL,
    nombre3 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15)NULL,
    PRIMARY KEY (carne)
);

DROP TABLE IF EXISTS instructores;
CREATE TABLE IF NOT EXISTS instructores(
	id INT AUTO_INCREMENT NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15)NULL,
    nombre3 VARCHAR(15)NULL,
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15)NULL,
    direccion VARCHAR(45)NULL,
    email VARCHAR(45)NOT NULL,
    telefono VARCHAR(8)NOT NULL,	
    fecha_nacimiento DATE NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS salones;
CREATE TABLE IF NOT EXISTS salones(
	codigo_salon VARCHAR(5) NOT NULL,
    descripcion VARCHAR(45)NULL,
    capacidad_maxima INT NOT NULL,
    edificio VARCHAR (15)NULL,
    nivel INT NULL,
    PRIMARY KEY (codigo_salon)
);

DROP TABLE IF EXISTS carreras_tecnicas;
CREATE TABLE IF NOT EXISTS carreras_tecnicas(
	codigo_tecnico VARCHAR(6)NOT NULL,
    carrera VARCHAR(45)NOT NULL,
    grado VARCHAR(10)NOT NULL,
    seccion VARCHAR(3)NOT NULL,
    jornada VARCHAR(10)NOT NULL,
    PRIMARY KEY (codigo_tecnico)
);

DROP TABLE IF EXISTS horarios;
CREATE TABLE IF NOT EXISTS horarios(
	id INT AUTO_INCREMENT NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_final TIME NOT NULL,
    lunes TINYINT(1)NULL,
    martes TINYINT(1) NULL,
    miercoles TINYINT(1) NULL,
    jueves TINYINT(1) NULL,
    viernes TINYINT(1) NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS cursos;
CREATE TABLE IF NOT EXISTS cursos(
	id INT AUTO_INCREMENT NOT NULL,
    nombre_curso VARCHAR(255)NOT NULL,
    ciclo YEAR NULL,
    cupo_maximo INT NULL,
    cupo_minimo INT NULL,
    carrera_tecnica_id VARCHAR(128) NOT NULL,
    horario_id INT NOT NULL,
    instructor_id INT NOT NULL,
    salon_id VARCHAR(5) NOT NULL,
    PRIMARY KEY (id) ,
    CONSTRAINT fk_cursos_carreras_tecnicas
		FOREIGN KEY (carrera_tecnica_id)
        REFERENCES carreras_tecnicas(codigo_tecnico)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_horarios
		FOREIGN KEY (horario_id)
        REFERENCES horarios(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_instructores
		FOREIGN KEY (instructor_id)
        REFERENCES instructores(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_salones
		FOREIGN KEY(salon_id)
        REFERENCES salones(codigo_salon)
        ON DELETE CASCADE ON UPDATE CASCADE
);


DROP TABLE IF EXISTS asignaciones_alumnos;
CREATE TABLE IF NOT EXISTS asignaciones_alumnos(
	id INT NOT NULL AUTO_INCREMENT,
	alumno_id VARCHAR(16) NOT NULL,
	curso_id INT NOT NULL,
	fecha_asignacion DATETIME NULL,
	CONSTRAINT pk_asignaciones_alumnos PRIMARY KEY (id),
	CONSTRAINT fk_asignaciones_alumnos_alumno
	FOREIGN KEY (alumno_id) REFERENCES alumnos(carne)
	ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_asignaciones_alumnos_cursos
	FOREIGN KEY (curso_id) REFERENCES cursos(id)
	ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles(
	id INT NOT NULL,
    descripcion VARCHAR(50)	NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS usuarios;
CREATE TABLE IF NOT EXISTS usuarios(
	user VARCHAR(25) NOT NULL,
    pass VARCHAR(255) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    rol_id INT NOT NULL,
    PRIMARY KEY (user),
    CONSTRAINT fk_usuarios_roles FOREIGN KEY (rol_id) REFERENCES roles(id)
);

INSERT INTO roles(id, descripcion) VALUES (1, "Administrador");
INSERT INTO roles(id, descripcion) VALUES (2, "Estandar");

INSERT INTO usuarios(user, pass, nombre, rol_id) VALUES("root", "admin", "Jorge Pérez", 1);
INSERT INTO usuarios(user, pass, nombre, rol_id) VALUES ("kinal", "12345", "Luis Canto", 2);

-- Alumnos 
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2021654", "Jose", "David", "", "Figueros", "Aldino");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2020345", "Anibal", "Alejandro", "Matias", "Cordin", "Alux");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2018654", "Alvaro", "Oswaldo", "", "Hernandez", "Polsón");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("219548", "Emmanuel", "", "", "Fereira", "Hernadez");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2021693", "Fernando", "Alonso", "", "Guzman", "Quiro");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2022548", "Kennet", "Arnoldo", "", "Gomez", "Silju");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2018462", "Domingo", "Ismael", "", "Lopez", "Almeida");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2019632", "Julia", "Lorenza", "", "Alon", "Gracía");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2021346", "Carmen", "Lucia", "Amelia", "Gonzales", "Pineda");
INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
VALUES("2021569", "Emma", "Jessenia", "", "Gil", "Granaados");

-- INSTRUCTORES
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Juan", "Jose" ,null , "Rivas", null , "Zona1 de Mixco", "jrivas@gmail.com","85201634",'2001/02/13');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Dorbal", "Emilio" ,null , "Aldana", "Ramos" , "Zona3 ciudad de Guatemala", "daldana@gmail.com","85201694",'1980/12/14');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Daniel", "de Jesus" ,null , "Estrada", "Rojo" , "Zona1 de San Miguel Petapa", "destrada@gmail.com","95201634",'2009/03/25');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Pablo", "Mateo" ,null , "Garicia ", "Lopez" , "Zona6 de Mixco", "pgarcia@gmail.com","85203634",'2000/12/04');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Juan", "Alvarado" ,null , "Ramos", "De Leon" , "Zona5 ciudad de Guatemala", "jramos@gmail.com","85201639",'1999/01/21');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Victor", "Alejandro" ,null , "Bolaños", "Ramos" , "Zona2 ciudad de Guatemala", "vramos@gmail.com","84201634",'1996/02/13');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Marcos", "Ivan" ,null , "Herrera", "Castillo" , "Zona10 ciudad de Guatemala", "mherrera@gmail.com","58201634",'1997/03/10');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Carlos", "Miguel" ,null , "Choc", "Diaz" , "Zona4 ciudad de Guatemala", "cchoc@gmail.com","84201367",'1998/05/25');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Marc", "Antoni" ,null , "Alvarado", "Marroquin" , "Zona5 ciudad de Guatemala", "malvarado@gmail.com","84351367",'1998/05/20');
INSERT INTO instructores (nombre1,nombre2,nombre3,apellido1,apellido2,direccion,email,telefono,fecha_nacimiento)
VALUES ("Hector", "Samuel" ,null , "Barrios", "Escobar" , "Zona13 ciudad de Guatemala", "hbarrios@gmail.com","49351367",'1995/07/10');

-- Salones
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("C-29", "Laboratorio de computacion", 40, "Sur", 1);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("D-20", "Taller de mecánica", 35, "Central", 4);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("C-15", "Taller de Dibujo", 30, "Este", 2);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("D-14", "Salón de matematicas", 45, "Central", 2);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("F-15", "Taller de Dibujo", 40, "Sur", 3);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("G-32", "Taller de Informatica", 30, "Este", 4);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("E-27", "Laboratorio de Quimica", 25, "Sur", 2);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("N-20", "Taller de Electronica", 45, "Central", 1);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("G-37", "Taller de electricidad", 40, "Este", 3);
INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
VALUES("D-23", "Salon de eventos", 120, "Central", 3);

-- Carreras Tecnicas
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN4BV", "Informatica", "Cuarto", "B", "Vespertina");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("M5BM", "Mecanica", "Quinto", "B", "Matutima");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN6CM", "Informactica", "Sexto", "C", "Matutina");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("EL5AM", "Electricidad", "Quinto", "A", "Matutina");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("D4BV", "Dibujo Tecnico", "Cuarto", "B", "Vespertina");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("M4CV", "Mecanica", "Cuarto", "C", "Vespertina");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5BV", "Informatica", "Quinto", "B", "Vespertina");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("D6BM", "Dibujo Tecnico", "Sexto", "B", "Maturina");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("EL4CV", "Electronica", "Cuarto", "C", "Vespertina");
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN4CM", "Informatica", "Cuarto", "C", "Matutina");

-- Horarios
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("7:00:00", "12:00:00", 1, 0, 1, 0, 1);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("12:40:00", "16:40:00", 0, 0, 0, 1, 0);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES ("7:10:00", "12:30:00", 1, 1, 1, 0, 0);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("13:00:00", "17:30:00", 0, 0, 1, 0, 1);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("7:20:00", "11:30:00", 0, 0, 1, 1, 0);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("12:40:00", "15:00:00", 1, 1, 0, 1, 1);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("08:00:00", "12:20:00", 0, 0, 0, 1, 0);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("12:50:00", "17:20:00", 0, 0, 0, 0, 1);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("07:30:00", "12:25:00", 0, 1, 1, 0, 0);
INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
VALUES("13:00:00", "16:30:00", 1, 0, 0, 1, 1);

-- Cursos
INSERT INTO cursos(nombre_curso,ciclo,cupo_maximo,cupo_minimo,carrera_tecnica_id,horario_id,instructor_id,salon_id)
VALUES ("Informatica",2022,50,20,"IN5BV",2,1,"C-29");
INSERT INTO cursos(nombre_curso,ciclo,cupo_maximo,cupo_minimo,carrera_tecnica_id,horario_id,instructor_id,salon_id)
VALUES("Mecanica",2020,30,20,"M5BM",2,2,"D-20");
INSERT INTO cursos(nombre_curso,ciclo,cupo_maximo,cupo_minimo,carrera_tecnica_id,horario_id,instructor_id,salon_id)
VALUES("Electronica",2021,40,10,"EL4CV",4,3,"G-37");
INSERT INTO cursos(nombre_curso,ciclo,cupo_maximo,cupo_minimo,carrera_tecnica_id,horario_id,instructor_id,salon_id)
VALUES("Dibujo",2018,48,15,"D4BV",1,5,"C-15");
INSERT INTO cursos(nombre_curso,ciclo,cupo_maximo,cupo_minimo,carrera_tecnica_id,horario_id,instructor_id,salon_id)
VALUES("Dibujo",2019,49,15,"D6BM",3,4,"F-15");
INSERT INTO cursos(nombre_curso,ciclo,cupo_maximo,cupo_minimo,carrera_tecnica_id,horario_id,instructor_id,salon_id)
VALUES("Informatica",2021,30,8,"IN4BV",3,2,"N-20");
INSERT INTO cursos(nombre_curso,ciclo,cupo_maximo,cupo_minimo,carrera_tecnica_id,horario_id,instructor_id,salon_id)
VALUES ("Informatica",2022,50,20,"IN4CM",2,6,"G-32");

insert into asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
values ("2021654", "1", "2021-10-15");
insert into asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
values ("2020345", "2", "2021-09-12");
insert into asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
values ("2018654", "3", "2021-11-13");
insert into asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
values ("2021693", "4", "2021-12-05");

/*****************************************************************************************************************************************************/
-- ***********************************************************Procedimientos almacenados*********************************************************** --

-- __________________________________________________________Alumnos__________________________________________________________ --
-- READ
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read $$
CREATE PROCEDURE sp_alumnos_read()
BEGIN
SELECT
a.carne,
a.nombre1,
a.nombre2,
a.nombre3,
a.apellido1,
a.apellido2
FROM
alumnos AS a;
END $$
DELIMITER ;

-- CREATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_create $$
CREATE PROCEDURE sp_alumnos_create (
    IN _carne VARCHAR(16), 
    IN _nombre1 VARCHAR(15), 
    IN _nombre2 VARCHAR(15), 
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15)
 )
BEGIN
  INSERT INTO alumnos (
      carne, 
      nombre1, 
      nombre2,
      nombre3,
      apellido1,
      apellido2
  ) 
  VALUES 
  (
      _carne, 
      _nombre1, 
      _nombre2, 
      _nombre3,
      _apellido1,
      _apellido2
  );
END $$
DELIMITER ;

-- UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$
CREATE PROCEDURE sp_alumnos_update(
     IN _carne VARCHAR(16), 
     IN _nombre1 VARCHAR(15), 
     IN _nombre2 VARCHAR(15), 
     IN _nombre3 VARCHAR(15),
     IN _apellido1 VARCHAR(15),
     IN _apellido2 VARCHAR(15)
)
BEGIN
    UPDATE
        alumnos
	SET
        nombre1 = _nombre1,
        nombre2 = _nombre2,
        nombre3 = _nombre3,
        apellido1 = _apellido1,
        apellido2 = _apellido2
	WHERE
        carne = _carne;
END $$
DELIMITER ;

-- DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete $$
CREATE PROCEDURE sp_alumnos_delete (
    IN _carne INT
    )
BEGIN
    DELETE FROM 
        alumnos
	WHERE 
        carne = _carne;
END $$
DELIMITER ;

-- READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_by_id $$
CREATE PROCEDURE sp_alumnos_read_by_id (
      IN _carne VARCHAR(16)
)
BEGIN
    SELECT 
       a.carne,
       a.nombre1,
       a.nombre2,
       a.nombre3,
       a.apellido1,
       a.apellido2
       
	FROM
       alumnos AS a
    WHERE
    carne = _carne;
	   
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_report $$
CREATE PROCEDURE sp_alumnos_report()
BEGIN
	SELECT
		a.carne,
		CONCAT(a.nombre1," ", IF(a.nombre2 IS null, "", a.nombre2), " ", IF(a.nombre3 IS null, "", a.nombre3), " ",a.apellido1, " ", IF(a.apellido2 IS null, "", a.apellido2)) AS nombre_completo
	FROM
		alumnos AS a;
END $$
DELIMITER ;


-- __________________________________________________________Salones__________________________________________________________ --
-- READ
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read $$
CREATE PROCEDURE sp_salones_read()
BEGIN
SELECT
   s.codigo_salon,
   s.descripcion,
   s.capacidad_maxima,
   s.edificio,
   s.nivel
FROM
salones AS s;
END $$
DELIMITER ;

-- CREATE 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_create $$
CREATE PROCEDURE sp_salones_create (
    IN _codigo_salon VARCHAR(5), 
    IN _descripcion VARCHAR(45), 
    IN _capacidad_maxima INT,
    IN _edificio VARCHAR(15),
    IN _nivel INT
    
 )
BEGIN
  INSERT INTO salones (
      codigo_salon, 
      descripcion, 
      capacidad_maxima,
      edificio,
      nivel
      
  ) 
  VALUES 
  (
      _codigo_salon, 
      _descripcion, 
      _capacidad_maxima, 
      _edificio,
      _nivel
      
  );
END $$
DELIMITER ;

-- UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_update $$
CREATE PROCEDURE sp_salones_update(
    IN _codigo_salon VARCHAR(5), 
    IN _descripcion VARCHAR(45), 
    IN _capacidad_maxima INT,
    IN _edificio VARCHAR(15),
    IN _nivel INT
)
BEGIN
    UPDATE
        salones
	SET
	  codigo_salon = _codigo_salon,
      descripcion = _descripcion,
      capacidad_maxima = _capacidad_maxima,
      edificio = _edificio,
      nivel = _nivel
	WHERE
        codigo_salon = _codigo_salon;
END $$
DELIMITER ;

-- DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete $$
CREATE PROCEDURE sp_salones_delete (
    IN _codigo_salon VARCHAR(5)
    )
BEGIN
    DELETE FROM 
        salones
	WHERE 
        codigo_salon = _codigo_salon;
END $$
DELIMITER ;

-- READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_by_id $$
CREATE PROCEDURE sp_salones_read_by_id (
      IN _codigo_salon VARCHAR(5)
)
BEGIN
    SELECT 
       s.codigo_salon,
       s.descripcion,
       s.capacidad_maxima,
       s.edificio,
       s.nivel
	FROM
       salones AS s
	WHERE
    codigo_salon = _codigo_salon;
	   
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_report $$
CREATE PROCEDURE sp_salones_report()
BEGIN
SELECT
   s.codigo_salon,
   s.descripcion,
   s.capacidad_maxima,
   s.edificio,
   s.nivel
FROM
salones AS s;
END $$
DELIMITER ;


-- __________________________________________________________Carreras Tecnicas__________________________________________________________ --
-- READ
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read $$
CREATE PROCEDURE sp_carreras_tecnicas_read()
BEGIN
SELECT
   c.codigo_tecnico,
   c.carrera,
   c.grado,
   c.seccion,
   c.jornada
FROM
carreras_tecnicas AS c;
END $$
DELIMITER ;


-- CREATE 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_create $$
CREATE PROCEDURE sp_carreras_tecnicas_create (
    IN _codigo_tecnico VARCHAR(6), 
    IN _carrera VARCHAR(45), 
    IN _grado VARCHAR(10),
    IN _seccion VARCHAR(3),
    IN _jornada VARCHAR(10)
    
 )
BEGIN
  INSERT INTO carreras_tecnicas (
      codigo_tecnico, 
      carrera, 
      grado,
      seccion,
      jornada
      
  ) 
  VALUES 
  (
      _codigo_tecnico, 
      _carrera, 
      _grado, 
      _seccion,
      _jornada
      
  );
END $$
DELIMITER ;

-- UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_update $$
CREATE PROCEDURE sp_carreras_tecnicas_update(
    IN _codigo_tecnico VARCHAR(6), 
    IN _carrera VARCHAR(45), 
    IN _grado VARCHAR(10),
    IN _seccion CHAR(1),
    IN _jornada VARCHAR(10)
)
BEGIN
    UPDATE
        carreras_tecnicas
	SET
	  codigo_tecnico = _codigo_tecnico,
      carrera = _carrera,
      grado = _grado,
      seccion = _seccion,
      jornada = _jornada
	WHERE
        codigo_tecnico = _codigo_tecnico;
END $$
DELIMITER ;

-- DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete $$
CREATE PROCEDURE sp_carreras_tecnicas_delete (
    IN _codigo_tecnico VARCHAR(6)
    )
BEGIN
    DELETE FROM 
        carreras_tecnicas
	WHERE 
        codigo_tecnico = _codigo_tecnico;
END $$
DELIMITER ;
call sp_carreras_tecnicas_read;

-- READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by_id $$
CREATE PROCEDURE sp_carreras_tecnicas_read_by_id (
      IN _codigo_tecnico VARCHAR(5)
)
BEGIN
    SELECT 
       ct.codigo_tecnico,
       ct.carrera,
       ct.grado,
       ct.seccion,
       ct.jornada
	FROM
       carreras_tecnicas AS ct
    WHERE 
	   codigo_tecnico = _codigo_tecnico;
	   
END $$
DELIMITER ;

-- Read report
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_report $$
CREATE PROCEDURE sp_carreras_tecnicas_report()
BEGIN
SELECT
   c.codigo_tecnico,
   c.carrera,
   c.grado,
   c.seccion,
   c.jornada
FROM
carreras_tecnicas AS c;
END $$
DELIMITER ;



-- __________________________________________________________Instructores__________________________________________________________ --
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read $$
CREATE PROCEDURE sp_instructores_read()
BEGIN
SELECT
   i.id,
   i.nombre1,
   i.nombre2,
   i.nombre3,
   i.apellido1,
   i.apellido2,
   i.direccion,
   i.email,
   i.telefono,
   i.fecha_nacimiento
FROM
instructores AS i;
   
END$$
DELIMITER ;

CALL sp_instructores_read;

-- CREATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_create $$
CREATE PROCEDURE sp_instructores_create (
    IN _nombre1 VARCHAR(15), 
    IN _nombre2 VARCHAR(15), 
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15),
    IN _direccion VARCHAR(45),
    IN _email VARCHAR(45),
    IN _telefono VARCHAR(15),
    IN _fecha_nacimiento DATE
 )
BEGIN
  INSERT INTO instructores (
      nombre1, 
      nombre2,
      nombre3,
      apellido1,
      apellido2,
      direccion,
      email,
      telefono,
      fecha_nacimiento
	
  ) 
  VALUES 
  (
      _nombre1, 
      _nombre2, 
      _nombre3,
      _apellido1,
      _apellido2,
      _direccion,
      _email,
      _telefono,
      _fecha_nacimiento
  );
END $$
DELIMITER ;


-- UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_update $$
CREATE PROCEDURE sp_instructores_update(
	IN _id iNT,
    IN _nombre1 VARCHAR(15), 
    IN _nombre2 VARCHAR(15), 
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15),
    IN _direccion VARCHAR(45),
    IN _email VARCHAR(45),
    IN _telefono VARCHAR(15),
    IN _fecha_nacimiento DATE
)
BEGIN
    UPDATE
        instructores
	SET
        nombre1 = _nombre1,
        nombre2 = _nombre2,
        nombre3 = _nombre3,
        apellido1 = _apellido1,
        apellido2 = _apellido2,
        direccion = _direccion,
        email = _email,
        telefono = _telefono,
        fecha_nacimiento = _fecha_nacimiento
	WHERE
        id = _id;
END $$
DELIMITER ;

-- DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$
CREATE PROCEDURE sp_instructores_delete (
    IN _id INT
    )
BEGIN
    DELETE FROM 
        instructores
	WHERE 
        id = _id;
END $$
DELIMITER ;


-- READ BY
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_by_id $$
CREATE PROCEDURE sp_instructores_read_by_id (
      IN _id VARCHAR(16)
)
BEGIN
    SELECT 
	   i.id,
       i.nombre1,
       i.nombre2,
       i.nombre3,
       i.apellido1,
       i.apellido2,
       i.direccion,
       i.email,
       i.direccion,
       i.telefono,
       i.fecha_nacimiento
	FROM
       instructores AS i
   WHERE id= _id;
	   
END $$
DELIMITER ;

-- Report

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_report $$
CREATE PROCEDURE sp_instructores_report()
BEGIN
	SELECT  
		id,
		CONCAT( nombre1," ",nombre2," ", IF(nombre3 IS NULL, " ", nombre3) ," ",apellido1," ",IF(apellido2 IS NULL, " ", apellido2)) AS nombre_completo, 
		direccion, 
		email,
		telefono, 
		fecha_nacimiento
    FROM instructores;
   
END$$
DELIMITER ;




-- __________________________________________________________Cursos__________________________________________________________ --
-- READ
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read $$
CREATE PROCEDURE sp_cursos_read()
BEGIN
SELECT
cu.id,
cu.nombre_curso,
cu.ciclo,
cu.cupo_maximo,
cu.cupo_minimo,
cu.carrera_tecnica_id,
cu.horario_id,
cu.instructor_id,
cu.salon_id
FROM
cursos AS cu;
END $$
DELIMITER ;

CALL sp_cursos_read;

-- CREATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_create $$
CREATE PROCEDURE sp_cursos_create (
    IN _nombre_curso VARCHAR(255), 
    IN _ciclo YEAR, 
    IN _cupo_maximo INT,
    IN _cupo_minimo INT,
    IN _carrera_tecnica_id VARCHAR(128),
    IN _horario_id INT,
    IN _instructor_id INT,
    IN _salon_id VARCHAR(5)
 )
BEGIN
  INSERT INTO cursos (
      nombre_curso, 
      ciclo,
      cupo_maximo,
      cupo_minimo,
      carrera_tecnica_id,
      horario_id,
      instructor_id,
      salon_id
	
  ) 
  VALUES 
  (
      _nombre_curso, 
      _ciclo, 
      _cupo_maximo,
      _cupo_minimo,
      _carrera_tecnica_id,
      _horario_id,
      _instructor_id,
      _salon_id
  );
END $$
DELIMITER ;

-- UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_update $$
CREATE PROCEDURE sp_cursos_update(
	IN _id iNT,
	IN _nombre_curso VARCHAR(16), 
    IN _ciclo VARCHAR(15), 
    IN _cupo_maximo VARCHAR(15), 
    IN _cupo_minimo VARCHAR(15),
    IN _carrera_tecnica_id VARCHAR(15),
    IN _horario_id VARCHAR(15),
    IN _instructor_id VARCHAR(15),
    IN _salon_id VARCHAR(15)
)
BEGIN
    UPDATE
        cursos
	SET
        nombre_curso = _nombre_curso,
        ciclo = _ciclo,
        cupo_maximo = _cupo_maximo,
        cupo_minimo = _cupo_minimo,
        carrera_tecnica_id = _carrera_tecnica_id,
        horario_id = _horario_id,
        instructor_id = _instructor_id,
        salon_id = _salon_id
	WHERE
        id = _id;
END $$
DELIMITER ;

-- DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete $$
CREATE PROCEDURE sp_cursos_delete (
    IN _id INT
    )
BEGIN
    DELETE FROM 
        cursos
	WHERE 
        id = _id;
END $$
DELIMITER ;

-- READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_by_id $$
CREATE PROCEDURE sp_cursos_read_by_id(
	IN _id int
)
BEGIN 
	SELECT 
		cu.id,
		cu.nombre_curso,
		cu.ciclo,
		cu.cupo_maximo,
		cu.cupo_minimo,
		cu.carrera_tecnica_id,
		cu.horario_id,
		cu.instructor_id,
		cu.salon_id
	FROM 
		cursos AS cu 
	WHERE id = _id;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_report $$
CREATE PROCEDURE sp_cursos_report()
BEGIN
	SELECT
		cu.id,
		cu.nombre_curso,
		cu.ciclo,
		cu.cupo_maximo,
		cu.cupo_minimo,
		CONCAT(cu.carrera_tecnica_id,"  ", c.carrera)AS carrera,
		cu.horario_id,
        h.horario_inicio,
        h.horario_final,
        CONCAT( cu.instructor_id,"  ",i.nombre1," ",i.apellido1) AS Instructor, 
		CONCAT(cu.salon_id,"  ",s.descripcion) AS salon
	FROM
		cursos AS cu INNER JOIN carreras_tecnicas AS c INNER JOIN horarios AS h  INNER JOIN instructores AS i INNER JOIN salones AS s
        ON cu.carrera_tecnica_id = c.codigo_tecnico AND cu.horario_id = h.id AND cu.instructor_id = i.id AND cu.salon_id = s.codigo_salon;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_report_by_id $$
CREATE PROCEDURE sp_cursos_report_by_id(IN _id INT)
BEGIN
	SELECT
		cu.id,
		cu.nombre_curso,
		cu.ciclo,
		cu.cupo_maximo,
		cu.cupo_minimo,
		CONCAT(cu.carrera_tecnica_id,"  ", c.carrera)AS carrera,
		cu.horario_id,
        h.horario_inicio,
        h.horario_final,
        CONCAT( cu.instructor_id,"  ",i.nombre1," ",i.apellido1) AS Instructor, 
		CONCAT(cu.salon_id,"  ",s.descripcion) AS salon
	FROM
		cursos AS cu INNER JOIN carreras_tecnicas AS c INNER JOIN horarios AS h  INNER JOIN instructores AS i INNER JOIN salones AS s
        ON cu.carrera_tecnica_id = c.codigo_tecnico AND cu.horario_id = h.id AND cu.instructor_id = i.id AND cu.salon_id = s.codigo_salon
        WHERE cu.id = _id;
END $$
DELIMITER ;



-- __________________________________________________________Horarios__________________________________________________________ --
-- READ
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read $$
CREATE PROCEDURE sp_horarios_read()
BEGIN
SELECT
	id,
	horario_inicio,
	horario_final,
	lunes,
	martes,
	miercoles,
	jueves,
	viernes
FROM
	horarios AS h;
END $$
DELIMITER ;

-- CREATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_create $$
CREATE PROCEDURE sp_horarios_create (
    IN _horario_inicio TIME, 
    IN _horario_final TIME, 
    IN _lunes TINYINT,
    IN _martes TINYINT,
    IN _miercoles TINYINT,
    IN _jueves TINYINT,
    IN _viernes TINYINT
 )
BEGIN
  INSERT INTO horarios (
      horario_inicio, 
      horario_final,
      lunes,
      martes,
      miercoles,
      jueves,
      viernes
	
  ) 
  VALUES 
  (
      _horario_inicio, 
      _horario_final, 
      _lunes,
      _martes,
      _miercoles,
      _jueves,
      _viernes
  );
END $$
DELIMITER ;

-- UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update $$
CREATE PROCEDURE sp_horarios_update(
	IN _id INT,
	IN _horario_inicio TIME, 
    IN _horario_final TIME, 
    IN _lunes TINYINT,
    IN _martes TINYINT,
    IN _miercoles TINYINT,
    IN _jueves TINYINT,
    IN _viernes TINYINT
)
BEGIN
    UPDATE
        horarios
	SET
        horario_inicio = _horario_inicio,
        horario_final = _horario_final,
        lunes = _lunes,
        martes = _martes,
        miercoles = _miercoles,
        jueves = _jueves,
        viernes = _viernes
	WHERE
        id = _id;
END $$
DELIMITER ;

-- DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete $$
CREATE PROCEDURE sp_horarios_delete (
    IN _id INT
    )
BEGIN
    DELETE FROM 
        horarios
	WHERE 
        id = _id;
END $$
DELIMITER ;

-- READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read_by_id $$
CREATE PROCEDURE sp_horarios_read_by_id(IN _id INT)
BEGIN
SELECT
	id,
	horario_inicio,
	horario_final,
	lunes,
	martes,
	miercoles,
	jueves,
	viernes
FROM
	horarios
WHERE
	id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_report $$
CREATE PROCEDURE sp_horarios_report()
BEGIN
SELECT
	id,
	horario_inicio,
	horario_final,
	IF(lunes IS TRUE, "Si", "No"), " ",
	IF (martes IS TRUE, "Si", "No"), " ",
	IF (miercoles IS TRUE, "Si", "No"), " ",
	IF(jueves IS TRUE, "Si", "No"), " ",
	IF(viernes IS TRUE, "Si", "No")
FROM
	horarios AS h;
END $$
DELIMITER ;
-- __________________________________________________________Asignaciones alumnos__________________________________________________________ --
-- READ
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read $$
CREATE PROCEDURE sp_asignaciones_alumnos_read()
BEGIN 
SELECT 
	aa.id,
    aa.alumno_id,
    aa.curso_id,
    aa.fecha_asignacion
FROM 
asignaciones_alumnos AS aa;
END $$
DELIMITER ; 

-- CREATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_create $$
CREATE PROCEDURE sp_asignaciones_alumnos_create (
    IN _alumno_id VARCHAR(16), 
    IN _curso_id INT, 
    IN _fecha_asignacion DATETIME
 )
BEGIN
  INSERT INTO asignaciones_alumnos (
      alumno_id, 
      curso_id,
      fecha_asignacion
	
  ) 
  VALUES 
  (
	
	_alumno_id , 
    _curso_id, 
    _fecha_asignacion 
  );
END $$
DELIMITER ;

-- UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_update $$
CREATE PROCEDURE sp_asignaciones_alumnos_update(
	IN _id int,
	IN _alumno_id varchar(16),
	IN _curso_id int,
	IN _fecha_asignacion datetime
)
BEGIN
	UPDATE 
		asignaciones_alumnos
        SET
		alumno_id = _alumno_id,
		curso_id = _curso_id,
		fecha_asignacion = fecha_asignacion
        WHERE 
        id = _id;

END$$
DELIMITER ;

-- DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_delete $$
CREATE PROCEDURE sp_asignaciones_alumnos_delete (
    IN _id INT
    )
BEGIN
    DELETE FROM 
        asignaciones_alumnos
	WHERE 
        id = _id;
END $$
DELIMITER ;

-- READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read_by_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_read_by_id(IN _id VARCHAR(45))
BEGIN
SELECT
	asi.id,
	asi.alumno_id,
	asi.curso_id,
	asi.fecha_asignacion
FROM
	asignaciones_alumnos AS asi
WHERE
	id = _id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_report $$
CREATE PROCEDURE sp_asignaciones_alumnos_report()
BEGIN
	SELECT 
		aa.id,
        aa.alumno_id,
        CONCAT(a.nombre1," ", IF(a.nombre2 IS null, "", a.nombre2), " ", IF(a.nombre3 IS null, "", a.nombre3), " ",a.apellido1, " ", IF(a.apellido2 IS null, "", a.apellido2)) AS nombre_completo,
        aa.curso_id,
        c.nombre_curso,
        aa.fecha_asignacion
    FROM 
		asignaciones_alumnos AS aa INNER JOIN alumnos AS a INNER JOIN cursos AS c
        ON aa.alumno_id = a.carne AND aa.curso_id = c.id;
END $$
DELIMITER ;

-- Reporte por id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_report_by_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_report_by_id(IN _id INT)
BEGIN
	SELECT 
		aa.id,
        aa.alumno_id,
        CONCAT(a.nombre1," ", IF(a.nombre2 IS null, "", a.nombre2), " ", IF(a.nombre3 IS null, "", a.nombre3), " ",a.apellido1, " ", IF(a.apellido2 IS null, "", a.apellido2)) AS nombre_completo,
        aa.curso_id,
        c.nombre_curso,
        aa.fecha_asignacion
    FROM 
		asignaciones_alumnos AS aa INNER JOIN alumnos AS a INNER JOIN cursos AS c
        ON aa.alumno_id = a.carne AND aa.curso_id = c.id
	WHERE aa.id = _id;
END $$
DELIMITER ;

/*************************************************Roles y usuario************************************************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_usuario_read $$
CREATE PROCEDURE sp_usuario_read(IN _user VARCHAR(255), IN _pass VARCHAR(255))
BEGIN
	SELECT
		us.user,
        us.pass,
        us.nombre,
        us.rol_id,
        r.descripcion
    FROM 
		usuarios AS us INNER JOIN roles AS r
        ON us.rol_id = r.id 
	WHERE user = _user AND pass = _pass;
END $$
DELIMITER ;

CALL sp_usuario_read("kinal","12345");

SELECT *FROM usuarios;