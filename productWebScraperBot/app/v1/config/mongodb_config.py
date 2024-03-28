import pymongo

client = pymongo.MongoClient("mongodb://localhost:27017/")
database = client["web-scraper"]
collection = database["products"]


def insert_product(product):
    filter_query = {'_id': product['_id']}
    update_query = {'$set': product}
    result = collection.update_one(filter_query, update_query, upsert=True)
    return result
