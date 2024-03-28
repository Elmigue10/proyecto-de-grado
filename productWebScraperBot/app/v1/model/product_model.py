import json


class Producto:

    def __init__(self, id = '', nombre='', url='', marca='', precio='', categoria='', plataforma='', imagen_url='',
                 caracteristicas=None, comentarios=None, created_or_updated_at = ''):
        self._id = id
        self._nombre = nombre
        self._url = url
        self._marca = marca
        self._precio = precio
        self._categoria = categoria
        self._plataforma = plataforma
        self._imagen_url = imagen_url
        self._caracteristicas = caracteristicas if caracteristicas is not None else []
        self._comentarios = comentarios if comentarios is not None else []
        self._created_or_updated_at = created_or_updated_at

    @property
    def id(self):
        return self.id

    @property
    def nombre(self):
        return self.nombre

    @property
    def url(self):
        return self.url

    @property
    def marca(self):
        return self.marca

    @property
    def precio(self):
        return self.precio

    @property
    def categoria(self):
        return self.categoria

    @property
    def plataforma(self):
        return self.plataforma

    @property
    def imagen_url(self):
        return self.imagen_url

    @property
    def caracteristicas(self):
        return self.caracteristicas

    @property
    def comentarios(self):
        return self.comentarios

    @property
    def created_or_updated_at(self):
        return self.created_or_updated_at

    @id.setter
    def id(self, value):
        self._id = value

    @nombre.setter
    def nombre(self, value):
        self._nombre = value

    @url.setter
    def url(self, value):
        self._url = value

    @marca.setter
    def marca(self, value):
        self._marca = value

    @precio.setter
    def precio(self, value):
        self._precio = value

    @categoria.setter
    def categoria(self, value):
        self._categoria = value

    @plataforma.setter
    def plataforma(self, value):
        self._plataforma = value

    @imagen_url.setter
    def imagen_url(self, value):
        self._imagen_url = value

    @caracteristicas.setter
    def caracteristicas(self, value):
        self._caracteristicas = value

    @comentarios.setter
    def comentarios(self, value):
        self._comentarios = value

    @created_or_updated_at.setter
    def created_or_updated_at(self, value):
        self._created_or_updated_at = value

    def to_dict(self):
        return {
            '_id': self._id,
            'nombre': self._nombre,
            'url': self._url,
            'marca': self._marca,
            'precio': self._precio,
            'categoria': self._categoria,
            'plataforma': self._plataforma,
            'imagen_url': self._imagen_url,
            'caracteristicas': [caracteristica.to_dict() for caracteristica in self._caracteristicas],
            'comentarios': [comentario.to_dict() for comentario in self._comentarios],
            'createdOrUpdatedAt': self._created_or_updated_at
        }

    def to_json(self):
        return json.dumps(self.to_dict(), ensure_ascii=False)