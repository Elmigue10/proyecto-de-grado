import random
import re

import pymongo

## DOCKER
client = pymongo.MongoClient("mongodb://ComAdmin:C0m4dm1n@mongo/")
## LOCAL
# client = pymongo.MongoClient("mongodb://localhost:27017/")
database = client["web-scraper"]
collection = database["products"]


def insert_product(product):
    filter_query = {'_id': product['_id']}
    update_query = {'$set': product}
    result = collection.update_one(filter_query, update_query, upsert=True)
    return result


def find_all(skip, limit):
    results = collection.find().skip(skip).limit(limit)

    total_items = collection.count_documents({})

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_id(product_id):
    query = {'_id': product_id}
    result = collection.find_one(query)
    return result


def find_by_brand(brand_name, skip, limit):
    regex_pattern = re.compile(f'^{re.escape(brand_name)}$', re.IGNORECASE)
    query = {'marca': {'$regex': regex_pattern}}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)
    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_category(category_name, skip, limit):
    regex_pattern = re.compile(f'^{re.escape(category_name)}$', re.IGNORECASE)
    query = {'categoria': {'$regex': regex_pattern}}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)
    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_platform(platform_name, skip, limit):
    regex_pattern = re.compile(f'^{re.escape(platform_name)}$', re.IGNORECASE)
    query = {'plataforma': {'$regex': regex_pattern}}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)
    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_name(product_name, skip, limit):
    regex_pattern = re.compile(f".*{re.escape(product_name)}.*", re.IGNORECASE)
    query = {'nombre': {'$regex': regex_pattern}}
    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)
    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_price_range(min_price, max_price, category_name, skip, limit):
    category_regex = re.compile(f'^{re.escape(category_name)}$', re.IGNORECASE)
    category_query = {'categoria': {'$regex': category_regex}}

    price_query = {
        "precio": {"$gte": min_price, "$lte": max_price}
    }

    query = {'$and': [price_query, category_query]}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)
    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_brand_and_category(brand_name, category_name, skip, limit):
    brand_regex = re.compile(f'^{re.escape(brand_name)}$', re.IGNORECASE)
    category_regex = re.compile(f'^{re.escape(category_name)}$', re.IGNORECASE)

    brand_query = {'marca': {'$regex': brand_regex}}
    category_query = {'categoria': {'$regex': category_regex}}

    query = {'$and': [brand_query, category_query]}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_platform_and_category(platform_name, category_name, skip, limit):
    platform_regex = re.compile(f'^{re.escape(platform_name)}$', re.IGNORECASE)
    category_regex = re.compile(f'^{re.escape(category_name)}$', re.IGNORECASE)

    platform_query = {'plataforma': {'$regex': platform_regex}}
    category_query = {'categoria': {'$regex': category_regex}}

    query = {'$and': [platform_query, category_query]}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_brand_category_and_platform(brand_name, category_name, platform_name, skip, limit):
    brand_regex = re.compile(f'^{re.escape(brand_name)}$', re.IGNORECASE)
    category_regex = re.compile(f'^{re.escape(category_name)}$', re.IGNORECASE)
    platform_regex = re.compile(f'^{re.escape(platform_name)}$', re.IGNORECASE)

    brand_query = {'marca': {'$regex': brand_regex}}
    category_query = {'categoria': {'$regex': category_regex}}
    platform_query = {'plataforma': {'$regex': platform_regex}}

    query = {'$and': [brand_query, category_query, platform_query]}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_ram_memory(ram_memory, category_name, skip, limit):
    category_regex = re.compile(f'^{re.escape(category_name)}$', re.IGNORECASE)
    category_query = {'categoria': {'$regex': category_regex}}

    query_ram = {"caracteristicas":
                     {"$elemMatch":
                          {"nombre": {"$regex": "ram", "$options": "i"},
                           "valor": {"$regex": ram_memory}
                           }
                      }
                 }

    query = {'$and': [query_ram, category_query]}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_storage_capacity(storage_capacity, category_name, skip, limit):
    category_regex = re.compile(f'^{re.escape(category_name)}$', re.IGNORECASE)
    category_query = {'categoria': {'$regex': category_regex}}

    query_storage_capacity = {
        "caracteristicas": {
            "$elemMatch": {
                "nombre": {
                    "$regex": "(memoria interna|capacidad de disco|disco duro|capacidad de almacenamiento|"
                              "unidad de estado sólido ssd|capacidad)",
                    "$options": "i"},
                "valor": {"$regex": storage_capacity}
            }
        }
    }

    query = {'$and': [query_storage_capacity, category_query]}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_screen_size(screen_size, category_name, skip, limit):
    category_regex = re.compile(f'^{re.escape(category_name)}$', re.IGNORECASE)
    category_query = {'categoria': {'$regex': category_regex}}

    query_screen_size = {
        "caracteristicas": {
            "$elemMatch": {
                "nombre": {"$regex": "tamaño.*pantalla|pantalla.*tamaño", "$options": "i"},
                "valor": {"$regex": screen_size}
            }
        }
    }

    query = {'$and': [query_screen_size, category_query]}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_by_id_list(id_list, skip, limit):
    query = {"_id": {"$in": id_list}}

    results = collection.find(query).skip(skip).limit(limit)
    total_items = collection.count_documents(query)

    return {
        'products': list(results),
        'total_items': total_items
    }


def find_brands():
    distinct_brands = collection.distinct("marca")
    return distinct_brands


def find_categories():
    distinct_categories = collection.distinct("categoria")
    return distinct_categories


def find_platforms():
    distinct_platforms = collection.distinct("plataforma")
    return distinct_platforms


def reorganize():
    all_documents = list(collection.find())
    random.shuffle(all_documents)
    collection.delete_many({})
    collection.insert_many(all_documents)
    return {
        'message': "OK",
        'status': 200
    }


def refine_id():
    products = collection.find()

    for product in products:
        if "/" in product["_id"]:
            new_id = product["_id"].replace("/", "-")

            new_document = {**product, "_id": new_id}

            collection.delete_one({"_id": product["_id"]})

            collection.insert_one(new_document)

    return {
        'message': "OK",
        'status': 200
    }
