/* 
usuario: app_running
Pass: app_running	(md57d4c168e63a9e6f88d3cfb7c93a2bf10) */

-- DROP DATABASE db_running;
CREATE DATABASE db_running
  WITH OWNER = app_running
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Spain.1252'
       LC_CTYPE = 'Spanish_Spain.1252'
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE db_running
  IS 'Base de datos destinada a guardar el contenido de la aplicación Running.';
  

-- DROP ROLE app_running;
CREATE ROLE app_running LOGIN
  ENCRYPTED PASSWORD 'md57d4c168e63a9e6f88d3cfb7c93a2bf10'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

  
-- DROP TABLE carrera;
CREATE TABLE carrera
(
  id serial NOT NULL,
  nombre character varying(80) NOT NULL,
  modalidades character varying(50) NOT NULL,
  provincia character varying(80) NOT NULL,
  ciudad character varying(80) NOT NULL,
  direccion character varying(200),
  fecha_inicio timestamp without time zone,
  descripcion text,
  url_web character varying(500),
  recomendada boolean,
  url_imagen character varying(500),
  distancia_disponible character varying(50),
  CONSTRAINT carrera_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE carrera
  OWNER TO postgres;
GRANT ALL ON TABLE carrera TO postgres;
GRANT ALL ON TABLE carrera TO app_running;


-- DROP TABLE grupos_running;

CREATE TABLE grupos_running
(
  id serial NOT NULL,
  nombre character varying(80),
  CONSTRAINT grupos_running_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE grupos_running
  OWNER TO postgres;
GRANT ALL ON TABLE grupos_running TO postgres;
GRANT ALL ON TABLE grupos_running TO app_running;


-- DROP TABLE usuario_app;

CREATE TABLE usuario_app
(
  id serial NOT NULL,
  email character varying(80),
  tipo_cuenta character(1),
  password character varying(80),
  nombre character varying(80),
  apellido character varying(80),
  fecha_nacimiento date,
  foto_perfil character varying(500),
  nick character varying(80),
  grupo_id integer,
  CONSTRAINT usuario_app_pkey PRIMARY KEY (id),
  CONSTRAINT fk_usuario_app_grupos_running FOREIGN KEY (grupo_id)
      REFERENCES grupos_running (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario_app
  OWNER TO postgres;
GRANT ALL ON TABLE usuario_app TO postgres;
GRANT ALL ON TABLE usuario_app TO app_running;

-- DROP TABLE usuario_carrera;

CREATE TABLE usuario_carrera
(
  id serial NOT NULL,
  tiempo character(3),
  anotado boolean,
  me_gusta boolean,
  corrida boolean,
  distancia character(3),
  modalidad text,
  usuario_id integer,
  carrera_id integer,
  CONSTRAINT usuario_carrera_pkey PRIMARY KEY (id),
  CONSTRAINT fk_usuario_carrera_carrera FOREIGN KEY (carrera_id)
      REFERENCES carrera (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usuario_carrera_usuario FOREIGN KEY (usuario_id)
      REFERENCES usuario_app (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario_carrera
  OWNER TO postgres;
GRANT ALL ON TABLE usuario_carrera TO postgres;
GRANT ALL ON TABLE usuario_carrera TO app_running;

  