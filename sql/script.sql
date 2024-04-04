CREATE TABLE "rol" (
    "id" SERIAL PRIMARY KEY,
    "rol" VARCHAR NOT NULL CHECK ("rol" IN ('CLIENTE', 'ADMIN'))
);

CREATE TABLE "usuario" (
    "id" SERIAL PRIMARY KEY,
    "nombre_completo" VARCHAR NOT NULL,
    "correo_electronico" VARCHAR UNIQUE NOT NULL, 
    "contrasena" VARCHAR NOT NULL,
    "fecha_registro" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "fecha_actualiza" TIMESTAMP,
    "estado" BOOLEAN NOT NULL DEFAULT true,
    "rol_id" BIGINT NOT NULL REFERENCES rol(id) ON DELETE CASCADE
);

CREATE TABLE "historial_busqueda" (
    "id" SERIAL PRIMARY KEY,
    "fecha" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "busqueda" VARCHAR NOT NULL,
    "usuario_id" BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE "resultado_busqueda" (
    "id" SERIAL PRIMARY KEY,
    "id_producto_mongodb" VARCHAR NOT NULL,
    "historial_busqueda_id" BIGINT NOT NULL REFERENCES historial_busqueda(id) ON DELETE CASCADE
);

CREATE TABLE "producto_visto" (
    "id" SERIAL PRIMARY KEY,
    "id_producto_mongodb" VARCHAR NOT NULL,
    "nombre_producto_mongodb" VARCHAR NOT NULL,
    "fecha" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "usuario_id" BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE "tipo_solicitud_pqrs" (
    "id" SERIAL PRIMARY KEY,
    "tipo_solicitud" VARCHAR NOT NULL CHECK ("tipo_solicitud" IN ('P', 'Q', 'R', 'S'))
);

CREATE TABLE "incidencia" (
    "id" SERIAL PRIMARY KEY,
    "tipo_incidencia" VARCHAR NOT NULL CHECK ("tipo_incidencia" IN ('creado', 'asignado', 'en revision', 'resuelto'))
);

CREATE TABLE "solicitud_pqrs" (
    "id" SERIAL PRIMARY KEY,
    "descripcion_solicitud" VARCHAR(255) NOT NULL,
    "fecha_registro" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "fecha_actualiza" TIMESTAMP,
    "tipo_solicitud_pqrs_id" BIGINT NOT NULL REFERENCES tipo_solicitud_pqrs(id) ON DELETE CASCADE,
    "incidencia_id" BIGINT NOT NULL REFERENCES incidencia(id) ON DELETE CASCADE
);

CREATE TABLE "solicitud_pqrs_usuario" (
    "id" SERIAL PRIMARY KEY,
    "usuario_id" BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    "solicitud_pqrs_id" BIGINT NOT NULL REFERENCES solicitud_pqrs(id) ON DELETE CASCADE
);

INSERT INTO "rol" ("rol") VALUES ('CLIENTE');
INSERT INTO "rol" ("rol") VALUES ('ADMIN');

INSERT INTO "tipo_solicitud_pqrs" ("tipo_solicitud") VALUES ('P');
INSERT INTO "tipo_solicitud_pqrs" ("tipo_solicitud") VALUES ('Q');
INSERT INTO "tipo_solicitud_pqrs" ("tipo_solicitud") VALUES ('R');
INSERT INTO "tipo_solicitud_pqrs" ("tipo_solicitud") VALUES ('S');

INSERT INTO "incidencia" ("tipo_incidencia") VALUES ('creado');
INSERT INTO "incidencia" ("tipo_incidencia") VALUES ('asignado');
INSERT INTO "incidencia" ("tipo_incidencia") VALUES ('en revision');
INSERT INTO "incidencia" ("tipo_incidencia") VALUES ('resuelto');

INSERT INTO "usuario" ("nombre_completo", "correo_electronico", "contrasena", "fecha_registro", "rol_id") VALUES 
('Miguel Valbuena', 'miguel.admin@email.com', '$2a$10$suicaOQhr04.M0Fdt8Q...ZgW3v9dpI4gVfTpsqoAEAXn3mQOlzJi', CURRENT_TIMESTAMP, 2);