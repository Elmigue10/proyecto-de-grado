CREATE TABLE "cliente" (
    "id" SERIAL PRIMARY KEY,
    "correo_electronico" VARCHAR UNIQUE NOT NULL, 
    "contrasena" VARCHAR NOT NULL
);

CREATE TABLE "historial_busqueda" (
    "id" SERIAL PRIMARY KEY,
    "fecha" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "busqueda" VARCHAR NOT NULL,
    "cliente_id" BIGINT NOT NULL REFERENCES cliente(id) ON DELETE CASCADE
);

CREATE TABLE "producto_visto" (
    "id" SERIAL PRIMARY KEY,
    "id_producto_mongodb" BIGINT NOT NULL,
    "nombre_producto_mongodb" VARCHAR NOT NULL,
    "fecha" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "historial_busqueda_id" BIGINT NOT NULL REFERENCES historial_busqueda(id) ON DELETE CASCADE 
);

CREATE TABLE "administrador" (
    "id" SERIAL PRIMARY KEY,
    "nombre_completo" VARCHAR NOT NULL,
    "correo_electronico" VARCHAR UNIQUE NOT NULL, 
    "contrasena" VARCHAR NOT NULL
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
    "tipo_solicitud" CHAR(1) NOT NULL,
    "descripcion_solicitud" VARCHAR(255) NOT NULL,
    "fecha_registro" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "fecha_actualiza" TIMESTAMP,
    "cliente_id" BIGINT NOT NULL REFERENCES cliente(id) ON DELETE CASCADE,
    "tipo_solicitud_pqrs_id" BIGINT NOT NULL REFERENCES tipo_solicitud_pqrs(id) ON DELETE CASCADE,
    "incidencia_id" BIGINT NOT NULL REFERENCES incidencia(id) ON DELETE CASCADE,
    "administrador_id" BIGINT NOT NULL REFERENCES administrador(id) ON DELETE CASCADE
);