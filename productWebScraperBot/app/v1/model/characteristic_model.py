import json


class Caracteristica:

    def __init__(self, nombre, valor):
        self._nombre = nombre
        self._valor = valor

    def to_dict(self):
        return {
            'nombre': self._nombre,
            'valor': self._valor
        }

    def to_json(self):
        return json.dumps(self.to_dict(), ensure_ascii=False)