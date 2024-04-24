FROM node:18-alpine

WORKDIR /cpmparatech-fe/

COPY public/ ./public
COPY src/ ./src
COPY package.json ./package.json
COPY package-lock.json ./package-lock.json

RUN npm install

CMD ["npm", "start"]