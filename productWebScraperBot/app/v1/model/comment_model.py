class Comentario:

    def __init__(self, username = '', content = ''):
        self._username = username
        self._content = content

    @property
    def username(self):
        return self.username

    @property
    def content(self):
        return self.content

    @username.setter
    def username(self, value):
        self._username = value

    @content.setter
    def content(self, value):
        self._content = value

    def to_dict(self):
        return {
            'username': self._username,
            'content': self._content
        }
