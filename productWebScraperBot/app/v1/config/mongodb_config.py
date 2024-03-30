import pymongo
from bson import ObjectId
from fastapi_pagination import paginate

client = pymongo.MongoClient("mongodb://localhost:27017/")
database = client["web-scraper"]
collection = database["products"]


def insert_product(product):
    filter_query = {'_id': product['_id']}
    update_query = {'$set': product}
    result = collection.update_one(filter_query, update_query, upsert=True)
    return result


def find_all(skip, limit):
    total_items = collection.count_documents({})

    pipeline = [
        {'$sample': {'size': limit}},
        {'$skip': skip},
        {'$limit': limit}
    ]
    results = collection.aggregate(pipeline)

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_id(product_id):
    query = {'_id': product_id}
    result = collection.find_one(query)
    return result


def find_by_brand(brand_name):
    query = {'marca': brand_name}
    results = collection.find(query)
    return list(results)


def find_by_category(category_name):
    query = {'categoria': category_name}
    results = collection.find(query)
    return list(results)


def find_by_platform(platform_name):
    query = {'plataforma': platform_name}
    results = collection.find(query)
    return list(results)


def find_by_name(product_name):
    query = {'nombre': product_name}
    results = collection.find(query)
    return list(results)


def find_products_by_price_range(min_price, max_price):
    min_price = int(min_price)
    max_price = int(max_price)
    filter_query = {
        "precio": {"$gte": min_price, "$lte": max_price}
    }
    results = collection.find(filter_query)
    return list(results)


def find_brands():
    distinct_brands = collection.distinct("marca")
    return distinct_brands


def find_categories():
    distinct_categories = collection.distinct("categoria")
    return distinct_categories


def find_platforms():
    distinct_platforms = collection.distinct("plataforma")
    return distinct_platforms
